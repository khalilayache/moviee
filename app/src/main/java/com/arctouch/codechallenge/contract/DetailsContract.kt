package com.arctouch.codechallenge.contract

import com.arctouch.codechallenge.model.Movie


object DetailsContract {

  const val EXTRA_MOVIE_ID = "extra_movie_id"

  interface Presenter : PresenterActivity<View> {
    fun retryGetMovieDetails()
  }

  interface View {
    fun showLoading()
    fun hideLoading()

    fun showErrorState()
    fun hideErrorState()

    fun showMainLayout()
    fun hideMainLayout()

    fun setMovieInfos(movie: Movie, posterArrayList: ArrayList<String>)
  }

}

