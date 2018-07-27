package com.arctouch.codechallenge.presenter

import android.os.Bundle
import com.arctouch.codechallenge.api.Api
import com.arctouch.codechallenge.contract.HomeContract
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val movieRepository: MovieRepository) : HomeContract.Presenter {


  private lateinit var view: HomeContract.View
  private var moviesList: MutableList<Movie>? = null
  private var genresList: MutableList<Genre>? = null

  private var page = 1L

  override fun onCreate(savedInstanceState: Bundle?, extras: Bundle?) {
    getMoviesInfo()
  }

  override fun bindView(view: HomeContract.View) {
    this.view = view
  }

  override fun onClick(movie: Movie) {
  }

  override fun getMovieList() {
    getMoviesInfo()
  }

  override fun increasePage() {
    this.page++
  }

  private fun getMoviesInfo() {

    if (genresList == null) {
      movieRepository.getGenreList(Api.API_KEY, Api.DEFAULT_LANGUAGE)
          .doOnSubscribe {
            view.showLoading()
            view.hideRecycler()
            view.hideErrorState()
          }
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnError {
            view.showErrorState()
            view.hideLoading()
            view.hideRecycler()
          }
          .doAfterTerminate {
            getMovies()
          }
          .subscribe { genresList = it.genres as MutableList<Genre> }
    } else {
      getMovies()

    }
  }

  private fun getMovies() {
    movieRepository.getUpcomingMovies(Api.API_KEY, page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError {
          view.showErrorState()
          view.hideLoading()
          view.hideRecycler()
        }
        .doAfterTerminate { view.hideLoading() }
        .subscribe {
          val moviesListWithGender = it.results.map { movie ->
            movie.copy(genres = genresList?.filter { movie.genreIds?.contains(it.id) == true })
          }

          if (moviesList == null) {
            moviesList = moviesListWithGender as MutableList<Movie>
          } else {
            moviesList?.addAll(moviesListWithGender)
          }
          moviesList?.let { updateMovieList(it) }
        }
  }

  private fun updateMovieList(moviesList: List<Movie>) {
    view.updateMovieList(moviesList)
  }

}
