package org.pechblenda.mrpaymentapp.service

import retrofit2.Call
import retrofit2.http.GET

import org.pechblenda.mrpaymentapp.entity.RestResponse

interface PeriodService {

	@GET("periods")
	fun listRepos(): Call<RestResponse>

}