package com.example.exampletaipeitour

import android.content.Context
import com.example.exampletaipeitour.View.MyView
import org.json.JSONArray

class MyPresenter(private val model: MyModel, private val view: MyView) {

    fun getContext(context: Context?) {
        model.initContext(context)
    }

    fun fetchData() {
        model.fetchData(object : MyModel.ApiCallback {
            override fun onDataReceived(data: JSONArray?) {
                view.showData(data)
            }

            override fun onError(error: String) {
                view.showError(error)
            }
        })
    }

    interface DataArrayCallback {
        fun onDataArrayReceived(dataArray: JSONArray?)
    }

    fun getDataArray(callback: DataArrayCallback) {
        model.fetchData(object : MyModel.ApiCallback {
            override fun onDataReceived(data: JSONArray?) {
                callback.onDataArrayReceived(data)
            }

            override fun onError(error: String) {
                view.showError(error)
            }
        })
    }

}