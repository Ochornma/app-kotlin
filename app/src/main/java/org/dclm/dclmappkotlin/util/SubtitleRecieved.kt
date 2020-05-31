package org.dclm.dclmappkotlin.util

import org.dclm.dclmappkotlin.ui.listen.SubTitle

interface SubtitleRecieved {
    fun subtitle(subTitles: SubTitle)
    fun error(subTitles: SubTitle?)
}