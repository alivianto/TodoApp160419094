package com.ubaya.todoapp160419094.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ubaya.todoapp160419094.model.TodoDatabase

val DB_NAME = "newtododb"

fun buildDb(context: Context) =
    Room.databaseBuilder(context, TodoDatabase::class.java,"newtododb")
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
        .build()

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 NOT NULL")
    }
}

// Alter the Todo table by adding a new field:
// is_done
// INTEGER
// Default value is 0 and not null

val MIGRATION_2_3 = object : Migration(2,3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Why this is_done field use INTEGER instead of BOOLEAN?
        // Menurut saya jika memakai BOOLEAN pada akhirnya pada saat data akan diinputkan data tersebut
        // akan menjadi INTEGER (1 atau 0). Oleh karena itu lebih baik menggunakan integer saja agar
        // tidak perlu convert toInteger lagi.
        database.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER DEFAULT 0 NOT NULL")
    }
}