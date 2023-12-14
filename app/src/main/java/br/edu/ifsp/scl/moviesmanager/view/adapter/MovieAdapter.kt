package br.edu.ifsp.scl.moviesmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.sdm.moviesmanager.R
import br.edu.ifsp.scl.sdm.moviesmanager.databinding.MovieCellBinding

class MovieAdapter(
    private val taskList: List<Movie>,
        private val onMovieClickListener: OnMovieClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieTileViewHolder>() {
    inner class MovieTileViewHolder(movieCellBinding: MovieCellBinding) :
        RecyclerView.ViewHolder(movieCellBinding.root) {
        val nameTv: TextView = movieCellBinding.nameTv
        val genreAndYearTV: TextView = movieCellBinding.genreAndYearTv
        val scoreTv: TextView = movieCellBinding.scoreTv

        init {
            movieCellBinding.apply {
                root.run {
                    setOnCreateContextMenuListener { menu, _, _ ->
                        (onMovieClickListener as? Fragment)?.activity?.menuInflater?.inflate(
                            R.menu.context_menu_task,
                            menu
                        )
                        menu?.findItem(R.id.removeMovieMi)?.setOnMenuItemClickListener {
                            onMovieClickListener.onRemoveMovieMenuItemClick(adapterPosition)
                            true
                        }
                        menu?.findItem(R.id.editMovieMi)?.setOnMenuItemClickListener {
                            onMovieClickListener.onEditMovieMenuItemClick(adapterPosition)
                            true
                        }
                    }
                    setOnClickListener {
                        onMovieClickListener.onMovieClick(adapterPosition)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieCellBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).run { MovieTileViewHolder(this) }


    override fun onBindViewHolder(holder: MovieTileViewHolder, position: Int) {
        taskList[position].let { movie ->
            with(holder) {
                nameTv.text = movie.name
                genreAndYearTV.text = movie.yearLaunch.toString()
                scoreTv.text = movie.score.toString()
            }
        }
    }

    override fun getItemCount() = taskList.size
}