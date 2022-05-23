package org.pechblenda.mrpaymentapp.service

import org.pechblenda.mrpaymentapp.entity.RestResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface PeriodService {

	@GET("periods")
	fun listRepos(@Header("Authorization") bearerToken: String): Call<RestResponse>

}