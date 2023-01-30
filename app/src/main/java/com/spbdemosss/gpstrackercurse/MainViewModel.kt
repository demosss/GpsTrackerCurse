package com.spbdemosss.gpstrackercurse

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbdemosss.gpstrackercurse.location.LocationModel

class MainViewModel : ViewModel() {
    val locationUpdates = MutableLiveData<LocationModel>()
    val timeData = MutableLiveData<String>()
}