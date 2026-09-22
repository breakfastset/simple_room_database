package com.example.mysqldemo

import androidx.room3.Dao
import androidx.room3.Query

@Dao
interface MemberDao {
    @Query("SELECT * FROM member")
    suspend fun getAllMembers(): List<Member>
}