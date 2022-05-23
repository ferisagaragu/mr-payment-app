package org.pechblenda.mrpaymentapp.adapter

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpConnector {

	fun initRetrofit(): Retrofit {
		return Retrofit.Builder()
			.baseUrl("https://mr-payment.herokuapp.com/rest/")
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

}