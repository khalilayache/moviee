package com.arctouch.codechallenge.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.base.BaseActivity
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.extension.showToast
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.ui.adapter.HomeAdapter
import com.arctouch.codechallenge.ui.adapter.HomeAdapter.ItemClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_activity.progressBar
import kotlinx.android.synthetic.main.home_activity.recyclerView

class HomeActivity : BaseActivity(), ItemClick{

  private val toast by lazy { Toast(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.home_activity)

    api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          val moviesWithGenres = it.results.map { movie ->
            movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
          }
          recyclerView.adapter = HomeAdapter(moviesWithGenres, this@HomeActivity)
          progressBar.visibility = View.GONE
        }


    /*SplashActivity Code

             api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe {
              Cache.cacheGenres(it.genres)
              startActivity(Intent(this, HomeActivity::class.java))
              finish()
          }*/

  }


  override fun itemClick(movie: Movie) {
    toast.showToast(this, movie.title, Toast.LENGTH_SHORT)
  }
}
