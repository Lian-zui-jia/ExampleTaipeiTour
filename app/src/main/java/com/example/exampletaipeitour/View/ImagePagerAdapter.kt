package com.example.exampletaipeitour.View
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exampletaipeitour.R
import org.json.JSONArray
import org.json.JSONException

class ImagePagerAdapter(private val fragment: Fragment, private val dataArray: String?) :
    RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    private var imageArray: JSONArray? = null

    init {
        try {
            val jsonObject = JSONArray(dataArray)
            imageArray = jsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_imageview, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageObject = imageArray?.optJSONObject(position)
        val imageUrl = imageObject?.optString("src")

        if (!imageUrl.isNullOrEmpty()) {
            val imageView = holder.itemView.findViewById<ImageView>(R.id.imageView_sub)
            Glide.with(fragment)
                .load(imageUrl)
                .into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return imageArray?.length() ?: 0
    }
}