package com.arctouch.codechallenge.contract

import android.os.Bundle

interface PresenterActivity<in V> {
  fun onCreate(savedInstanceState: Bundle?, extras: Bundle?)
  fun bindView(view: V)
}
