package org.pechblenda.mrpaymentapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import org.pechblenda.mrpaymentapp.R
import org.pechblenda.mrpaymentapp.entity.Period

import java.text.DecimalFormat

class ListViewAdapter(private val itemList: List<Period>): RecyclerView.Adapter<ViewHolder>() {

	private val debtFormat: DecimalFormat = DecimalFormat("#,###.## MNX")
	private var individualFormat: DecimalFormat = DecimalFormat("#,### MNX")

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.period_element,
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = itemList[position]

		holder.name.text = item.name
		holder.individual.text = individualFormat.format(item.individual).toString()
		holder.freeMoney.text = debtFormat.format(item.freeMoney).toString()

		if (item.remainingDebt == 0.0) {
			holder.paid.visibility = View.VISIBLE
			holder.remainingDebt.text = ""
		} else {
			holder.paid.visibility = View.GONE
			holder.remainingDebt.text = debtFormat.format(item.remainingDebt).toString()
		}
	}

	override fun getItemCount(): Int {
		return itemList.size
	}

}