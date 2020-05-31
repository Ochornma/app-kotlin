package org.dclm.dclmappkotlin.ui.ondemand.search

import android.os.Build
import android.text.Html

data class Search(
var id: Int,
var title: String,
var subTitle: String,
var audioUrl: String,
var urlHigh: String,
var urlLow: String
) {
    var heading: String = title
    get() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
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
}