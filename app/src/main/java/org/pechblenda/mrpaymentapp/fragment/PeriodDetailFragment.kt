package org.pechblenda.mrpaymentapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import org.pechblenda.mrpaymentapp.R
import org.pechblenda.mrpaymentapp.util.format.Currency
import org.pechblenda.mrpaymentapp.util.transform.JSONFormat

class PeriodDetailFragment: Fragment() {

	lateinit var unique: TextView
	lateinit var monthly: TextView
	lateinit var recurret: TextView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val inflater = inflater.inflate(R.layout.period_detail, container, false)

		unique = inflater.findViewById(R.id.unique)
		monthly = inflater.findViewById(R.id.monthly)
		recurret = inflater.findViewById(R.id.recurrent)

		return inflater
	}

	override fun onStart() {
		super.onStart()
		val data = JSONFormat.gsonInstance().fromJson<Map<String, Double>>(
			arguments?.getString("detail"),
			Map::class.java
		)

		unique.text = Currency.withoutDecimalPesos.format(data["unique"])
		monthly.text = Currency.withoutDecimalPesos.format(data["monthly"])
		recurret.text = Currency.withoutDecimalPesos.format(data["recurrent"])
	}

}