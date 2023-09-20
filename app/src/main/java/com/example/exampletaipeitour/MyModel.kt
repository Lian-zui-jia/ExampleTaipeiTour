package com.example.exampletaipeitour

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import android.content.SharedPreferences

data class Attraction(val name: String, val introduction: String ,val open_time:String ,val modified: String ,val imgUrl:String,val url: String?, val address: String?,val imgUrlarray: String?) {
}

class MyModel {
    private lateinit var total: String
    private lateinit var data: String
    private var context: Context? = null


    fun initContext(context: Context?) {
        this.context = context
    }

    interface ApiCallback {
        fun onDataReceived(data: JSONArray?)
        fun onError(error: String)
    }

    fun fetchData(callback: ApiCallback) {
        val sharedPref = context?.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val lang: String? = sharedPref?.getString("lang", "zh-tw")
        var url: String ="https://www.travel.taipei/open-api/"+lang+"/Attractions/All?page=1"

        var request = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // 解析Json
                val data = parseResponse(response)
                callback.onDataReceived(data)
            },
            { error ->
                callback.onError(error.message ?: "Unknown error")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }

        Volley.newRequestQueue(context).add(request)
    }

    private fun parseResponse(response: JSONObject): JSONArray? {
        try {

        val total = response.optString("total", "total Not Found")
        val data = response.optString("data", "data Not Found")

        val myModel = MyModel()
        myModel.total = total
        myModel.data = data
        val jsonString = myModel.data

        val dataArray =JSONArray(jsonString)
        val modifiedJSONArray =JSONArray()

        for (i in 0 until dataArray.length()) {
            val attractionObject = dataArray.getJSONObject(i)
            //景點名
            val name = attractionObject.getString("name")
            //簡介
            val introduction =attractionObject.getString("introduction")
            //營業時間
            val open_time =attractionObject.getString("open_time")
            //最後編輯時間
            val modified =attractionObject.getString("modified")
            //獲得第一張圖片
            val imgurlArray =JSONArray(attractionObject.getString("images"))
            val imgUrl :String
            if (imgurlArray.length() >0){
                val imgurlObject = imgurlArray.getJSONObject(0)
                 imgUrl = imgurlObject?.optString("src") ?: ""
            }else{
                 imgUrl = ""
            }

            val url =attractionObject.getString("url")
            val address = attractionObject.getString("address")
//         重新建立Json只取需要值
            val modifiedJSONObject = JSONObject()
            modifiedJSONObject.put("name", name)
            modifiedJSONObject.put("introduction", introduction)
            modifiedJSONObject.put("open_time", open_time)
            modifiedJSONObject.put("modified", modified)
            modifiedJSONObject.put("imgUrl", imgUrl)
            modifiedJSONObject.put("url", url)
            modifiedJSONObject.put("address", address)
            //為了第二頁將所有圖片網址傳過去
            modifiedJSONObject.put("imgUrlarray",imgurlArray)

            modifiedJSONArray.put(modifiedJSONObject)

        }

        return modifiedJSONArray
            
        }catch (e :Exception){
            e.printStackTrace()
            Log.e("parseResponseException", e.toString())
            return null
        }
    }

}