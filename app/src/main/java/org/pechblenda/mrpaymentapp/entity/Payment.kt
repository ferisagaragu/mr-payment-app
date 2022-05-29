package org.pechblenda.mrpaymentapp.entity

import java.util.UUID
import java.util.Date

class Payment(
	val uuid: UUID,
	val name: String,
	val quantity: Double,
	val startDate: Date?,
	val endDate: Date?,
	val payDate: Date?,
	val totalQuantity: Double,
	val createDate: Date,
	val type: Int,
	val monthCount: Int,
	val pay: Boolean
)
