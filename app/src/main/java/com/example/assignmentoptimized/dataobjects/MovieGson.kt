package com.example.assignmentoptimized.dataobjects

import com.example.assignmentoptimized.localdatabse.Movie

data class MovieGson(
    var page: String,
    var results: List<Movie>
)