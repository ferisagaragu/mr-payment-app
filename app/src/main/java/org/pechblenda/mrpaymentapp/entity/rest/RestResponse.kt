package org.pechblenda.mrpaymentapp.entity.rest

import org.pechblenda.mrpaymentapp.entity.DetailPeriod
import org.pechblenda.mrpaymentapp.entity.Payment
import org.pechblenda.mrpaymentapp.entity.Period
import org.pechblenda.mrpaymentapp.util.format.DateMonth
import org.pechblenda.mrpaymentapp.util.transform.JSONFormat

class RestResponse(
	val timestamp: String,
	val status: Int,
	val count	:	Int,
	private val data: Any,
) {

	private lateinit var periodDetail: DetailPeriod

	fun getPeriodDetail(): DetailPeriod {
		return periodDetail
	}

	fun convertDataToPeriods(): List<Period> {
		return (data as List<Any?>).map { item ->
			val data = JSONFormat.gsonInstance().fromJson(
				JSONFormat.generateJSONText(item),
				Period::class.java
			)

			if (data.name == DateMonth.generateMonthName()) periodDetail = data.detail

			data
		}
	}

	fun convertDataToPayment(): List<Payment> {
		return (data as List<Any?>).map { item ->
			JSONFormat.gsonInstance().fromJson(
				JSONFormat.generateJSONText(item),
				Payment::class.java
			)
		}
	}

}