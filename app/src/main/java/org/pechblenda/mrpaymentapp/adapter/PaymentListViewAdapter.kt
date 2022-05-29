package org.pechblenda.mrpaymentapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import org.pechblenda.mrpaymentapp.R
import org.pechblenda.mrpaymentapp.activity.PaymentActivity
import org.pechblenda.mrpaymentapp.entity.Payment

class PaymentListViewAdapter(
	private val paymentList: List<Payment>,
	private val context: PaymentActivity
): RecyclerView.Adapter<PaymentViewHolder>() {

	lateinit var paymentViewHolder: PaymentViewHolder

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
		paymentViewHolder = PaymentViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.payment_element,
				parent,
				false
			)
		)

		return paymentViewHolder
	}

	override fun onBindViewHolder(paymentHolder: PaymentViewHolder, position: Int) {
		val payment = paymentList[position]
		paymentHolder.onRender(payment, context)
	}

	override fun getItemCount(): Int {
		return paymentList.size
	}

}