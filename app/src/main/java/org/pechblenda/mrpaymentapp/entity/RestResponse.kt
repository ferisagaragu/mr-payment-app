package org.pechblenda.mrpaymentapp.entity

import org.pechblenda.mrpaymentapp.util.transform.JSONFormat

class RestResponse(
	val timestamp: String,
	val status: Int,
	val count	:	Int,
	private val data: Any
) {

	fun convertDataToPeriods(): List<Period> {
		return (data as List<Any?>).map { item ->
			JSONFormat.gsonInstance().fromJson(
				JSONFormat.generateJSONText(item),
				Period::class.java
			)
		}
	}

}