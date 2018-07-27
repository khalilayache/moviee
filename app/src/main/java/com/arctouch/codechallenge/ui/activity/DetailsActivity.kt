package com.arctouch.codechallenge.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.contract.DetailsContract
import com.arctouch.codechallenge.di.component.DaggerDetailsComponent
import com.arctouch.codechallenge.extension.gone
import com.arctouch.codechallenge.extension.visible
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.ui.adapter.PosterAdapter
import kotlinx.android.synthetic.main.activity_details.app_bar
import kotlinx.android.synthetic.main.activity_details.errorStateLayout
import kotlinx.android.synthetic.main.activity_details.posterViewPager
import kotlinx.android.synthetic.main.activity_details.progressBar
import kotlinx.android.synthetic.main.activity_details.toolbar
import kotlinx.android.synthetic.main.activity_details.toolbar_layout
import kotlinx.android.synthetic.main.content_details.movieGenres
import kotlinx.android.synthetic.main.content_details.movieOverview
import kotlinx.android.synthetic.main.content_details.movieRelease
import kotlinx.android.synthetic.main.home_activity.retryButton
import javax.inject.Inject

class
DetailsActivity : AppCompatActivity(), DetailsContract.View {

  companion object {
    @JvmStatic
    fun createIntentWithMovie(
        context: Context,
        movieId: Int
    ): Intent {
      return Intent(context, DetailsActivity::class.java)
          .putExtra(DetailsContract.EXTRA_MOVIE_ID, movieId)
    }
  }

  @Inject
  lateinit var presenter: DetailsContract.Presenter

  private val posterAdapter by lazy { PosterAdapter(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_details)
    initDagger()
    initView(savedInstanceState)
    initToolbar()
    initListeners()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      finish()
    }
    return super.onOptionsItemSelected(item)
  }

  private fun initView(savedInstanceState: Bundle?) {
    presenter.bindView(this@DetailsActivity)
    presenter.onCreate(savedInstanceState, intent.extras)

    posterViewPager.adapter = posterAdapter
  }

  private fun initToolbar() {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
  }

  private fun initListeners() {
    retryButton.setOnClickListener { presenter.retryGetMovieDetails() }
  }

  override fun showLoading() {
    progressBar.visible()
  }

  override fun hideLoading() {
    progressBar.gone()
  }

  override fun showErrorState() {
    errorStateLayout.visible()
  }

  override fun hideErrorState() {
    errorStateLayout.gone()
  }

  override fun showMainLayout() {
    app_bar.visible()
  }

  override fun hideMainLayout() {
    app_bar.gone()
  }

  override fun setMovieInfos(movie: Movie, posterArrayList: ArrayList<String>) {
    toolbar_layout.title = movie.title

    var genreString = ""
    movie.genres?.let {
      it.forEach {
        genreString += if(genreString == ""){
          it.name
        } else {
          ", " + it.name
        }
      }
    }
    movieGenres.text = genreString

    if (genreString == "")
      movieGenres.text = getText(R.string.no_genres)
    else
      movieGenres.text = genreString

    if (movie.releaseDate == "")
      movieRelease.text = getText(R.string.no_release_date)
    else
      movieRelease.text = movie.releaseDate

    if (movie.overview == "")
      movieOverview.text = getText(R.string.no_overview)
    else
      movieOverview.text = movie.overview

    posterAdapter.posterArrayList = posterArrayList
    posterAdapter.notifyDataSetChanged()

  }

  private fun initDagger() {
    DaggerDetailsComponent.builder().build().inject(this@DetailsActivity)
  }

}
