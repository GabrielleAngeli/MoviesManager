package br.edu.ifsp.scl.moviesmanager.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifsp.scl.moviesmanager.model.entity.Gender
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie

@Dao
interface MovieDao {
    companion object {
        const val MOVIE_TABLE = "movie" // nome da tabela
    }
    @Insert
    fun createMovie(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun retrieveMovies(): List<Movie>

    @Delete
    fun deleteMove(movie: Movie)

    @Insert
    fun createGender(gender: Gender)

    @Query("SELECT * FROM gender")
    fun retrieveGenders(): List<Gender>
}