package org.pechblenda.mrpaymentapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import org.pechblenda.mrpaymentapp.MainActivity
import org.pechblenda.mrpaymentapp.R
import org.pechblenda.mrpaymentapp.entity.Period

class PeriodListViewAdapter(private val periodList: List<Period>, private val context: MainActivity): RecyclerView.Adapter<PeriodViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodViewHolder {
		return PeriodViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.period_element,
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(periodHolder: PeriodViewHolder, position: Int) {
		val period = periodList[position]
		periodHolder.onRender(period, context)
	}

	override fun getItemCount(): Int {
		return periodList.size
	}

}