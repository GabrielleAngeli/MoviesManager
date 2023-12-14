package br.edu.ifsp.scl.moviesmanager.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Movie (
    @PrimaryKey
    var name: String = "",
    var yearLaunch: Int = 0,
    var producer: String = "",
    var durationMinutes: Int = 0,
    var watched: Boolean = false,
    var score: Float = 0f,
    var movieGenre: String = ""
    ) : Parcelable