package org.dclm.dclmappkotlin.util

import org.dclm.dclmappkotlin.ui.ondemand.OnDemand

interface PodcastNetworkCall {
    fun podcastRecieved(onDemands: MutableList<OnDemand>, checkstates: MutableList<OnDemand.CheckState>)
    fun categoryRecieved(categories: List<OnDemand.Category>)
    fun error(menu: Boolean)
}
