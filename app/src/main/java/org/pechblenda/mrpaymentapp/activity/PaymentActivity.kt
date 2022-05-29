package org.pechblenda.mrpaymentapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import org.pechblenda.mrpaymentapp.R
import org.pechblenda.mrpaymentapp.entity.rest.RestResponse
import org.pechblenda.mrpaymentapp.network.RetrofitConnector
import org.pechblenda.mrpaymentapp.service.PaymentService
import org.pechblenda.mrpaymentapp.adapter.PaymentListViewAdapter
import org.pechblenda.mrpaymentapp.interfaces.RecycleViewListener
import org.pechblenda.mrpaymentapp.entity.Payment

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

import java.util.UUID

class PaymentActivity: AppCompatActivity(), RecycleViewListener {

	private lateinit var paymentList: RecyclerView
	private lateinit var swipePaymentList: SwipeRefreshLayout

	private lateinit var retrofit: Retrofit
	private lateinit var periodUuid: UUID
	private lateinit var paymentService: PaymentService

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_payment)

		paymentList = findViewById(R.id.paymentList)
		swipePaymentList = findViewById(R.id.swipePaymentList)

		paymentList.layoutManager = LinearLayoutManager(this)
		paymentList.adapter = PaymentListViewAdapter(listOf(), this)

		periodUuid = UUID.fromString(intent.extras?.getString("periodUuid"))
		retrofit = RetrofitConnector.initRetrofit()
		paymentService = retrofit.create(PaymentService::class.java)

		addOnListener()
		findAllPaymentsByPeriodUuid()
	}

	private fun addOnListener() {
		swipePaymentList.setOnRefreshListener {
			findAllPaymentsByPeriodUuid()
		}
	}

	private fun findAllPaymentsByPeriodUuid() {
		val context = this
		swipePaymentList.isRefreshing = true

		paymentService.findAllPaymentsByPeriodUuid(periodUuid).enqueue(object: Callback<RestResponse> {
			override fun onResponse(call: Call<RestResponse>, response: Response<RestResponse>) {
				val resp = response.body()
				val paymentListViewAdapter = PaymentListViewAdapter(
					resp?.convertDataToPayment()!!,
					context
				)

				runOnUiThread {
					swipePaymentList.isRefreshing = false
					paymentList.adapter = paymentListViewAdapter
				}
			}

			override fun onFailure(call: Call<RestResponse>, t: Throwable) {
				Toast.makeText(
					applicationContext,
					"Por alguna razon los pagos no estan disponibles por ahora",
					Toast.LENGTH_SHORT
				).show()

				runOnUiThread {
					swipePaymentList.isRefreshing = false
				}
			}
		})
	}

	override fun onClick(item: Any, status: Any) {
		val payment = item as Payment
		val isCheck = status as Boolean

		val paymentStatus = mutableMapOf<String, Any>()
		paymentStatus["uuid"] = payment.uuid.toString()
		paymentStatus["pay"] = isCheck

		paymentService.setPaymentPay(paymentStatus).enqueue(object: Callback<RestResponse> {
			override fun onResponse(call: Call<RestResponse>, response: Response<RestResponse>) {
				runOnUiThread {
					Toast.makeText(
						applicationContext,
						"El cambio se ha aplicado",
						Toast.LENGTH_SHORT
					).show()
				}
			}

			override fun onFailure(call: Call<RestResponse>, t: Throwable) {
				runOnUiThread {
					Toast.makeText(
						applicationContext,
						"Oops por algúna razon no se puede persistir el status",
						Toast.LENGTH_SHORT
					).show()
				}
			}
		})
	}

}