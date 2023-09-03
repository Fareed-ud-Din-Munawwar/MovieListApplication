package com.example.assignmentoptimized.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentoptimized.databinding.RecyclerviewitemBinding
import com.example.assignmentoptimized.dataobjects.MovieUI
import com.example.assignmentoptimized.localdatabse.Movie

class MovieListRecyclerView(
    val onMovieSelected: (Movie) -> Unit,
    val onMovieHold: (Movie) -> Unit
) : Filterable,
    ListAdapter<Movie, MovieListRecyclerView.MyRecyclerViewHolder>(MovieDiffCallBack()) {

    var moviesList = listOf<Movie>()

    inner class MyRecyclerViewHolder(private val binding: RecyclerviewitemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateView(movie: Movie) {
            binding.root.setOnClickListener {

                onMovieSelected.invoke(movie)
            }

            binding.root.setOnLongClickListener {
                if (binding.checkBox.visibility == View.GONE) {
                    binding.checkBox.visibility = View.VISIBLE

                } else {
                    binding.checkBox.visibility = View.GONE
                }

                onMovieHold.invoke(movie)
                //it.isSelected = true
                true
            }
            binding.checkBox.visibility = View.GONE
            binding.movieModel = MovieUI(movie)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerViewHolder {

        return MyRecyclerViewHolder(
            RecyclerviewitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyRecyclerViewHolder, position: Int) {
        Log.d("list", currentList[0].title + ", " + moviesList[0].title)
        Log.d("list", currentList[position].title)
        holder.populateView(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun submitData(list: List<Movie>?) {
        moviesList = list ?: emptyList()
        submitList(moviesList)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(Str: CharSequence?): FilterResults {
                val newList: List<Movie> = moviesList.filter {
                    it.title.contains(Str!!, true)
                }

                return FilterResults().apply { values = newList }

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                //notify here
                val list = if (results?.values == null)
                    listOf()
                else
                    results.values as List<Movie>

                submitList(list)
            }
        }
    }

    class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}


