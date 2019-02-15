package com.example.framgianguyenthanhminhb.workmanagerdemo

import android.arch.lifecycle.MediatorLiveData

/**
 * Created by MinhNguyen on 15/02/2019.
 * nguyen.thanh.minhb@framgia.com
 */
class LiveDataHelper private constructor() {

    var progressLiveData = MediatorLiveData<Double>()

    fun updatePercentage(percentage: Double) {
        progressLiveData.postValue(percentage)
    }


    companion object {
        var liveDataHelper: LiveDataHelper? = null
        fun getInstance(): LiveDataHelper {
            if (liveDataHelper == null)
                liveDataHelper = LiveDataHelper()
            return liveDataHelper!!
        }
    }
}
