package com.example.assignmentoptimized.localdatabse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.assignmentoptimized.dataobjects.MovieType

@Entity
data class Movie(
    val title: String = "",
    val release_date: String = "",
    val overview: String = "",
    val original_language: String = "",
    val poster_path: String = "",
    val vote_average: Float = 0F,
    val tag: String = MovieType.DEFAULT.toString(),
    var favourite: Boolean = false,
    @PrimaryKey
    val id: Long = 0,
)
