package org.dclm.dclmappkotlin.ui.listen

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.dclm.dclmappkotlin.util.DemandRepository
import org.dclm.dclmappkotlin.util.SubtitleRecieved

class ListenViewModel(val context: Context,
val networkcall: SubtitleRecieved) : ViewModel() {


     var checkButton: MutableLiveData<Boolean> = MutableLiveData()
    var link: MutableLiveData<String> = MutableLiveData()
    private var repository: DemandRepository? = null

    init {
        repository = DemandRepository(context, networkcall)
    }

    fun playPause(state: Boolean){
      //  val state = ListenFragment().state
       checkButton.postValue(state)
    }

    fun jsonParse(url: String){
        repository?.jsonParse(url)
    }

}

