package org.dclm.dclmappkotlin.ui.ondemand

import android.os.Build
import android.text.Html

data class OnDemand(
    var title: String,
    var date: String,
    var urlHigh: String,
    var urlLow: String,
    var urlAudio: String,
    var category: String
) {
    var heading: String = title
        get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(
                    "<p align=\"justify\">" +
                            " " + field + "</p>", Html.FROM_HTML_MODE_COMPACT
                ).toString()
            }
            return Html.fromHtml(
                "<p align=\"justify\">" +
                        " " + field + "</p>"
            ).toString()
        }


    data class Category(
        var number: Int,
        var category: String
    )

    data class CheckState(
        var position: Int,
        var check: Boolean
    ) {

    }
}