package com.spbdemosss.gpstrackercurse

import androidx.lifecycle.*
import com.spbdemosss.gpstrackercurse.db.MainDb
import com.spbdemosss.gpstrackercurse.db.TrackItem
import com.spbdemosss.gpstrackercurse.location.LocationModel
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainViewModel(db: MainDb) : ViewModel() {
    val dao = db.getDao()
    val locationUpdates = MutableLiveData<LocationModel>()
    val timeData = MutableLiveData<String>()
    val tracks = dao.getAllTracks().asLiveData()

    fun insertTrack(trackItem: TrackItem) = viewModelScope.launch {
        dao.insertTrack(trackItem)
    }

    class ViewModelFactory(private val db: MainDb) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                return MainViewModel(db) as T
            }
            throw java.lang.IllegalArgumentException("Unknown ViewModel class")

        }
    }
}