package org.dclm.dclmappkotlin.ui.listen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.dclm.dclmappkotlin.util.SubtitleRecieved

class ListenFactory(val context: Context, val network:SubtitleRecieved): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListenViewModel::class.java)){
            return ListenViewModel(context, network) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}