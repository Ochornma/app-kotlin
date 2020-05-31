package org.dclm.dclmappkotlin.ui.subscribe

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.dclm.dclmappkotlin.R

class SubscribeFragment : Fragment() {

    companion object {
        fun newInstance() = SubscribeFragment()
    }

    private lateinit var viewModel: SubscribeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subscribe_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SubscribeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
