package org.dclm.dclmappkotlin.ui.doctrine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.dclm.dclmappkotlin.R

class DoctrineFragment : Fragment() {

    companion object {
        fun newInstance() = DoctrineFragment()
    }

    private lateinit var viewModel: DoctrineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctrine_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DoctrineViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
