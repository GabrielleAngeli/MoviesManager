package br.edu.ifsp.scl.moviesmanager.view

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.edu.ifsp.scl.moviesmanager.controller.MovieViewModel
import br.edu.ifsp.scl.moviesmanager.model.Constants.EXTRA_MOVIE
import br.edu.ifsp.scl.moviesmanager.model.Constants.MOVIE_FRAGMENT_REQUEST_KEY
import br.edu.ifsp.scl.moviesmanager.model.entity.Gender
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.view.adapter.GenderAdapter
import br.edu.ifsp.scl.moviesmanager.view.adapter.OnGenderClickListener
import br.edu.ifsp.scl.moviesmanager.view.adapter.OnMovieClickListener
import br.edu.ifsp.scl.sdm.moviesmanager.R
import br.edu.ifsp.scl.sdm.moviesmanager.databinding.FragmentMovieBinding

class MovieFragment : Fragment(), OnGenderClickListener {
    private lateinit var fragmentMovieBinding: FragmentMovieBinding
    private val navigationArgs: MovieFragmentArgs by navArgs()

    //Presenter - MVP
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.MovieViewModelFactory
    }

    // Adapter
    private val gendersAdapter: GenderAdapter by lazy {
        GenderAdapter(requireContext(), genderList, this)
    }

    private val scoreList = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "Não selecionado"
    )

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
        fragmentMovieBinding.scoreSp.adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, scoreList )
        fragmentMovieBinding.movieGenreSp.adapter = GenderAdapter(requireActivity(), genderList, this)

        val receivedTask = navigationArgs.movie
        receivedTask?.also { movie ->
            (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Detalhes"
            with(fragmentMovieBinding) {
                nameEt.setText(movie.name)
                producerEt.setText(movie.producer)
                yearLaunchEt.setText(movie.yearLaunch.toString())
                if(movie.durationMinutes> 0) {
                    durationMinutesEt.setText(movie.durationMinutes.toString())
                } else durationMinutesEt.setText(0)
                watchedCb.isChecked = movie.watched
                scoreSp.setSelection(movie.score - 1)
                movieGenreSp.setSelection(movie.movieGenresPosition)
                navigationArgs.editMovie.also { editTask ->
                    nameEt.isEnabled = false
                    producerEt.isEnabled = editTask
                    yearLaunchEt.isEnabled = editTask
                    durationMinutesEt.isEnabled = editTask
                    scoreSp.isEnabled = editTask
                    watchedCb.isEnabled = editTask
                    movieGenreSp.isEnabled = editTask
                    saveBt.visibility = if (editTask) VISIBLE else GONE
                }
            }
        }

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
                val selected = genderList[position]
                Toast.makeText(requireActivity(), "Gênero selecionadoaaaaaaaaa: ${selected?.name}", Toast.LENGTH_SHORT).show()

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
            val movieScore = fragmentMovieBinding.scoreSp.selectedItem
            val movieWatched = fragmentMovieBinding.watchedCb.isChecked
            val movieGenre = fragmentMovieBinding.movieGenreSp.selectedItemPosition
            println("movieGenre - $movieGenre")

            if (!movieName.isNullOrEmpty() && !movieYear.isNullOrEmpty() && !movieDuration.isNullOrEmpty() )
            {
                setFragmentResult(MOVIE_FRAGMENT_REQUEST_KEY, Bundle().apply {
                    putParcelable(
                        EXTRA_MOVIE, Movie(
                            name = movieName,
                            yearLaunch = movieYear.toInt(),
                            producer = movieProducer,
                            durationMinutes = movieDuration.toInt(),
                            watched = movieWatched,
                            score = movieScore.toString().toInt(),
                            movieGenresPosition = movieGenre.toString().toInt()
                        )
                    )
                })
                findNavController().navigateUp()
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

    override fun onGenderClick(position: Int) {
        fragmentMovieBinding.movieGenreSp.setSelection(position)
        gendersAdapter.notifyDataSetChanged()

    }
}