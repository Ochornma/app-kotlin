package org.dclm.dclmappkotlin.ui.ondemand.video

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.dclm.dclmappkotlin.R

class VideoDemandFragment : Fragment() {

    companion object {
        fun newInstance() = VideoDemandFragment()
    }

    private lateinit var viewModel: VideoDemandViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.video_demand_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VideoDemandViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
