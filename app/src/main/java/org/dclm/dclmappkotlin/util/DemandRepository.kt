package org.dclm.dclmappkotlin.util

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import org.dclm.dclmappkotlin.R
import org.dclm.dclmappkotlin.ui.blog.Blog
import org.dclm.dclmappkotlin.ui.listen.SubTitle
import org.dclm.dclmappkotlin.ui.ondemand.OnDemand
import org.dclm.dclmappkotlin.ui.ondemand.search.Search
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


class DemandRepository {
    private var mQueue: RequestQueue? = null
    private val blogs: List<OnDemand>? = null
    private var context: Context? = null
    private var networkCall: PodcastNetworkCall? = null
    private var searchNetworkCall: SearchNetworkCall? = null
    private var networkCal: NetworkCall? = null
    private var subtitleReceived: SubtitleRecieved? = null

    fun getVolley(){
            mQueue = context?.let { VolleyRequest.invoke(it) }
    }


    constructor(context: Context, networkCall: PodcastNetworkCall) {
        //mQueue = Volley.newRequestQueue(context.getApplicationContext());
        // mQueue = VolleyRequest.getVolley(context!!.applicationContext)
        this.context = context
        this.networkCall = networkCall
        getVolley()
    }

    constructor(context: Context, networkCall: SearchNetworkCall) {
        // mQueue = Volley.newRequestQueue(context.getApplicationContext());
        //mQueue = VolleyRequest.getVolley(context!!.applicationContext)
        this.context = context
        searchNetworkCall = networkCall
        getVolley()

    }

    constructor(context: Context, networkCall: NetworkCall) {
        this.context = context
        networkCal = networkCall
        getVolley()
        // mQueue = VolleyRequest.getVolley(context!!.applicationContext)
    }

    constructor(context: Context, networkcall: SubtitleRecieved){
        this.context = context
        this.subtitleReceived = networkcall;
        getVolley()
    }

    fun categoryJson() {
        //val cat = ArrayList<OnDemand.Category>()
        val categories: MutableList<OnDemand.Category> = ArrayList()
        val request = JsonArrayRequest(Request.Method.GET, "https://api.dclmict.org/v1/sermon/category", null, Response.Listener {
                for (i in 0 until it.length()) {
                    val jsonObject = it.getJSONObject(i)
                    val id = jsonObject.getInt("categoryId")
                    val category = jsonObject.getString("categoryName")
                    categories.add(OnDemand.Category(id, category))
                }
                networkCall?.categoryRecieved(categories)
            },
            Response.ErrorListener { })
        mQueue?.add(request)
    }

    fun jsonParse(url: String, page: Int, languageID: Int, cattegories: MutableList<OnDemand.Category>) {
        val onDemand: MutableList<OnDemand> = ArrayList()
        val checksatate:MutableList<OnDemand.CheckState> = mutableListOf()
        val request = JsonObjectRequest(Request.Method.GET, url + page, null, Response.Listener {
            try {
                val jsonObject = it.getJSONObject("meta")
                val count:Int = Integer.parseInt(jsonObject.getString("count"))
                var count2 = count - ((page - 1) * 15) - 1
                for (i in 0 until 20){
                    val object2: JSONObject = it.getJSONObject("result")
                    val object3 = object2.getJSONObject("data")
                    try {
                        val object4 = object3.getJSONObject(count2.toString())
                        val sermonTitle = object4.getString("sermonTitle")
                        val date = object4.getString("sermonDate")
                        val sermonLow = object4.getString("sermonLow")
                        val sermonHigh = object4.getString("sermonHigh")
                        val sermonAudio = object4.getString("sermonAudio")
                        val languageId = object4.getInt("languageId")
                        val category = object4.getInt("categoryId")
                        if (languageId == languageID) {
                            if (languageId == 1){
                                onDemand.add(OnDemand(sermonTitle, date, sermonHigh, sermonLow, sermonAudio, cattegories[category - 1].category))
                            } else{
                                onDemand.add(OnDemand(sermonTitle, date, sermonLow, sermonHigh, sermonAudio, cattegories[category - 1].category))
                            }
                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    count2--
                }
            }catch (e : Exception){
                e.printStackTrace()
            }
            for (i in 0 until onDemand.size){
                checksatate.add(OnDemand.CheckState(i, false))
                networkCall?.podcastRecieved(onDemand, checksatate)
            }
        }, Response.ErrorListener {
            networkCall?.error(false)
        })
        mQueue?.add(request)
    }



    fun getEvents(page: Int) {
        val searches: MutableList<Search> = ArrayList()
        val url = "https://api.dclmict.org/v1/sermon/event"
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener {
                    for (i in it.length() - ((page - 1) * 20) downTo it.length() - (page * 20 )) {
                        try {
                            val jsonObject = it.getJSONObject(i)
                            val id = (jsonObject.getInt("eventId"))
                            val title = jsonObject.getString("eventCode")
                            val subTitle = jsonObject.getString("eventTheme")
                            searches.add(Search(id, title, subTitle, "", "", ""))
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    searchNetworkCall?.onReceived(searches)

                },
                Response.ErrorListener { error: VolleyError? ->

                }
            )
        mQueue?.add(jsonArrayRequest)
    }

    fun jsonEvent(eventID: String, languageId: String, url: String, page: Int) {
        val searches: MutableList<Search> = ArrayList()
        val jsonBody = JSONObject().apply {
            try {
                this.put("event", eventID)
                this.put("language", languageId)
            }catch (e: Exception){
                e.printStackTrace()
            }

        }
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, "$url$page", jsonBody, Response.Listener { response: JSONObject ->
                    for (i in 5000 downTo 1) {
                        try {
                            val object2 = response.getJSONObject("result")
                            val object3 = object2.getJSONObject("data")
                            try {
                                val object4 = object3.getJSONObject(i.toString())
                                val sermonTitle = object4.getString("sermonTitle")
                                val date = object4.getString("sermonDate")
                                val sermonLow = object4.getString("sermonLow")
                                val sermonHigh = object4.getString("sermonHigh")
                                val sermonAudio = object4.getString("sermonAudio")
                                if (languageId == "1") {
                                    searches.add(Search(i, sermonTitle, date, sermonAudio, sermonHigh, sermonLow))
                                } else {
                                    searches.add(Search(i, sermonTitle, date, sermonAudio, sermonLow, sermonHigh))
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        searchNetworkCall?.onReceived(searches)

                    }
                },
                Response.ErrorListener {

                }
            )
        mQueue?.add(jsonObjectRequest)
    }

    fun jsonTopic(topic: String, languageId: String, url: String, page: Int) {
        val searches: MutableList<Search> = ArrayList()
        val jsonBody = JSONObject().apply {
            try {
                this.put("title", topic)
                this.put("language", languageId)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, "$url$page", jsonBody, Response.Listener { response: JSONObject ->
                    for (i in 5000 downTo 1) {
                        try {
                            val object2 = response.getJSONObject("result")
                            val object3 = object2.getJSONObject("data")
                            try {
                                val object4 = object3.getJSONObject(i.toString())
                                val sermonTitle = object4.getString("sermonTitle")
                                val date = object4.getString("sermonDate")
                                val sermonLow = object4.getString("sermonLow")
                                val sermonHigh = object4.getString("sermonHigh")
                                val sermonAudio = object4.getString("sermonAudio")
                                if (languageId == "1") {
                                    searches.add(Search(i, sermonTitle, date, sermonAudio, sermonHigh, sermonLow))
                                } else {
                                    searches.add(Search(i, sermonTitle, date, sermonAudio, sermonLow, sermonHigh))
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        if (searchNetworkCall != null){
                            searchNetworkCall?.onReceived(searches)
                        }

                    }
                },
                Response.ErrorListener {

                }
            )
        mQueue!!.add(jsonObjectRequest)
    }

    fun getBlogs(page: Int) {
        val blog: MutableList<Blog> = java.util.ArrayList<Blog>()
        if (context != null) {
            val request = JsonArrayRequest(Request.Method.GET, "https://dclm.org/wp-json/wp/v2/posts?page=$page",
                null,
                Response.Listener { response: JSONArray ->
                    try {
                        for (i in 0 until response.length()) {
                            val jsonObject2 = response.getJSONObject(i)
                            val jsonObject = jsonObject2.getJSONObject("title")
                            val title = jsonObject.getString("rendered")
                            val time = jsonObject2.getString("date_gmt")
                            val jsonObject1 = jsonObject2.getJSONObject("content")
                            val content = jsonObject1.getString("rendered")
                            blog.add(Blog(title = title, date = time, body = content))
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    if (networkCal != null) {
                        networkCal?.blogRecieved(blog)
                    }
                },
                Response.ErrorListener { error: VolleyError ->
                    error.printStackTrace()
                    if (networkCall != null && mQueue != null) {
                        //networkCall.dataError(error.networkResponse.statusCode);
                    }
                }

            )
            mQueue!!.add(request)
        }
    }

    fun jsonParse(url: String?) {
        if (context != null) {
            val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response->
                        try {
                            val nowPlaying = response.getJSONObject("now_playing")
                            val song = nowPlaying.getJSONObject("song")
                            val minister = song.getString("artist")
                            val topic = song.getString("title")
                            val listeners = response.getJSONObject("listeners")
                            val number = listeners.getString("total")
                            var listining: String? = " "
                            listining = if (context != null) {
                                context!!.resources.getString(R.string.listning) + number
                            } else {
                                number
                            }
                           // context?.let { listining = it.resources.getString(R.string.listning) }?: number
                            if (subtitleReceived != null) {
                                val subTitle = SubTitle(topic, minister, listining!!)
                                subtitleReceived?.subtitle(subTitle)
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error->
                        error.printStackTrace()
                        val subTitle: SubTitle
                        if (context != null) {
                            subTitle = SubTitle(context!!.resources.getString(R.string.message), context!!.resources.getString(R.string.ministering), " ")
                        } else {
                            subTitle = SubTitle(" ", " ", " ")
                        }
                        if (subtitleReceived != null) {
                            subtitleReceived?.error(subTitle)
                        }
                    }
                )
            mQueue!!.add(request)
        }
    }

}
