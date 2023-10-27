package com.example.libraryapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

// RoomのEntityを定義
@Entity
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val title: String
)
