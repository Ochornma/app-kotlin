package org.dclm.dclmappkotlin.ui.connect

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ConnectFactory(var context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConnectViewModel::class.java)){
            return ConnectViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}