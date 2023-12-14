package br.edu.ifsp.scl.moviesmanager.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.edu.ifsp.scl.moviesmanager.model.entity.Gender
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie

@Dao
interface MovieDao {
    companion object {
        const val MOVIE_TABLE = "movie" // nome da tabela
    }
    @Insert
    fun createMovie(movie: Movie)

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun retrieveMovies(): List<Movie>
    @Insert
    fun createGender(gender: Gender)

    @Query("SELECT * FROM gender")
    fun retrieveGenders(): List<Gender>
}