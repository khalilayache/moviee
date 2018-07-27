package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

  @GET("movie/{id}")
  fun details(
      @Path("id") id: Long,
      @Query("api_key") apiKey: String
  ): Observable<Movie>

  @GET("movie/upcoming")
  fun upcoming(
      @Query("api_key") apiKey: String,
      @Query("page") page: Long
  ): Observable<UpcomingMoviesResponse>
}
