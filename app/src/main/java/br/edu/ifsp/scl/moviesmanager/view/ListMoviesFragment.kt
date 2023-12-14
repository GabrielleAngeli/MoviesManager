package br.edu.ifsp.scl.moviesmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.moviesmanager.controller.MovieViewModel
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.view.adapter.MovieAdapter
import br.edu.ifsp.scl.moviesmanager.view.adapter.OnMovieClickListener
import br.edu.ifsp.scl.sdm.moviesmanager.R
import br.edu.ifsp.scl.sdm.moviesmanager.databinding.FragmentListMoviesBinding


class ListMoviesFragment : Fragment(), OnMovieClickListener {
    private lateinit var fragmentListMoviesBinding: FragmentListMoviesBinding

    // Navigation controller
    private val navController: NavController by lazy {
        findNavController()
    }
    //Presenter - MVP
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.MovieViewModelFactory
    }

    // Data source
    private val movieList: MutableList<Movie> = mutableListOf()

    // Adapter
    private val moviesAdapter: MovieAdapter by lazy {
        MovieAdapter(movieList, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel.moviesMld.observe(requireActivity()) { movies ->
            movieList.clear()
            movies.forEachIndexed { index, task ->
                movieList.add(task)
                moviesAdapter.notifyItemChanged(index)
            }
        }


        movieViewModel.getMovies()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = getString(R.string.movie_list)

        fragmentListMoviesBinding = FragmentListMoviesBinding.inflate(inflater, container, false).apply {
            moviesRv.layoutManager = LinearLayoutManager(context)
            moviesRv.adapter = moviesAdapter

            addMovieFab.setOnClickListener {
                navController.navigate(
                    ListMoviesFragmentDirections.actionListMoviesFragmentToMovieFragment()
                )
            }
        }

        return fragmentListMoviesBinding.root

    }

    override fun onMovieClick(position: Int) = navigateToMovieFragment(position, false)

    private fun navigateToMovieFragment(position: Int, editMovie: Boolean) {
        movieList[position].also {
            navController.navigate(
                ListMoviesFragmentDirections.actionListMoviesFragmentToMovieFragment()
            )
        }
    }
    override fun onRemoveMovieMenuItemClick(position: Int) {
    }

    override fun onEditMovieMenuItemClick(position: Int) {
    }

    override fun onDoneCheckBoxClick(position: Int, checked: Boolean) {
    }

}