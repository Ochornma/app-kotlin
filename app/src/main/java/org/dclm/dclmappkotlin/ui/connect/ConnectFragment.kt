package org.dclm.dclmappkotlin.ui.connect

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import org.dclm.dclmappkotlin.R
import org.dclm.dclmappkotlin.databinding.ConnectFragmentBinding

class ConnectFragment : Fragment() {

    companion object {
        fun newInstance() = ConnectFragment()
    }

    //private lateinit var viewModel: ConnectViewModel
    private lateinit var binding: ConnectFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.connect_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = activity?.let { ConnectFactory(it) }!!
        val viewModel =   ViewModelProvider(this, factory).get(ConnectViewModel::class.java)
        binding.viewmodel = viewModel
    }

}
