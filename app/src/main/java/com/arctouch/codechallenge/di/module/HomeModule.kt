package com.arctouch.codechallenge.di.module

import com.arctouch.codechallenge.contract.HomeContract
import com.arctouch.codechallenge.presenter.HomePresenter
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

  @Provides
  fun provideHomePresenter(homePresenter: HomePresenter): HomeContract.Presenter = homePresenter
}
