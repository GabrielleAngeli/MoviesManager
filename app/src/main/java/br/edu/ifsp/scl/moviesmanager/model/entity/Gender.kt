package br.edu.ifsp.scl.moviesmanager.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Gender(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var name: String
) : Parcelable