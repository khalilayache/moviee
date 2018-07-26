package com.arctouch.codechallenge.di.component

import com.arctouch.codechallenge.di.module.HomeModule
import com.arctouch.codechallenge.di.module.RepositoryModule
import com.arctouch.codechallenge.ui.activity.HomeActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        HomeModule::class,
        RepositoryModule::class)
)
interface HomeComponent {
  fun inject(activity: HomeActivity)
}
