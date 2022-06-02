package org.pechblenda.mrpaymentapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import org.pechblenda.mrpaymentapp.entity.rest.RestResponse
import org.pechblenda.mrpaymentapp.handle.DetailPeriodHandle
import org.pechblenda.mrpaymentapp.handle.ListPeriodHandle
import org.pechblenda.mrpaymentapp.network.RetrofitConnector
import org.pechblenda.mrpaymentapp.service.PeriodService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

	private val listPeriodHandle: ListPeriodHandle by viewModels()
	private val detailPeriodHandle: DetailPeriodHandle by viewModels()

	private lateinit var retrofit: Retrofit
	private lateinit var periodService: PeriodService

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		retrofit = RetrofitConnector.initRetrofit()
		periodService = retrofit.create(PeriodService::class.java)
	}

	override fun onStart() {
		super.onStart()
		findAllPeriods()

		listPeriodHandle.refresh.observe(this) {
			findAllPeriods()
		}
	}

	private fun findAllPeriods() {
		periodService.findAllPeriods().enqueue(object : Callback<RestResponse> {
			override fun onResponse(call: Call<RestResponse>, response: Response<RestResponse>) {
				val resp = response.body()
				listPeriodHandle.setPeriods(resp?.convertDataToPeriods()!!)
				detailPeriodHandle.setDetailPeriod(resp?.getPeriodDetail()!!)
			}

			override fun onFailure(call: Call<RestResponse>, t: Throwable) {
				Toast.makeText(
					applicationContext,
					"Oops no pudimos establecer conexión con el servidor",
					Toast.LENGTH_SHORT
				).show()
				listPeriodHandle.onError()
			}
		})
	}

}
