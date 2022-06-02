package org.pechblenda.mrpaymentapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import org.pechblenda.mrpaymentapp.R
import org.pechblenda.mrpaymentapp.handle.DetailPeriodHandle
import org.pechblenda.mrpaymentapp.util.format.Currency
import org.pechblenda.mrpaymentapp.util.format.DateMonth

class DetailPeriodFragment: Fragment() {

	private val detailPeriodHandle: DetailPeriodHandle by activityViewModels()

	private lateinit var periodDetailLayout: ConstraintLayout
	private lateinit var unique: TextView
	private lateinit var monthly: TextView
	private lateinit var recurret: TextView
	private lateinit var monthName: TextView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val inflater = inflater.inflate(R.layout.fragment_detail_period, container, false)

		periodDetailLayout = inflater.findViewById(R.id.detailPeriodLayout)
		unique = inflater.findViewById(R.id.unique)
		monthly = inflater.findViewById(R.id.monthly)
		recurret = inflater.findViewById(R.id.recurrent)
		monthName = inflater.findViewById(R.id.monthName)

		return inflater
	}

	override fun onStart() {
		super.onStart()

		detailPeriodHandle.detailPeriod.observe(viewLifecycleOwner) { it
			periodDetailLayout.visibility = View.VISIBLE
			unique.text = Currency.withoutDecimalPesos.format(it.unique)
			monthly.text = Currency.withoutDecimalPesos.format(it.monthly)
			recurret.text = Currency.withoutDecimalPesos.format(it.recurrent)
			monthName.text = DateMonth.generateMonthName()
		}
	}

}