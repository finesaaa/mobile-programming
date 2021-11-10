package com.finesaaa.jsonparserapp.service
import com.finesaaa.jsonparserapp.model.CaseModel
import retrofit2.Call
import retrofit2.http.GET

interface CaseService {
  @GET("countries")
  fun getAffectedCountryList () : Call<List<CaseModel>>
}