package com.example.assignmentoptimized.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.assignmentoptimized.databinding.FragmentAddmovieBinding
import com.example.assignmentoptimized.dataobjects.MovieType
import com.example.assignmentoptimized.dataobjects.MovieUI
import com.example.assignmentoptimized.localdatabse.Movie
import com.example.assignmentoptimized.viewmodels.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMovieFragment : Fragment() {
    private var _binding: FragmentAddmovieBinding? = null
    private val binding get() = _binding!!

    val myViewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddmovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object FragmentCompanion {
        private const val imageMimeType = "image/*"
    }

    private var imageUri = ""

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.imageView3.setImageURI(it)
                imageUri = it.toString()
            }
        }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch(imageMimeType)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieModel = MovieUI()

        binding.imageView3.setOnClickListener {
            selectImageFromGallery()
        }

        binding.button.setOnClickListener {
            binding.movieModel?.let {
                addMovie(
                    name = it.title,
                    year = (binding.newyear.text.toString()),
                    description = it.overview,
                    image = imageUri
                )
            }
        }
    }

    private fun addMovie(name: String, year: String, description: String, image: String) {

        val movie = Movie(
            name, year, description, "en",
            image, 0F, MovieType.CUSTOM.toString(), false, name.hashCode().toLong()
        )

        myViewModel.addNewMovie(movie)
        findNavController().popBackStack()
    }
}