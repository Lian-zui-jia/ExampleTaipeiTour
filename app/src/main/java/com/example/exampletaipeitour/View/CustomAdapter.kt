package com.example.exampletaipeitour.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exampletaipeitour.Attraction
import com.example.exampletaipeitour.MyPresenter
import com.example.exampletaipeitour.R
import org.json.JSONArray


class CustomAdapter(private val presenter: MyPresenter, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private var attractions: List<Attraction> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val attraction = attractions[position]
        holder.bind(attraction)
    }

    override fun getItemCount(): Int {
        return attractions.size
    }



    fun setAttractions() {
        val attractionss = mutableListOf<Attraction>()

        presenter.getDataArray(object : MyPresenter.DataArrayCallback {
            override fun onDataArrayReceived(dataArray: JSONArray?) {
                // 在这里处理获取到的 dataArray
                if (dataArray != null) {
                    // 数据不为空，可以使用它
                    // 遍历 JSONArray 中的每个对象并获取属性值
                    for (i in 0 until dataArray.length()) {
                        val attractionObject = dataArray.getJSONObject(i)

                        // 获取属性值
                        val name = attractionObject.getString("name")
                        val introduction = attractionObject.getString("introduction")
                        val open_time = attractionObject.getString("open_time")
                        val modified = attractionObject.getString("modified")
                        val imgUrl = attractionObject.getString("imgUrl")
                        val url = attractionObject.getString("url")
                        val address =attractionObject.getString("address")
                        val imgUrlarray =attractionObject.getString("imgUrlarray")
                        val attraction = Attraction(name, introduction, open_time, modified, imgUrl,url,address,imgUrlarray)
                        attractionss.add(attraction)
                    }

                    this@CustomAdapter.attractions = attractionss
                    notifyDataSetChanged()
                } else {
                }
            }
        })
    }
    interface OnItemClickListener {
        fun onItemClick(attraction: Attraction)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val attraction = attractions[position]

                    itemClickListener.onItemClick(attraction)
                }
            }
        }

        private val imageView: ImageView = itemView.findViewById(R.id.imageView_sub)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewIntroduction: TextView = itemView.findViewById(R.id.textViewIntoduction)

        fun bind(attraction: Attraction) {

            Glide.with(imageView)
                .load(attraction.imgUrl)
                .placeholder(R.drawable.ic_launcher_foreground) // 占位图（可选）
                .error(R.drawable.ic_launcher_foreground) // 加载错误时显示的图像（可选）
                .into(imageView)

            textViewName.text = attraction.name
            textViewIntroduction.text=attraction.introduction
        }
    }
}
