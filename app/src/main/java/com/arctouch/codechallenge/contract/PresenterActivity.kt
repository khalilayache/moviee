package com.arctouch.codechallenge.contract

interface PresenterActivity<in V> {
  fun onCreate()
  fun bindView(view: V)
}
