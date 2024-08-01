package com.example.androidtest.ex1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.androidtest.ex1.model.Student
import com.example.androidtest.ex1.model.StudentDao

@Database(entities = [Student::class], version = 3)
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
                ).addMigrations( MIGRATION_2_3).build()
                INSTANT = instant
                instant
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE students ADD COLUMN gender TEXT NOT NULL DEFAULT 'Unknown'")
            }
        }
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE students_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, age INTEGER NOT NULL, gender TEXT NOT NULL)")
                db.execSQL("INSERT INTO students_new (id, name, age, gender) SELECT id, name, age, gender FROM students")
                db.execSQL("DROP TABLE students")
                db.execSQL("ALTER TABLE students_new RENAME TO students")
            }
        }
    }
}
