package com.alome.mvp.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesDb : RoomDatabase() {
    abstract fun MovieDao(): MovieDao?
    companion object {
        private var INSTANCE: MoviesDb? = null


        fun getDatabase(context: Context): MoviesDb? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<MoviesDb>(
                    context.applicationContext, MoviesDb::class.java, "AppDBB"
                ).allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }

    fun destroyInstance() {
        INSTANCE = null
    }
}