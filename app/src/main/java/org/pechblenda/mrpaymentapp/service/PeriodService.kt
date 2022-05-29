package org.pechblenda.mrpaymentapp.service

import retrofit2.Call
import retrofit2.http.GET

import org.pechblenda.mrpaymentapp.entity.rest.RestResponse

interface PeriodService {

	@GET("periods")
	fun findAllPeriods(): Call<RestResponse>

}