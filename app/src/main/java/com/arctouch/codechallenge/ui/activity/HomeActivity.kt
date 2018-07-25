package com.arctouch.codechallenge.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.Api
import com.arctouch.codechallenge.api.GenreApi
import com.arctouch.codechallenge.api.MoviesApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.extension.showToast
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.ui.adapter.HomeAdapter
import com.arctouch.codechallenge.ui.adapter.HomeAdapter.ItemClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_activity.progressBar
import kotlinx.android.synthetic.main.home_activity.recyclerView
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class HomeActivity : AppCompatActivity(), ItemClick {

  private val toast by lazy { Toast(this) }

  private val genresApi: GenreApi = Retrofit.Builder()
      .baseUrl(Api.BASE_URL)
      .client(OkHttpClient.Builder().build())
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()
      .create(GenreApi::class.java)

  private val upcomingApi: MoviesApi = Retrofit.Builder()
      .baseUrl(Api.BASE_URL)
      .client(OkHttpClient.Builder().build())
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()
      .create(MoviesApi::class.java)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.home_activity)


    genresApi.getList(Api.API_KEY, Api.DEFAULT_LANGUAGE)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doAfterTerminate {
          upcomingApi.upcoming(Api.API_KEY, Api.DEFAULT_LANGUAGE, 1, Api.DEFAULT_REGION)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe {
                val moviesWithGenres = it.results.map { movie ->
                  movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                }
                recyclerView.adapter = HomeAdapter(moviesWithGenres, this@HomeActivity)
                progressBar.visibility = View.GONE
              }
        }
        .subscribe {
          Cache.cacheGenres(it.genres)
        }
  }

  override fun itemClick(movie: Movie) {
    toast.showToast(this, movie.title, Toast.LENGTH_SHORT)
  }
}
