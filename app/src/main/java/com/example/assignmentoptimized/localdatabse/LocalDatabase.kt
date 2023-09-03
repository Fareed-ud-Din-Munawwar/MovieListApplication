package com.example.assignmentoptimized.localdatabse

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}