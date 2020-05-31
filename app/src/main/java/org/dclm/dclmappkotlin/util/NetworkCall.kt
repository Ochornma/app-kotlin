package org.dclm.dclmappkotlin.util

import org.dclm.dclmappkotlin.ui.blog.Blog

interface NetworkCall {
    fun blogRecieved(blog: List<Blog>)

}