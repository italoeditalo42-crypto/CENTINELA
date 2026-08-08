package com.centinela.app.contract

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [IntentionContract::class], version = 2, exportSchema = false)
abstract class CentinelaDatabase : RoomDatabase() {
    abstract fun intentionContractDao(): IntentionContractDao

    companion object {
        @Volatile private var INSTANCE: CentinelaDatabase? = null

        fun getInstance(context: Context): CentinelaDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CentinelaDatabase::class.java,
                    "centinela_db"
                )
                .fallbackToDestructiveMigration() // Si el schema cambió, borra y recrea
                .build()
                .also { INSTANCE = it }
            }
        }
    }
}
