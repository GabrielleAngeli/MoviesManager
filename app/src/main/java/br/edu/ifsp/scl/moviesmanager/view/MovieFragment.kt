package br.edu.ifsp.scl.moviesmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.edu.ifsp.scl.moviesmanager.controller.MovieViewModel
import br.edu.ifsp.scl.moviesmanager.model.entity.Gender
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.view.adapter.GenderAdapter
import br.edu.ifsp.scl.sdm.moviesmanager.R
import br.edu.ifsp.scl.sdm.moviesmanager.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {
    private lateinit var fragmentMovieBinding: FragmentMovieBinding

    //Presenter - MVP
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.MovieViewModelFactory
    }

    // Adapter
    private val gendersAdapter: GenderAdapter by lazy {
        GenderAdapter( requireContext(),
            genderList)
    }

    // Data source
    private val genderList: MutableList<Gender> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieViewModel.gendersMld.observe(requireActivity()) { genders ->
            genderList.clear()
            genders.forEachIndexed { index, genders ->
                genderList.add(genders)
            }
        }

        movieViewModel.getGenders()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = getString(R.string.register_movie)

        fragmentMovieBinding = FragmentMovieBinding.inflate(inflater, container, false)

        val spinnerGenero = fragmentMovieBinding.movieGenreSp


        spinnerGenero.adapter = gendersAdapter


        // Adicione um ouvinte de seleção ao Spinner
        spinnerGenero.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        fragmentMovieBinding.addGenreBtn.setOnClickListener {
            mostrarDialogoIncluirGenero()
        }

        fragmentMovieBinding.saveBt.setOnClickListener {
            val movieName = fragmentMovieBinding.nameEt.text.toString()
            val movieYear = fragmentMovieBinding.yearLaunchEt.text.toString()
            val movieProducer = fragmentMovieBinding.producerEt.text.toString()
            val movieDuration = fragmentMovieBinding.durationMinutesEt.text.toString()
            val movieScore = fragmentMovieBinding.scoreSp.selectedItemPosition
            val movieWatched = fragmentMovieBinding.watchedCb.isChecked
            val movieGenre = fragmentMovieBinding.movieGenreSp.selectedItem

            if (!movieName.isNullOrEmpty() && !movieYear.isNullOrEmpty() && !movieDuration.isNullOrEmpty() )
            {
                val movie = Movie(
                    name = movieName,
                    yearLaunch = movieYear.toInt(),
                    producer = movieProducer,
                    durationMinutes = movieDuration.toInt(),
                    watched = movieWatched,
                    score = movieScore,
                )

                movieViewModel.insertMovie(movie)

            }
            else Toast.makeText( requireActivity(), "Existem campos preenchidos incorretamente.", Toast.LENGTH_SHORT).show()
        }

        return fragmentMovieBinding.root

    }

    private fun mostrarDialogoIncluirGenero() {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = LayoutInflater.from(requireActivity())
        val view = inflater.inflate(R.layout.add_gender, null)

        val editTextNovoGenero: EditText = view.findViewById(R.id.editTextNovoGenero)

        builder.setView(view)
            .setTitle("Incluir Gênero")
            .setPositiveButton("Salvar") { dialog, which ->
                // Obtenha o novo gênero inserido pelo usuário
                val novoGenero = Gender(name = editTextNovoGenero.text.toString())

                movieViewModel.insertGender(novoGenero)
                movieViewModel.getGenders()
                gendersAdapter.notifyDataSetChanged()
                // Implemente a lógica para adicionar o novo gênero aos dados do filme ou à lista de gêneros
                // Certifique-se de tratar a adição do novo gênero aos dados do filme
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                // Implemente a lógica para cancelar a inclusão do novo gênero
            }
            .show()
    }
}