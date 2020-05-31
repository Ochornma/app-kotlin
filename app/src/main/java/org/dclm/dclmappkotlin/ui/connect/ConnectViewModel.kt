package org.dclm.dclmappkotlin.ui.connect

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import org.dclm.dclmappkotlin.R

class ConnectViewModel(var context: Context) : ViewModel() {


    fun socialMedia(socialMedia: String){
        (Intent(Intent.ACTION_VIEW, Uri.parse(socialMedia))).let { context.startActivity(it) }

    }

    fun email(){
        val intent = run {
            val  intent = Intent(Intent.ACTION_SENDTO)
           // intent.setType("message/rfc822")
            intent.type = "message/rfc822"
            intent.data = Uri.parse("mailto:ict@deeperlifeonline.org")
            intent.putExtra(Intent.EXTRA_EMAIL, "info@deeperlifeonline.org")
            intent.putExtra( Intent.EXTRA_SUBJECT,
                context.resources.getString(R.string.inquiry))
            intent
        }
        try {
            context.startActivity(Intent.createChooser(intent, "choose an email"))
        }catch (e: Exception){
            Toast.makeText(
                context,
                context.resources.getString(R.string.no_app),
                Toast.LENGTH_LONG
            ).show()
        }

    }
}