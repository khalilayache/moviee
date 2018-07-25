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


class PosterAdapter(context: Context) : PagerAdapter() {

  private val resources = arrayOf(R.drawable.ant1, R.drawable.ant3)

  private var layoutInflater : LayoutInflater = LayoutInflater.from(context)

  override fun getCount(): Int {
    return resources.size
  }

  override fun isViewFromObject(view: View, `object`: Any): Boolean {
    return view === `object` as ConstraintLayout
  }

  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val itemView = layoutInflater.inflate(R.layout.poster_item, container, false)

    val imageView = itemView.findViewById(R.id.posterImageView) as ImageView
    imageView.setImageResource(resources[position])

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
