package com.arctouch.codechallenge.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.genresTextView
import kotlinx.android.synthetic.main.movie_item.view.posterImageView
import kotlinx.android.synthetic.main.movie_item.view.releaseDateTextView
import kotlinx.android.synthetic.main.movie_item.view.titleTextView

class HomeAdapter(private val clickListener: ItemClick) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

  var movies: List<Movie> = emptyList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(movies[position])
    holder.itemView.setOnClickListener { clickListener.itemClick(movies[position]) }
  }

  override fun getItemCount() = movies.size

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val movieImageUrlBuilder = MovieImageUrlBuilder()

    fun bind(movie: Movie) {
      itemView.titleTextView.text = movie.title
      itemView.genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
      itemView.releaseDateTextView.text = movie.releaseDate

      Picasso.get()
          .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
          .placeholder(R.drawable.image_not_available_placeholder)
          .into(itemView.posterImageView)
    }
  }

  interface ItemClick {
    fun itemClick(movie: Movie)
  }
}
