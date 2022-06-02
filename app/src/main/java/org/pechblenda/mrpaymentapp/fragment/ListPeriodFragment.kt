package org.pechblenda.mrpaymentapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import org.pechblenda.mrpaymentapp.R
import org.pechblenda.mrpaymentapp.adapter.PeriodListViewAdapter
import org.pechblenda.mrpaymentapp.handle.ListPeriodHandle
import org.pechblenda.mrpaymentapp.interfaces.RecycleViewListener

import org.pechblenda.mrpaymentapp.activity.PaymentActivity

import java.util.UUID

class ListPeriodFragment : Fragment(), RecycleViewListener {

	private val listPeriodHandle: ListPeriodHandle by activityViewModels()

	private lateinit var periodList: RecyclerView
	private lateinit var swipePeriodList: SwipeRefreshLayout
	private lateinit var retryMessage: TextView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val inflater = inflater.inflate(R.layout.fragment_list_period, container, false)

		periodList = inflater.findViewById(R.id.periodList)
		swipePeriodList = inflater.findViewById(R.id.swipePeriodList)
		retryMessage = inflater.findViewById(R.id.retryMessage)

		swipePeriodList.isRefreshing = true
		periodList.layoutManager = LinearLayoutManager(this.context)
		periodList.adapter = PeriodListViewAdapter(listOf(), this)

		return inflater
	}

	override fun onStart() {
		super.onStart()
		addOnHandle()
	}

	override fun onClick(item: Any, status: Any) {
		val bundle = Bundle()
		val intent = Intent(this.context, PaymentActivity::class.java)

		bundle.putString("periodUuid", (item as UUID).toString())
		intent.putExtras(bundle)

		this.startActivity(intent)
	}

	private fun addOnHandle() {
		listPeriodHandle.periods.observe(viewLifecycleOwner) { periods ->
			periodList.layoutManager = LinearLayoutManager(this.context)
			periodList.adapter = PeriodListViewAdapter(periods, this)
			swipePeriodList.isRefreshing = false
			retryMessage.visibility = View.GONE
		}

		swipePeriodList.setOnRefreshListener {
			listPeriodHandle.onRefresh()
		}

		listPeriodHandle.error.observe(viewLifecycleOwner) {
			swipePeriodList.isRefreshing = false
			retryMessage.visibility = View.VISIBLE
		}
	}


}