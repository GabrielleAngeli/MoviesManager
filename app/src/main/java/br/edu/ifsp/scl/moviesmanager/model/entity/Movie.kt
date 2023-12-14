package br.edu.ifsp.scl.moviesmanager.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
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
    var score: Int = 0,
    ) : Parcelable {  }