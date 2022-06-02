package org.pechblenda.mrpaymentapp.handle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import org.pechblenda.mrpaymentapp.entity.DetailPeriod

class DetailPeriodHandle: ViewModel() {

	private val mutableDetailPeriod = MutableLiveData<DetailPeriod>()

	val detailPeriod: LiveData<DetailPeriod> get() = mutableDetailPeriod

	fun setDetailPeriod(periods: DetailPeriod) {
		mutableDetailPeriod.value = periods
	}

}