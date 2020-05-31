package org.dclm.dclmappkotlin.ui.experience

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.dclm.dclmappkotlin.R

class ExperienceFragment : Fragment() {

    companion object {
        fun newInstance() = ExperienceFragment()
    }

    private lateinit var viewModel: ExperienceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.experience_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExperienceViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
