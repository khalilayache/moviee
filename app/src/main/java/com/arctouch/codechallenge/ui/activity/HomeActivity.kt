package com.arctouch.codechallenge.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.contract.HomeContract
import com.arctouch.codechallenge.di.component.DaggerHomeComponent
import com.arctouch.codechallenge.extension.gone
import com.arctouch.codechallenge.extension.visible
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.ui.adapter.HomeAdapter
import com.arctouch.codechallenge.ui.adapter.HomeAdapter.ItemClick
import com.arctouch.codechallenge.ui.view.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.home_activity.errorStateLayout
import kotlinx.android.synthetic.main.home_activity.progressBar
import kotlinx.android.synthetic.main.home_activity.recyclerView
import kotlinx.android.synthetic.main.home_activity.retryButton
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), ItemClick, HomeContract.View {

  @Inject
  lateinit var presenter: HomeContract.Presenter

  private val homeAdapter by lazy { HomeAdapter(this@HomeActivity) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.home_activity)
    initDagger()
    initView(savedInstanceState)
    initListeners()
  }

  private fun initListeners() {
    retryButton.setOnClickListener { presenter.getMovieList() }
  }

  override fun itemClick(movie: Movie) {
    startActivity(DetailsActivity.createIntentWithMovie(this@HomeActivity,movie.id))
  }

  override fun showLoading() {
    progressBar.visible()
  }

  override fun hideLoading() {
    progressBar.gone()
  }

  override fun showRecycler() {
    recyclerView.visible()
  }

  override fun hideRecycler() {
    recyclerView.gone()
  }

  override fun showErrorState() {
    errorStateLayout.visible()
    progressBar.gone()
  }

  override fun hideErrorState() {
    errorStateLayout.gone()
  }

  override fun updateMovieList(movieList: List<Movie>) {
    recyclerView.visible()
    homeAdapter.movies = movieList
    homeAdapter.notifyDataSetChanged()
  }

  private fun initView(savedInstanceState: Bundle?) {
    initMovieList()

    presenter.bindView(this)
    presenter.onCreate(savedInstanceState, intent.extras)
  }

  private fun initMovieList() {
    recyclerView.adapter = homeAdapter
    recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(recyclerView.layoutManager as LinearLayoutManager) {
      override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
        presenter.increasePage()
        presenter.getMovieList()
      }
    })
  }

  private fun initDagger() {
    DaggerHomeComponent.builder().build().inject(this@HomeActivity)
  }

}
