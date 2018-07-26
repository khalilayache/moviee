package com.arctouch.codechallenge.repository

import com.arctouch.codechallenge.api.GenreApi
import com.arctouch.codechallenge.api.MoviesApi
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.Observable
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val genreApi: GenreApi,
    private val moviesApi: MoviesApi
) : MovieRepository {
  override fun getUpcomingMovies(apiKey: String, language: String, page: Long, region: String): Observable<UpcomingMoviesResponse> {
    return moviesApi.upcoming(apiKey, language, page, region)
  }

  override fun movieDetails(id: Long, apiKey: String, language: String): Observable<Movie> {
    return moviesApi.details(id, apiKey, language)
  }

  override fun getGenreList(apiKey: String, language: String): Observable<GenreResponse> {
    return genreApi.getList(apiKey, language)
  }
}
