package com.example.weathermvvm

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {
    val migration_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE weather_entity ADD COLUMN time TEXT NOT NULL DEFAULT 'undefined'")
        }
    }
}