package com.arctouch.codechallenge.presenter

import android.os.Bundle
import com.arctouch.codechallenge.api.Api
import com.arctouch.codechallenge.contract.DetailsContract
import com.arctouch.codechallenge.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
    private val movieRepository: MovieRepository) : DetailsContract.Presenter {

  private var movieId: Int? = null
  private lateinit var view: DetailsContract.View

  override fun onCreate(savedInstanceState: Bundle?, extras: Bundle?) {
    movieId = extras?.getInt(DetailsContract.EXTRA_MOVIE_ID)
    loadMovieDetails()
  }

  override fun retryGetMovieDetails() {
    loadMovieDetails()
  }

  private fun loadMovieDetails() {

    movieId?.let {
      movieRepository.movieDetails(it.toLong(), Api.API_KEY, Api.DEFAULT_LANGUAGE)
          .doOnSubscribe {
            view.showLoading()
            view.hideMainLayout()
            view.hideErrorState()
          }
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnError {
            view.showErrorState()
            view.hideLoading()
            view.hideMainLayout()
          }
          .doAfterTerminate {
            view.hideLoading()
            view.hideErrorState()
            view.showMainLayout()
          }
          .subscribe {
            val posterArrayList: ArrayList<String> = ArrayList()
            it.posterPath?.let {
                posterArrayList.add(it)
            }
            it.backdropPath?.let {
              posterArrayList.add(it)
            }
            view.setMovieInfos(it, posterArrayList)
          }
    }
  }

  override fun bindView(view: DetailsContract.View) {
    this.view = view
  }

}
