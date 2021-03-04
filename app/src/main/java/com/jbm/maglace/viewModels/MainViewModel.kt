package com.jbm.maglace.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jbm.maglace.model.MyRepository
import com.jbm.maglace.model.Rink
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val myRepository: MyRepository): ViewModel() {
    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    var liveRinkList = MutableLiveData<List<Rink>>()

    init {
        myRepository.getRinksFromURL {
            var rinkList = mutableListOf<Rink>()

            for (district in it) {
                for (rink in district.patinoires) {
                    rinkList.add(rink)
                }
            }

            liveRinkList.postValue(rinkList)
        }
    }
}