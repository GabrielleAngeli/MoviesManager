package br.edu.ifsp.scl.moviesmanager.controller

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Room
import br.edu.ifsp.scl.moviesmanager.model.database.MovieDataBase
import br.edu.ifsp.scl.moviesmanager.model.entity.Gender
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application): ViewModel() {
    private val movieDaoImpl = Room.databaseBuilder(
        application.applicationContext,
        MovieDataBase::class.java,
        MovieDataBase.MOVIE_DATABASE)
        .build().getMovieDao()

    val gendersMld = MutableLiveData<List<Gender>>()
    val moviesMld = MutableLiveData<List<Movie>>()

    fun insertMovie(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.createMovie(movie)
        }
    }

    fun getMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            val movies = movieDaoImpl.retrieveMovies()
            moviesMld.postValue(movies)
        }
    }
    fun insertGender(gender: Gender) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.createGender(gender)
        }
    }

    fun getGenders() {
        CoroutineScope(Dispatchers.IO).launch {
            val tasks = movieDaoImpl.retrieveGenders()
            gendersMld.postValue(tasks)
        }
    }

    companion object {
        val MovieViewModelFactory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T  =
                MovieViewModel(checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])) as T
        }
    }
}