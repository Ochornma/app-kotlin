package org.dclm.dclmappkotlin.util

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

abstract class VolleyRequest {
   /* private var volley: RequestQueue? = null

    @Synchronized
    fun getVolley(context: Context): RequestQueue? {
        if (volley == null) {
            volley = Volley.newRequestQueue(context.applicationContext)
        }
        return volley
    }
*/
    companion object{
        @Volatile private var instance : RequestQueue? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: Volley.newRequestQueue(context.applicationContext).also {
                instance = it
            }
        }
    }

  /*  companion object{
        @Volatile private var instance : RequestQueue? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance?: synchronized(VolleyRequest::class.java){
            instance ?: Volley.newRequestQueue(context.applicationContext).also {
                instance = it
            }
        }
    }*/

}