package com.arctouch.codechallenge.di.module

import com.arctouch.codechallenge.api.Api
import com.arctouch.codechallenge.api.GenreApi
import com.arctouch.codechallenge.api.MoviesApi
import com.arctouch.codechallenge.repository.MovieRepository
import com.arctouch.codechallenge.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {

  @Provides
  @Singleton
  fun providesMovieRepository(
      genreApi: GenreApi,
      moviesApi: MoviesApi): MovieRepository {
    return MovieRepositoryImpl(genreApi, moviesApi)
  }

  @Provides
  @Singleton
  fun providesGenreApi(retrofit: Retrofit): GenreApi{
    return retrofit.create(GenreApi::class.java)
  }

  @Provides
  @Singleton
  fun providesMovieApi(retrofit: Retrofit): MoviesApi {
    return retrofit.create(MoviesApi::class.java)
  }

  @Provides
  @Singleton
  fun providesRetroFit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
  }
}
