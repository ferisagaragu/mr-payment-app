package org.pechblenda.mrpaymentapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConnector {

	fun initRetrofit(): Retrofit {
		return Retrofit.Builder()
			.baseUrl("https://mr-payment.herokuapp.com/rest/")
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

}