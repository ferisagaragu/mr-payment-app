package org.pechblenda.mrpaymentapp.entity

import java.util.UUID
import java.util.Date

class Period(
	val uuid: UUID,
	val date: Date,
	val detail: DetailPeriod,
	val name:	String,
	val save:	Double,
	val enable:	Boolean,
	val totalMoney:	Double,
	val debt:	Double,
	val biweekly:	Double,
	val individual:	Double,
	val remainingDebt: Double,
	val freeMoney: Double
)
