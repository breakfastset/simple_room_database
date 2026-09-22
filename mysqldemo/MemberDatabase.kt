package com.example.mysqldemo

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(entities = arrayOf(Member::class), version = 1)
abstract class MemberDatabase: RoomDatabase() {

    abstract fun memberDao(): MemberDao  // to tell DB to find queries

    companion object {
        @Volatile
        private var Instance: MemberDatabase?= null

        fun getDatabase(context: Context): MemberDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MemberDatabase::class.java,
                    "member_database"
                )
                    .build()
                    .also { Instance = it}
                Instance = instance
                instance
            }
        }
    }
}