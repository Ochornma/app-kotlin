package org.dclm.dclmappkotlin.ui.home

import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import org.dclm.dclmappkotlin.R

class HomeViewModel : ViewModel() {

    fun getBottom(scrollView: ScrollView, linearLayout: LinearLayout) {
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            if (scrollView.getChildAt(0).bottom <= scrollView.height + scrollView.scrollY){
                linearLayout.visibility = View.GONE
            } else{
                linearLayout.visibility = View.VISIBLE
            }
        }
    }

    fun navigateRadio(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_radioFragment)
    }

    fun navigateWatch(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_watchFragment)
    }

    fun navigateExperience(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_experienceFragment)
    }

    fun navigateJotter(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_jotterFragment)
    }

    fun navigateDoctrine(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_doctrineFragment)
    }
}
