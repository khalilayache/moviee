package com.arctouch.codechallenge.ui.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.squareup.picasso.Picasso


class PosterAdapter(context: Context) : PagerAdapter() {

  var posterArrayList: List<String> = emptyList()

  private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

  override fun getCount(): Int {
    return posterArrayList.size
  }

  override fun isViewFromObject(view: View, `object`: Any): Boolean {
    return view === `object` as ConstraintLayout
  }

  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val itemView = layoutInflater.inflate(R.layout.poster_item, container, false)

    val movieImageUrlBuilder = MovieImageUrlBuilder()
    val imageView = itemView.findViewById(R.id.posterImageView) as ImageView

    Picasso.get()
        .load(movieImageUrlBuilder.buildBackdropUrl(posterArrayList[position]))
        .placeholder(R.drawable.ic_image_placeholder)
        .into(imageView)

    container.addView(itemView)
    return itemView
  }

  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    container.removeView(`object` as ConstraintLayout)
  }

  interface ItemClick {
    fun itemClick(movie: Movie)
  }
}
