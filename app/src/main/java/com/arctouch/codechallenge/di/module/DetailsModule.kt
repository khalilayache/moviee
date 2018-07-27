package com.arctouch.codechallenge.di.module

import com.arctouch.codechallenge.contract.DetailsContract
import com.arctouch.codechallenge.presenter.DetailsPresenter
import dagger.Module
import dagger.Provides

@Module
class DetailsModule {

  @Provides
  fun provideDetailsPresenter(detailsPresenter: DetailsPresenter): DetailsContract.Presenter = detailsPresenter
}
