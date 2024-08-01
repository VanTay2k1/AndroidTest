package com.example.androidtest.ex1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.androidtest.ex1.model.Student
import com.example.androidtest.ex1.model.StudentDao

@Database(entities = [Student::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANT: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANT ?: synchronized(this) {
                val instant = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "student_database"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANT = instant
                instant
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE students ADD COLUMN gender TEXT NOT NULL DEFAULT 'Unknown'")
            }
        }
    }
}
