package com.wilfredis.diariodedistas.Models

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wilfredis.diariodedistas.*

@Database(entities = arrayOf(User::class,Abodes::class), version = 1)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun abodesDab(): AbodesDab

}