package com.arctouch.codechallenge.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.ui.adapter.PosterAdapter
import kotlinx.android.synthetic.main.activity_details.posterViewPager
import kotlinx.android.synthetic.main.activity_details.toolbar
import kotlinx.android.synthetic.main.activity_details.toolbar_layout

class DetailsActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_details)
    setSupportActionBar(toolbar)
    toolbar_layout.title = "Movie Title"

    posterViewPager.adapter = PosterAdapter(this)
  }
}
