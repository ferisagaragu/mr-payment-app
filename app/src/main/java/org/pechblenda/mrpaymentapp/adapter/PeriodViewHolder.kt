package org.pechblenda.mrpaymentapp.adapter

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import org.pechblenda.mrpaymentapp.R
import org.pechblenda.mrpaymentapp.entity.Period
import org.pechblenda.mrpaymentapp.fragment.ListPeriodFragment
import org.pechblenda.mrpaymentapp.util.format.Currency

class PeriodViewHolder(periodView: View): RecyclerView.ViewHolder(periodView) {

	private val periodElement: CardView
	private val name: TextView
	private val individual: TextView
	private val remainingDebt: TextView
	private val paid: View
	private val freeMoney: TextView

	init {
		periodElement = periodView.findViewById(R.id.periodElement)
		name = periodView.findViewById(R.id.periodName)
		individual = periodView.findViewById(R.id.periodIndividual)
		remainingDebt = periodView.findViewById(R.id.remainingDebt)
		paid = periodView.findViewById(R.id.paid)
		freeMoney = periodView.findViewById(R.id.freeMoney)
	}

	fun onRender(period: Period, context: ListPeriodFragment) {
		name.text = period.name
		individual.text = Currency.withoutDecimalPesos.format(period.individual).toString()
		freeMoney.text = Currency.decimalPesos.format(period.freeMoney).toString()

		if (period.remainingDebt == 0.0) {
			paid.visibility = View.VISIBLE
			remainingDebt.text = ""
		} else {
			paid.visibility = View.GONE
			remainingDebt.text = Currency.decimalPesos.format(period.remainingDebt).toString()
		}

		periodElement.setOnClickListener {
			context.onClick(period.uuid, Unit)
		}
	}
}
