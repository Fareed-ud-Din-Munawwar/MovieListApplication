package com.example.assignmentoptimized.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentoptimized.R
import com.example.assignmentoptimized.databinding.FragmentMovieListBinding
import com.example.assignmentoptimized.localdatabse.Movie
import com.example.assignmentoptimized.viewmodels.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    var selectedMovie = mutableListOf<Movie>()

    var mActionMode: ActionMode? = null
    var action: String = ""

    private val movieListAdapter: MovieListRecyclerView by lazy {
        MovieListRecyclerView(
            this::onMovieSelected,
            this::onMovieHold
        )
    }

    val myViewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        //setHasOptionsMenu(true)

        binding.let {
            it.recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            it.myAdapter = movieListAdapter
            //it.recyclerView.adapter = movieListAdapter
            it.editTextSearch.doOnTextChanged { text, _, _, _ ->
                movieListAdapter.filter.filter(text)
            }
            it.floatingActionButton.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddmovieFragment())
            }
        }

        myViewModel.movieListObserver.observe(viewLifecycleOwner) {
            myViewModel.movieList = it
            movieListAdapter.submitData(it)
        }

        myViewModel.movieFavListObserver.observe(viewLifecycleOwner) { favMovie ->
            myViewModel.favList = favMovie
        }

        myViewModel.toastResObserver.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        if (myViewModel.movieList.isEmpty()) {
            myViewModel.getMovieListAPI()
        }
    }

    private fun onMovieSelected(movie: Movie) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(
                movie.id
            )
        )
    }

    private fun onMovieHold(movie: Movie) {
        if (mActionMode == null) {
            mActionMode = activity?.startActionMode(callback)
        }

        if (!selectedMovie.contains((movie))) {
            selectedMovie.add(movie)
        } else {
            selectedMovie.remove(movie)
        }

        mActionMode?.title = "${selectedMovie.size} selected"
    }

    val callback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.contextualappbar, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            /*return*/ when (item?.itemId) {
                R.id.favourite -> {
                    // Handle favourite icon press
                    selectedMovie.forEach {

                        action += if (myViewModel.favList.contains(it)) {
                            myViewModel.removeFavouriteMovie(it)
                            "${it.title} removed from Favourites\n"
                        } else {
                            myViewModel.addFavouriteMovie(it)
                            "${it.title} added to Favourites\n"
                        }
                    }
                    mode?.finish()
                    //true
                }

                R.id.delete -> {
                    // Handle delete icon press
                    selectedMovie.forEach {
                        myViewModel.deleteMovie(it)
                        action += "${it.title} Deleted\n"

                    }
                    //true
                    mode?.finish()
                }
            }
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            if (action.isNotEmpty()) {
                Toast.makeText(
                    requireContext(),
                    action,
                    Toast.LENGTH_SHORT
                ).show()
            }
            action = ""
            selectedMovie.clear()
            mActionMode = null
        }
    }
}