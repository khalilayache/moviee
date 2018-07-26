package com.arctouch.codechallenge.repository

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.Observable

interface MovieRepository {
  fun getUpcomingMovies(apiKey: String,language: String, page: Long, region: String) : Observable<UpcomingMoviesResponse>
  fun movieDetails(id: Long, apiKey: String, language: String): Observable<Movie>
  fun getGenreList(apiKey: String, language: String) : Observable<GenreResponse>

}
