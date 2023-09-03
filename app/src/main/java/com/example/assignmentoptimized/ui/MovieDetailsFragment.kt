package com.example.assignmentoptimized.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.assignmentoptimized.databinding.FragmentMovieDetailsBinding
import com.example.assignmentoptimized.dataobjects.MovieType
import com.example.assignmentoptimized.dataobjects.MovieUI
import com.example.assignmentoptimized.viewmodels.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()

    val myViewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myViewModel.searchMovieById(args.movieDetails)
        myViewModel.searchResObserver.observe(viewLifecycleOwner) {
            if (it.tag.equals(MovieType.CUSTOM.toString(), true)) {
                binding.movieModel = MovieUI(it)
            } else {
                //get api result
                myViewModel.fetchMovieDetails(args.movieDetails)
                myViewModel.movieObserver.observe(viewLifecycleOwner) { movie ->
                    binding.movieModel = MovieUI(movie)
                }
            }
        }

        myViewModel.toastResObserver.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

    }

}
