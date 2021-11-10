package com.finesaaa.jsonparserapp.model

import androidx.annotation.Keep
import java.io.Serializable
import com.google.gson.annotations.SerializedName

@Keep
data class CaseModel(
  val active: Int ,
  val activePerOneMillion: Double,
  val cases: Int,
  val casesPerOneMillion: Double,
  val continent: String,
  val country: String,
  val countryInfo: CountryInfo,
  val critical: Int,
  val criticalPerOneMillion: Double,
  val deaths: Int,
  val deathsPerOneMillion: Double,
  val oneCasePerPeople: Int,
  val oneDeathPerPeople: Int,
  val oneTestPerPeople: Int,
  val population: Int,
  val recovered: Int,
  val recoveredPerOneMillion: Double,
  val tests: Int,
  val testsPerOneMillion: Double,
  val todayCases: Int,
  val todayDeaths: Int,
  val todayRecovered: Int,
  val updated: Long
) : Serializable  {
  data class CountryInfo(
    val flag: String,
    @SerializedName("_id")
    val id: Int
  )
}