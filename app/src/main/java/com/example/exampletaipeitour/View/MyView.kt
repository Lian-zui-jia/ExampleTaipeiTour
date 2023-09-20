package com.example.exampletaipeitour.View

import com.example.exampletaipeitour.Attraction
import org.json.JSONArray


interface MyView  {
    fun showData(data: JSONArray?)

    fun showAttractions(attractions: List<Attraction>)
    fun showError(message: String)

}


