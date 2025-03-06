package org.d3ifcool.twily.db

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 1
)

abstract class NoteDB : RoomDatabase(){

    abstract fun noteDao() : NoteDao

    companion object {
        @VisibleForTesting
        const val DATABASE_NAME = "note.db"

        @Volatile private var instance : NoteDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDB::class.java,
            "note.db"
        ).build()

    }
}