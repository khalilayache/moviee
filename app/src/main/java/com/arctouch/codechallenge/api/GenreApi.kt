package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.model.GenreResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreApi {

  @GET("genre/movie/list")
  fun getList(
      @Query("api_key") apiKey: String,
      @Query("language") language: String
  ): Observable<GenreResponse>
}
