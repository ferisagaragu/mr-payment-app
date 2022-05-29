package org.pechblenda.mrpaymentapp.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.PATCH
import retrofit2.http.Body

import org.pechblenda.mrpaymentapp.entity.rest.RestResponse

import java.util.UUID

interface PaymentService {

	@GET("payments/{periodUuid}")
	fun findAllPaymentsByPeriodUuid(@Path("periodUuid") uuid: UUID): Call<RestResponse>

	@PATCH("payments")
	fun setPaymentPay(@Body paymentStatus: MutableMap<String, Any>): Call<RestResponse>

}