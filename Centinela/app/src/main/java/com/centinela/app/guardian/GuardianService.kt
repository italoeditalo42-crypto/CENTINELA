package com.centinela.app.guardian

import android.app.*
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.centinela.app.DebtActivity
import com.centinela.app.InterruptActivity
import com.centinela.app.WeeklyMirrorActivity
import kotlinx.coroutines.*
import java.util.Calendar

class GuardianService : Service() {

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var checkJob: Job? = null
    private val prefs by lazy { getSharedPreferences("centinela", Context.MODE_PRIVATE) }

    // Estado persistente en SharedPreferences
    private var lastInterruptedApp: String?
        get() = prefs.getString("last_interrupted_app", null)
        set(v) = prefs.edit().putString("last_interrupted_app", v).apply()

    private var lastInterruptTime: Long
        get() = prefs.getLong("last_interrupt_time", 0L)
        set(v) = prefs.edit().putLong("last_interrupt_time", v).apply()

    private var blockedUntil: Long
        get() = prefs.getLong("blocked_until", 0L)
        set(v) = prefs.edit().putLong("blocked_until", v).apply()

    // Flag en memoria para evitar race condition del espejo semanal
    private var weeklyMirrorLaunchedThisSession = false

    companion object {
        const val CHANNEL_ID = "centinela_guardian"
        const val NOTIFICATION_ID = 1
        const val CHECK_INTERVAL_MS = 5_000L
        const val COOLDOWN_MS = 10 * 60 * 1000L
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification())
        startWatching()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY

    private fun startWatching() {
        checkJob = scope.launch {
            while (isActive) {
                checkWeeklyMirror()
                checkUsage()
                delay(CHECK_INTERVAL_MS)
            }
        }
    }

    private fun checkWeeklyMirror() {
        if (weeklyMirrorLaunchedThisSession) return

        val now = System.currentTimeMillis()
        val cal = Calendar.getInstance().apply { timeInMillis = now }
        val isSunday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val lastMirror = prefs.getLong("last_weekly_mirror", 0L)
        val oneDayMs = 24 * 60 * 60 * 1000L

        if (isSunday && hour >= 10 && (now - lastMirror) > oneDayMs) {
            // Marcar ANTES de lanzar para evitar lanzamientos múltiples
            weeklyMirrorLaunchedThisSession = true
            prefs.edit().putLong("last_weekly_mirror", now).apply()
            val intent = Intent(this, WeeklyMirrorActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }
    }

    private fun checkUsage() {
        val usageStats = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val now = System.currentTimeMillis()
        val windowStart = now - (60 * 60 * 1000L)
        val stats = usageStats.queryUsageStats(UsageStatsManager.INTERVAL_BEST, windowStart, now)

        val userBlockedApps = prefs.getStringSet("blocked_apps", null) ?: setOf(
            "com.google.android.youtube", "com.instagram.android",
            "com.zhiliaoapp.musically", "com.twitter.android", "com.facebook.katana"
        )

        val thresholdMinutes = prefs.getInt("usage_threshold_minutes", 20)
        val usageThreshold = thresholdMinutes * 60 * 1000L
        val blockDurationMinutes = prefs.getInt("block_duration_minutes", 10)
        val blockDurationMs = blockDurationMinutes * 60 * 1000L

        val topApp = stats
            ?.filter { it.packageName in userBlockedApps }
            ?.filter { it.lastTimeUsed >= (now - 10_000L) }
            ?.maxByOrNull { it.totalTimeInForeground }
            ?: return

        val sessionStart = now - usageThreshold
        val recentStats = usageStats.queryUsageStats(UsageStatsManager.INTERVAL_BEST, sessionStart, now)
        val sessionTime = recentStats
            ?.filter { it.packageName == topApp.packageName }
            ?.sumOf { it.totalTimeInForeground } ?: 0L

        if (sessionTime < usageThreshold) return

        val distractionMinutes = sessionTime / 60_000L

        // Deuda: tiene acción configurada Y no la ha pagado en este ciclo de bloqueo
        val debtAction = prefs.getString("debt_action", "") ?: ""
        val debtPaidAt = prefs.getLong("debt_paid_at", 0L)
        val debtIsPending = debtAction.isNotBlank() && debtPaidAt < blockedUntil

        if (now < blockedUntil) {
            if (debtIsPending) {
                val intent = Intent(this, DebtActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    putExtra("distraction_minutes", distractionMinutes)
                }
                startActivity(intent)
            } else {
                triggerInterrupt(topApp.packageName, topApp.totalTimeInForeground, sessionTime)
            }
            return
        }

        val cooldownExpired = (now - lastInterruptTime) > COOLDOWN_MS
        val isDifferentApp = topApp.packageName != lastInterruptedApp

        if (isDifferentApp || cooldownExpired) {
            blockedUntil = now + blockDurationMs
            lastInterruptedApp = topApp.packageName
            lastInterruptTime = now
            triggerInterrupt(topApp.packageName, topApp.totalTimeInForeground, sessionTime)
        }
    }

    private fun triggerInterrupt(packageName: String, timeMs: Long, sessionMs: Long) {
        val sessionStartMs = System.currentTimeMillis() - sessionMs
        val intent = Intent(this, InterruptActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("package_name", packageName)
            putExtra("time_ms", timeMs)
            putExtra("session_start_ms", sessionStartMs)
        }
        startActivity(intent)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, "Guardián Activo",
            NotificationManager.IMPORTANCE_MIN).apply {
            description = "CENTINELA vigilando en segundo plano"
            setShowBadge(false)
        }
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    private fun buildNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("CENTINELA")
        .setContentText("Guardián activo")
        .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
        .setPriority(NotificationCompat.PRIORITY_MIN)
        .setOngoing(true)
        .build()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        checkJob?.cancel()
        scope.cancel()
    }
}
