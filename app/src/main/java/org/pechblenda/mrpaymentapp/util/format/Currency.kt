package org.pechblenda.mrpaymentapp.util.format

import java.text.DecimalFormat

object Currency {
	val decimalPesos = DecimalFormat("#,###.## MNX")
	val withoutDecimalPesos = DecimalFormat("#,### MNX")
}