package com.jbm.maglace.model

import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.gson.Gson
import com.jbm.maglace.utils.Constants
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

// Repository class to access data
class MyRepository @Inject constructor() {
    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    fun getRinksFromURL(completion: (List<District>) -> Unit) {
        val request = Request.Builder()
            .url(Constants().PATINER_MONTREAL_DATA_URL)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val rinks = Gson().fromJson(responseBody, Array<District>::class.java)

                Log.d(TAG, "New Rinks downloaded and parsed = " + rinks.toString())

                completion(rinks.toList())
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "OKHTTP error. Couldn't get rinks from URL = " + e.toString())
            }
        })
    }
}