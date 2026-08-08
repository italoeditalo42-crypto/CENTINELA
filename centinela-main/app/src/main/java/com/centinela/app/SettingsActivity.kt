package com.centinela.app

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun getInstalledApps(context: Context): List<Pair<String, String>> {
    val pm = context.packageManager
    return pm.getInstalledApplications(PackageManager.GET_META_DATA)
        .filter { it.flags and ApplicationInfo.FLAG_SYSTEM == 0 }
        .filter { it.packageName != "com.centinela.app" }
        .map { Pair(it.packageName, pm.getApplicationLabel(it).toString()) }
        .sortedBy { it.second }
}

fun getBlockedApps(context: Context): Set<String> {
    return context.getSharedPreferences("centinela", Context.MODE_PRIVATE)
        .getStringSet("blocked_apps", setOf(
            "com.google.android.youtube",
            "com.instagram.android",
            "com.zhiliaoapp.musically",
            "com.twitter.android",
            "com.facebook.katana"
        )) ?: emptySet()
}

fun saveBlockedApps(context: Context, apps: Set<String>) {
    context.getSharedPreferences("centinela", Context.MODE_PRIVATE)
        .edit().putStringSet("blocked_apps", apps).apply()
}

fun getUsageThresholdMinutes(context: Context): Int {
    return context.getSharedPreferences("centinela", Context.MODE_PRIVATE)
        .getInt("usage_threshold_minutes", 20)
}

fun saveUsageThreshold(context: Context, minutes: Int) {
    context.getSharedPreferences("centinela", Context.MODE_PRIVATE)
        .edit().putInt("usage_threshold_minutes", minutes).apply()
}

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SettingsScreen(onDone = { finish() }) }
    }
}

@Composable
fun SettingsScreen(onDone: () -> Unit) {
    val context = LocalContext.current
    val apps = remember { getInstalledApps(context) }
    val prefs = context.getSharedPreferences("centinela", Context.MODE_PRIVATE)

    // Todos los estados fuera del LazyColumn para evitar reset al hacer scroll
    var blockedApps by remember { mutableStateOf(getBlockedApps(context)) }
    var thresholdMinutes by remember { mutableStateOf(getUsageThresholdMinutes(context).toFloat()) }
    var blockDurationMinutes by remember { mutableStateOf(prefs.getInt("block_duration_minutes", 10).toFloat()) }
    var hourlyValueText by remember { mutableStateOf(
        prefs.getFloat("hourly_value", 0f).let { if (it == 0f) "" else it.toString() }
    )}
    var debtAction by remember { mutableStateOf(prefs.getString("debt_action", "") ?: "") }
    var debtMinutesPerUnit by remember { mutableStateOf(prefs.getInt("debt_minutes_per_unit", 10).toFloat()) }
    var lockPassword by remember { mutableStateOf(prefs.getString("lock_password", "") ?: "") }
    var lockDurationTotal by remember { mutableStateOf(prefs.getInt("lock_duration_minutes", 30).toFloat()) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFF080808)).padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("CONFIGURACIÓN", color = Color(0xFF444444), fontSize = 11.sp,
                fontWeight = FontWeight.Bold, letterSpacing = 6.sp)
            Spacer(Modifier.height(24.dp))
        }

        // ── Tiempo antes del bloqueo ──
        item {
            Text("TIEMPO ANTES DEL BLOQUEO", color = Color(0xFF666666),
                fontSize = 11.sp, letterSpacing = 3.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("${thresholdMinutes.toInt()} MINUTOS", color = Color.White,
                fontSize = 22.sp, fontWeight = FontWeight.Black)
            Slider(
                value = thresholdMinutes,
                onValueChange = { thresholdMinutes = it },
                onValueChangeFinished = { saveUsageThreshold(context, thresholdMinutes.toInt()) },
                valueRange = 5f..60f, steps = 10,
                colors = androidx.compose.material3.SliderDefaults.colors(
                    thumbColor = Color(0xFFCC0000), activeTrackColor = Color(0xFFCC0000),
                    inactiveTrackColor = Color(0xFF222222))
            )
            Spacer(Modifier.height(16.dp))
        }

        // ── Duración del bloqueo de app ──
        item {
            Text("DURACIÓN DEL BLOQUEO DE APP", color = Color(0xFF666666),
                fontSize = 11.sp, letterSpacing = 3.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("${blockDurationMinutes.toInt()} MINUTOS", color = Color.White,
                fontSize = 22.sp, fontWeight = FontWeight.Black)
            Slider(
                value = blockDurationMinutes,
                onValueChange = { blockDurationMinutes = it },
                onValueChangeFinished = {
                    prefs.edit().putInt("block_duration_minutes", blockDurationMinutes.toInt()).apply()
                },
                valueRange = 1f..60f, steps = 58,
                colors = androidx.compose.material3.SliderDefaults.colors(
                    thumbColor = Color(0xFFCC0000), activeTrackColor = Color(0xFFCC0000),
                    inactiveTrackColor = Color(0xFF222222))
            )
            Spacer(Modifier.height(16.dp))
        }

        // ── Bloqueo total del celular ──
        item {
            Text("BLOQUEO TOTAL DEL CELULAR", color = Color(0xFF666666),
                fontSize = 11.sp, letterSpacing = 3.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("Contraseña de emergencia (dásela a tu mamá)",
                color = Color(0xFF444444), fontSize = 11.sp)
            Spacer(Modifier.height(8.dp))
            BasicTextField(
                value = lockPassword,
                onValueChange = {
                    lockPassword = it
                    prefs.edit().putString("lock_password", it).apply()
                },
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, Color(0xFF222222))
                    .background(Color(0xFF0D0D0D))
                    .padding(16.dp),
                textStyle = TextStyle(color = Color.White, fontSize = 14.sp,
                    letterSpacing = 4.sp),
                cursorBrush = SolidColor(Color(0xFFFFFF00)),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                decorationBox = { inner ->
                    if (lockPassword.isEmpty()) Text("Contraseña numérica...",
                        color = Color(0xFF333333), fontSize = 14.sp)
                    inner()
                }
            )
            Spacer(Modifier.height(8.dp))
            Text("DURACIÓN DEL BLOQUEO TOTAL", color = Color(0xFF666666),
                fontSize = 11.sp, letterSpacing = 3.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("${lockDurationTotal.toInt()} MINUTOS", color = Color.White,
                fontSize = 18.sp, fontWeight = FontWeight.Black)
            Slider(
                value = lockDurationTotal,
                onValueChange = { lockDurationTotal = it },
                onValueChangeFinished = {
                    prefs.edit().putInt("lock_duration_minutes", lockDurationTotal.toInt()).apply()
                },
                valueRange = 10f..480f, steps = 46,
                colors = androidx.compose.material3.SliderDefaults.colors(
                    thumbColor = Color(0xFFCC0000), activeTrackColor = Color(0xFFCC0000),
                    inactiveTrackColor = Color(0xFF222222))
            )
            Spacer(Modifier.height(16.dp))
        }

        // ── El Costo Declarado ──
        item {
            Text("EL COSTO DECLARADO", color = Color(0xFF666666),
                fontSize = 11.sp, letterSpacing = 3.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("¿Cuánto vale tu hora? (en tu moneda)", color = Color(0xFF444444),
                fontSize = 11.sp)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, Color(0xFF222222))
                    .background(Color(0xFF0D0D0D))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("$", color = Color(0xFFCC0000), fontSize = 18.sp,
                    fontWeight = FontWeight.Black)
                Spacer(Modifier.width(8.dp))
                BasicTextField(
                    value = hourlyValueText,
                    onValueChange = { v ->
                        hourlyValueText = v.filter { it.isDigit() || it == '.' }
                        val fl = hourlyValueText.toFloatOrNull() ?: 0f
                        prefs.edit().putFloat("hourly_value", fl).apply()
                    },
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp,
                        fontWeight = FontWeight.Bold),
                    cursorBrush = SolidColor(Color(0xFFFFFF00)),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    decorationBox = { inner ->
                        if (hourlyValueText.isEmpty()) Text("0.00",
                            color = Color(0xFF333333), fontSize = 18.sp)
                        inner()
                    }
                )
            }
            Spacer(Modifier.height(16.dp))
        }

        // ── La Deuda de Atención ──
        item {
            Text("LA DEUDA DE ATENCIÓN", color = Color(0xFF666666),
                fontSize = 11.sp, letterSpacing = 3.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("Acción que pagás por cada ${debtMinutesPerUnit.toInt()} min de distracción",
                color = Color(0xFF444444), fontSize = 11.sp)
            Spacer(Modifier.height(8.dp))
            BasicTextField(
                value = debtAction,
                onValueChange = {
                    debtAction = it
                    prefs.edit().putString("debt_action", it).apply()
                },
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, Color(0xFF222222))
                    .background(Color(0xFF0D0D0D))
                    .padding(16.dp),
                textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
                cursorBrush = SolidColor(Color(0xFFFFFF00)),
                decorationBox = { inner ->
                    if (debtAction.isEmpty()) Text("Ej: 10 flexiones, leer 5 páginas...",
                        color = Color(0xFF333333), fontSize = 14.sp)
                    inner()
                }
            )
            Spacer(Modifier.height(8.dp))
            Text("${debtMinutesPerUnit.toInt()} MIN = 1 acción",
                color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Black)
            Slider(
                value = debtMinutesPerUnit,
                onValueChange = { debtMinutesPerUnit = it },
                onValueChangeFinished = {
                    prefs.edit().putInt("debt_minutes_per_unit", debtMinutesPerUnit.toInt()).apply()
                },
                valueRange = 5f..60f, steps = 10,
                colors = androidx.compose.material3.SliderDefaults.colors(
                    thumbColor = Color(0xFFCC0000), activeTrackColor = Color(0xFFCC0000),
                    inactiveTrackColor = Color(0xFF222222))
            )
            Spacer(Modifier.height(16.dp))
        }

        // ── Apps vigiladas ──
        item {
            Text("APPS VIGILADAS", color = Color(0xFF666666),
                fontSize = 11.sp, letterSpacing = 3.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
        }

        items(apps) { (packageName, appName) ->
            val isBlocked = packageName in blockedApps
            Row(
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, if (isBlocked) Color(0xFF330000) else Color(0xFF1A1A1A))
                    .background(if (isBlocked) Color(0xFF110000) else Color(0xFF0D0D0D))
                    .clickable {
                        blockedApps = if (isBlocked) blockedApps - packageName
                        else blockedApps + packageName
                        saveBlockedApps(context, blockedApps)
                    }
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(appName,
                    color = if (isBlocked) Color.White else Color(0xFF666666),
                    fontSize = 13.sp,
                    fontWeight = if (isBlocked) FontWeight.Bold else FontWeight.Normal)
                Text(if (isBlocked) "✕" else "+",
                    color = if (isBlocked) Color(0xFFCC0000) else Color(0xFF333333),
                    fontSize = 16.sp, fontWeight = FontWeight.Black)
            }
        }

        item {
            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(56.dp)
                    .background(Color(0xFFCC0000))
                    .clickable { onDone() },
                contentAlignment = Alignment.Center
            ) {
                Text("GUARDAR Y SALIR", color = Color.White, fontSize = 13.sp,
                    fontWeight = FontWeight.Black, letterSpacing = 4.sp)
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
