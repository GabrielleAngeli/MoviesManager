package br.edu.ifsp.scl.moviesmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.sdm.moviesmanager.R
import br.edu.ifsp.scl.sdm.moviesmanager.databinding.FragmentListMoviesBinding


class ListMoviesFragment : Fragment() {
    private lateinit var fragmentListMoviesBinding: FragmentListMoviesBinding

    // Navigation controller
    private val navController: NavController by lazy {
        findNavController()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = getString(R.string.movie_list)

        fragmentListMoviesBinding = FragmentListMoviesBinding.inflate(inflater, container, false).apply {
            moviesRv.layoutManager = LinearLayoutManager(context)

            addMovieFab.setOnClickListener {
                navController.navigate(
                    ListMoviesFragmentDirections.actionListMoviesFragmentToMovieFragment()
                )
            }
        }

        return fragmentListMoviesBinding.root

    }

}