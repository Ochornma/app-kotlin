package org.dclm.dclmappkotlin.ui.blog

import android.os.Build
import android.text.Html
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "blog")
data class Blog(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val title: String,
    val date: String,
    val body: String
)/*: Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0*/
{

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