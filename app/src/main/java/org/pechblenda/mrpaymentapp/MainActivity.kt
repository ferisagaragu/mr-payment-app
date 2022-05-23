package org.pechblenda.mrpaymentapp

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.pechblenda.mrpaymentapp.adapter.HttpConnector
import org.pechblenda.mrpaymentapp.adapter.ListViewAdapter
import org.pechblenda.mrpaymentapp.entity.Period
import org.pechblenda.mrpaymentapp.entity.RestResponse
import org.pechblenda.mrpaymentapp.service.PeriodService
import retrofit2.Call
import retrofit2.Retrofit
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity() {

	private val token = "eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6W10sInN1YiI6ImZlcm5ueXBheTk1IiwiaWF0IjoxNjUzMjY1MzgwLCJleHAiOjE2NTMyODMzODB9.5Jh0AV_jLezqsMiVXtQzPbpkGL9hhJGhYT4B01UgZOShLMIViDshz_v1V__qdIs87MQPPwlUV0yy4gVE0a279w"

	private lateinit var retrofit: Retrofit
	private lateinit var executor: ExecutorService
	private lateinit var recyclerView: RecyclerView
	private lateinit var call: Call<RestResponse>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		//REMOVE THIS AND USE REAL PERMISSIONS
		//val policy = ThreadPolicy.Builder().permitAll().build()
		//StrictMode.setThreadPolicy(policy)
		//===================================

		retrofit = HttpConnector().initRetrofit()
		executor = Executors.newSingleThreadExecutor()
		recyclerView = findViewById(R.id.listItem)
		val linearLayoutManager = LinearLayoutManager(this)
		linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
		recyclerView.layoutManager = linearLayoutManager
		var listViewAdapter = ListViewAdapter(listOf())
		recyclerView.adapter = listViewAdapter

		val periodService: PeriodService = retrofit.create(PeriodService::class.java)
		call = periodService.listRepos(token)

		GlobalScope.launch {
			val adapter = ListViewAdapter(doNetworkCall())

			runOnUiThread {
				findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
				recyclerView.adapter = adapter
			}
		}
	}

	fun doNetworkCall(): List<Period> {
		val response = call.execute().body()

		val data = (response?.data as List<Any?>).map { item ->
			val gson = Gson()
			val json = gson.toJson(item)
			gson.fromJson(json, Period::class.java)
		}

		return data
	}

}