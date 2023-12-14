package br.edu.ifsp.scl.moviesmanager.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifsp.scl.moviesmanager.model.dao.MovieDao
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie

@Database(entities = [Movie::class], version = 1)

abstract class MovieDataBase : RoomDatabase() {
    companion object {
        const val MOVIE_DATABASE = "MovieDatabase"
    }
    abstract fun getTaskDao(): MovieDao
}