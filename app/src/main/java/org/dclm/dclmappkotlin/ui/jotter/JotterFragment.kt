package org.dclm.dclmappkotlin.ui.jotter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.dclm.dclmappkotlin.R

class JotterFragment : Fragment() {

    companion object {
        fun newInstance() = JotterFragment()
    }

    private lateinit var viewModel: JotterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.jotter_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JotterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
