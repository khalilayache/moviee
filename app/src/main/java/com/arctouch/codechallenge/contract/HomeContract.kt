package com.arctouch.codechallenge.contract

import com.arctouch.codechallenge.model.Movie

object HomeContract {

  interface Presenter : PresenterActivity<View>{
    fun onClick(movie: Movie)
    fun getMovieList()

    fun increasePage()

  }

  interface View {
    fun showLoading()
    fun hideLoading()

    fun updateMovieList(movieList: List<Movie>)

    fun showErrorState()
  }

}
