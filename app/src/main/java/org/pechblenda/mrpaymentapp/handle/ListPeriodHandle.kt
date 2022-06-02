package org.pechblenda.mrpaymentapp.handle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import org.pechblenda.mrpaymentapp.entity.Period

class ListPeriodHandle: ViewModel() {

	private val mutablePeriods = MutableLiveData<List<Period>>()
	private val mutableRefresh = MutableLiveData<Unit>()
	private val mutableError = MutableLiveData<Unit>()

	val periods: LiveData<List<Period>> get() = mutablePeriods
	val refresh: LiveData<Unit> get() = mutableRefresh
	val error: LiveData<Unit> get() = mutableError

	fun setPeriods(periods: List<Period>) {
		mutablePeriods.value = periods
	}

	fun onRefresh() {
		mutableRefresh.value = Unit
	}

	fun onError() {
		mutableError.value = Unit
	}

}