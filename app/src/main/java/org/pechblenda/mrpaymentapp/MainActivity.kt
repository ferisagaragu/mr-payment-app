package org.pechblenda.mrpaymentapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import org.pechblenda.mrpaymentapp.activity.PaymentActivity
import org.pechblenda.mrpaymentapp.adapter.PeriodListViewAdapter
import org.pechblenda.mrpaymentapp.entity.Period
import org.pechblenda.mrpaymentapp.entity.rest.RestResponse
import org.pechblenda.mrpaymentapp.fragment.PeriodDetailFragment
import org.pechblenda.mrpaymentapp.interfaces.RecycleViewListener
import org.pechblenda.mrpaymentapp.network.RetrofitConnector
import org.pechblenda.mrpaymentapp.service.PeriodService
import org.pechblenda.mrpaymentapp.util.transform.JSONFormat

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity(), RecycleViewListener {

	private lateinit var periodList: RecyclerView
	private lateinit var swipePeriodList: SwipeRefreshLayout
	private lateinit var retryButton: Button

	private lateinit var retrofit: Retrofit
	private lateinit var periodService: PeriodService
	private lateinit var periodDetail: Map<Any, Any>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		periodList = findViewById(R.id.periodList)
		swipePeriodList = findViewById(R.id.swipePeriodList)
		retryButton = findViewById(R.id.retryButton)

		periodList.layoutManager = LinearLayoutManager(this)
		periodList.adapter = PeriodListViewAdapter(listOf(), this)

		retrofit = RetrofitConnector.initRetrofit()
		periodService = retrofit.create(PeriodService::class.java)

		addOnListener()
		findAllPeriods()
	}

	override fun onResume() {
		super.onResume()
		findAllPeriods()
	}

	private fun addOnListener() {
		retryButton.setOnClickListener {
			findAllPeriods()
		}

		swipePeriodList.setOnRefreshListener {
			findAllPeriods()
		}
	}

	private fun findAllPeriods() {
		val context = this
		swipePeriodList.isRefreshing = true

		periodService.findAllPeriods().enqueue(object : Callback<RestResponse> {
			override fun onResponse(call: Call<RestResponse>, response: Response<RestResponse>) {
				val resp = response.body()
				val periodListViewAdapter = PeriodListViewAdapter(
					resp?.convertDataToPeriods()!!,
					context
				)

				periodDetail = resp.getPeriodDetail()

				runOnUiThread {
					swipePeriodList.isRefreshing = false
					periodList.adapter = periodListViewAdapter

					val bundle = Bundle()
					bundle.putString("detail", JSONFormat.generateJSONText(periodDetail))

					supportFragmentManager.commit {
						setReorderingAllowed(true)
						add<PeriodDetailFragment>(R.id.fragment_container_view, args = bundle)
					}
				}
			}

			override fun onFailure(call: Call<RestResponse>, t: Throwable) {
				Toast.makeText(
					applicationContext,
					"Parece que los sistemas estan iniciando, " +
						"vuelve a intentar iniciar la app mas tarde",
					Toast.LENGTH_SHORT
				).show()

				runOnUiThread {
					retryButton.visibility = View.VISIBLE
					swipePeriodList.isRefreshing = false
				}
			}
		})
	}

	override fun onClick(item: Any, status: Any) {
		val period = item as Period
		val bundle = Bundle()
		val intent = Intent(this, PaymentActivity::class.java)

		bundle.putString("periodUuid", period.uuid.toString())
		intent.putExtras(bundle)
		this.startActivity(intent)
	}

}
