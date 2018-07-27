package com.arctouch.codechallenge.di.component

import com.arctouch.codechallenge.di.module.DetailsModule
import com.arctouch.codechallenge.di.module.RepositoryModule
import com.arctouch.codechallenge.ui.activity.DetailsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        DetailsModule::class,
        RepositoryModule::class)
)
interface DetailsComponent {
  fun inject(activity: DetailsActivity)
}
