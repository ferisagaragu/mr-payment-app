package org.pechblenda.mrpaymentapp.util.transform

import com.google.gson.Gson

object JSONFormat {

	private var gson = Gson()

	fun generateJSONText(item: Any?): String {
		return gson.toJson(item)
	}

	fun gsonInstance(): Gson {
		return gson
	}

}