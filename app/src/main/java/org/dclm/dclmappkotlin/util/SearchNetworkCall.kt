package org.dclm.dclmappkotlin.util

import org.dclm.dclmappkotlin.ui.ondemand.search.Search

interface SearchNetworkCall {
    fun onReceived(searches: List<Search>)
    fun onError()
}