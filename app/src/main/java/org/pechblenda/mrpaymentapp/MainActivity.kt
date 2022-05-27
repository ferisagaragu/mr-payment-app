package org.pechblenda.mrpaymentapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import org.pechblenda.mrpaymentapp.adapter.PeriodListViewAdapter
import org.pechblenda.mrpaymentapp.entity.RestResponse
import org.pechblenda.mrpaymentapp.fragment.PeriodDetailFragment
import org.pechblenda.mrpaymentapp.network.RetrofitConnector
import org.pechblenda.mrpaymentapp.service.PeriodService
import org.pechblenda.mrpaymentapp.util.transform.JSONFormat

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

	private lateinit var recyclerView: RecyclerView
	private lateinit var progressBar: ProgressBar
	private lateinit var retryButton: Button

	private lateinit var retrofit: Retrofit
	private lateinit var periodService: PeriodService
	private lateinit var periodDetail: Map<Any, Any>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		recyclerView = findViewById(R.id.listItem)
		progressBar = findViewById(R.id.progressBar)
		retryButton = findViewById(R.id.retryButton)

		recyclerView.layoutManager = LinearLayoutManager(this)
		recyclerView.adapter = PeriodListViewAdapter(listOf(), this)

		retrofit = RetrofitConnector.initRetrofit()
		periodService = retrofit.create(PeriodService::class.java)

		addOnListener()
		findAllPeriods()
	}

	private fun addOnListener() {
		retryButton.setOnClickListener {
			retryButton.visibility = View.GONE
			progressBar.visibility = View.VISIBLE
			findAllPeriods()
		}
	}

	private fun findAllPeriods() {
		val context = this

		periodService.listRepos().enqueue(object : Callback<RestResponse> {
			override fun onResponse(call: Call<RestResponse>, response: Response<RestResponse>) {
				val resp = response.body()
				val periodListViewAdapter = PeriodListViewAdapter(
					resp?.convertDataToPeriods()!!,
					context
				)

				periodDetail = resp.getPeriodDetail()

				runOnUiThread {
					progressBar.visibility = View.GONE
					recyclerView.adapter = periodListViewAdapter

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
					progressBar.visibility = View.GONE
					retryButton.visibility = View.VISIBLE
				}
			}
		})
	}

}
