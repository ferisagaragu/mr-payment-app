package org.pechblenda.mrpaymentapp.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.pechblenda.mrpaymentapp.R

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	val name: TextView
	val individual: TextView
	val remainingDebt: TextView
	val paid: View
	val freeMoney: TextView

	init {
		name = itemView.findViewById(R.id.periodName)
		individual = itemView.findViewById(R.id.periodIndividual)
		remainingDebt = itemView.findViewById(R.id.remainingDebt)
		paid = itemView.findViewById(R.id.paid)
		freeMoney = itemView.findViewById(R.id.freeMoney)
	}

}