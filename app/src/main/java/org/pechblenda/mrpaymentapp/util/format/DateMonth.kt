package org.pechblenda.mrpaymentapp.util.format

import java.text.SimpleDateFormat
import java.util.Calendar

object DateMonth {

	fun generateMonthName(): String {
		val calendar = Calendar.getInstance()
		val dateFormat = SimpleDateFormat("MMMM yyy")
		return dateFormat.format(calendar.time)
	}

}