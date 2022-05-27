package org.pechblenda.mrpaymentapp.entity

import org.pechblenda.mrpaymentapp.util.format.DateMonth
import org.pechblenda.mrpaymentapp.util.transform.JSONFormat

class RestResponse(
	val timestamp: String,
	val status: Int,
	val count	:	Int,
	private val data: Any,
) {

	private lateinit var periodDetail: Map<Any,Any>

	fun getPeriodDetail(): Map<Any, Any> {
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

}