package org.dclm.dclmappkotlin.ui.listen

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.dclm.dclmappkotlin.R
import org.dclm.dclmappkotlin.databinding.ListenFragmentBinding
import org.dclm.dclmappkotlin.util.SubtitleRecieved

class ListenFragment : Fragment(), SubtitleRecieved{


    companion object {
        fun newInstance() = ListenFragment()
    }

    var state: Boolean = false
    var mBound = false
    var link: String? = null
    private val url: String? = null
    private lateinit var radioService: DCLMRadioService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder2 = service as DCLMRadioService.RadioBinder
            radioService = binder2.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    private lateinit var viewModel: ListenViewModel
    private lateinit var binding:ListenFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.listen_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = activity?.let { ListenFactory(it, this) }
        viewModel = factory?.let { ViewModelProvider(this, it).get(ListenViewModel::class.java) }!!
       binding.viewmodel = viewModel
        binding.state = state
        viewModel.checkButton.observe(this.viewLifecycleOwner, Observer {

        })
    }

    override fun subtitle(subTitles: SubTitle) {
        binding.data = subTitles
    }

    override fun error(subTitles: SubTitle?) {
        binding.data = subTitles
    }

}


