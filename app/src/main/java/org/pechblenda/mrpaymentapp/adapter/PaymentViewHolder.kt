package org.pechblenda.mrpaymentapp.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import org.pechblenda.mrpaymentapp.R
import org.pechblenda.mrpaymentapp.activity.PaymentActivity
import org.pechblenda.mrpaymentapp.entity.Payment
import org.pechblenda.mrpaymentapp.util.format.Currency

class PaymentViewHolder(paymentView: View): RecyclerView.ViewHolder(paymentView) {

	private val name: TextView
	private val quantity: TextView
	private val type: ImageView
	private val payPayment: CheckBox

	init {
		name = paymentView.findViewById(R.id.namePayment)
		quantity = paymentView.findViewById(R.id.quantityPayment)
		type = paymentView.findViewById(R.id.typePayment)
		payPayment = paymentView.findViewById(R.id.payPayment)
	}

	fun onRender(payment: Payment, context: PaymentActivity) {
		name.text = payment.name
		quantity.text = Currency.decimalPesos.format(payment.quantity)
		payPayment.isChecked = payment.pay

		when(payment.type) {
			2 -> type.setImageResource(R.drawable.calendar_arrow_right)
			4 -> type.setImageResource(R.drawable.calendar_heart)
			else -> type.setImageResource(R.drawable.calendar)
		}

		if (payment.type == 4) {
			payPayment.visibility = View.INVISIBLE
		} else {
			payPayment.visibility = View.VISIBLE
		}

		payPayment.setOnClickListener { context.onClick(payment, payPayment.isChecked) }
	}

}