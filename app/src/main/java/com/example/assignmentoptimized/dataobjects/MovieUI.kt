package com.example.assignmentoptimized.dataobjects

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.assignmentoptimized.BR
import com.example.assignmentoptimized.localdatabse.Movie
import java.text.SimpleDateFormat
import java.util.*


class MovieUI() : BaseObservable() {

    constructor(movie: Movie) : this() {
        title = movie.title
        release_date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(movie.release_date)
        overview = movie.overview
        original_language = movie.original_language
        poster_path = movie.poster_path
        vote_average = movie.vote_average.toFloat()
        tag = movie.tag
        id = movie.id
    }

    var title: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }
    var release_date: Date = Date()
        @Bindable get
        @RequiresApi(Build.VERSION_CODES.O)
        set(value) {
            field = value
            notifyPropertyChanged(BR.release_date)
        }

    var overview: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.overview)
        }
    var original_language: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.original_language)
        }
    var adult: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.adult)
        }
    var poster_path: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.poster_path)
        }
    var vote_average: Float = 0F
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.vote_average)
        }


    var tag: String = "Default"
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.tag)
        }

    var id: Long = 0
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }

}

