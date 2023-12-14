package br.edu.ifsp.scl.moviesmanager.model.dao

import androidx.room.Dao
import androidx.room.Insert
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie

@Dao
interface MovieDao {
    companion object {
        const val MOVIE_TABLE = "movie" // nome da tabela
    }
    @Insert
    fun createMovie(movie: Movie)
}