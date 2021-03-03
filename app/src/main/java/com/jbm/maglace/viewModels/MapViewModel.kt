package com.jbm.maglace.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jbm.maglace.data.MyRepository
import com.jbm.maglace.data.District
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val myRepository: MyRepository): ViewModel() {
    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    var liveRinkList = MutableLiveData<List<District>>()

    init {
        myRepository.getRinksFromURL {
            liveRinkList.postValue(it)
        }
    }
}