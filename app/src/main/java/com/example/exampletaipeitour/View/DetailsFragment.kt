package com.example.exampletaipeitour.View;

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exampletaipeitour.Attraction
import com.example.exampletaipeitour.MyModel
import com.example.exampletaipeitour.MyPresenter
import com.example.exampletaipeitour.R
import org.json.JSONArray
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2



class DetailsFragment(private val index: Int) : Fragment(), MyView ,CustomAdapter.OnItemClickListener {
    lateinit var presenter: MyPresenter
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CustomAdapter // 自定义适配器
    lateinit var imageadapter :ImagePagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        when(index){
            1 ->{
                val v = LayoutInflater.from(context).inflate(R.layout.fragment_main, container, false)

                val model = MyModel()
                presenter = MyPresenter(model, this)

                presenter.getContext(context)
                presenter.fetchData()

                recyclerView = v.findViewById<RecyclerView>(R.id.recycleview)

                recyclerView.layoutManager = LinearLayoutManager(context)
                adapter = CustomAdapter(presenter,this) // 将 presenter 传递给适配器
                recyclerView.adapter = adapter
                adapter.setAttractions()

                val activity = requireActivity()
                if (activity is AppCompatActivity) {
                    val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
                    val app_name = (requireActivity() as MainActivity).app_name
                    toolbar?.title = app_name
                    val menu = toolbar?.menu
                    menu?.findItem(R.id.lang)?.isVisible = true
                    menu?.findItem(R.id.go_back)?.isVisible=false
                }
                return v
            }
            2 ->{
                val v = LayoutInflater.from(context).inflate(R.layout.fragment_sub, container, false)


                val model = MyModel()
                presenter = MyPresenter(model, this)

                presenter.getContext(context)
                presenter.fetchData()

                val textViewSubTitle =v.findViewById<TextView>(R.id.textViewSubTitle)
                val textViewSubIntoduction =v.findViewById<TextView>(R.id.textViewSubIntoduction)
                val textViewOptime =v.findViewById<TextView>(R.id.textViewOptime)
                val textViewModified =v.findViewById<TextView>(R.id.textViewModified)
                val textViewUrl =v.findViewById<TextView>(R.id.textViewUrl)
                val textViewAddr =v.findViewById<TextView>(R.id.textViewAddr)

                val imgUrlarray = arguments?.getString("imgUrlarray")
                val name = arguments?.getString("name")
                val introduction = arguments?.getString("introduction")
                val openTime = arguments?.getString("open_time")
                val modified = arguments?.getString("modified")
                val url = arguments?.getString("url")
                val addr =arguments?.getString("address")

                viewPager = v.findViewById<ViewPager2>(R.id.viewPager)
                imageadapter = ImagePagerAdapter(this,imgUrlarray)
                viewPager.adapter = imageadapter

                textViewSubTitle.text =name+"\n"
                textViewSubIntoduction.text =introduction+"\n"
                textViewOptime.text =openTime+"\n"
                textViewAddr.text=addr+"\n"
                textViewModified.text =modified+"\n"

                val spannableUrl = SpannableString(url)
                if (url != null) {
                    spannableUrl.setSpan(ForegroundColorSpan(Color.BLUE), 0, url.length, 0)
                }
                if (url != null) {
                    spannableUrl.setSpan(UnderlineSpan(), 0, url.length, 0)
                }
                textViewUrl.text=spannableUrl

                textViewUrl.setOnClickListener {
                    val url = textViewUrl.text.toString()
                    if (Patterns.WEB_URL.matcher(url).matches()) {
                        val fragment = DetailsFragment(3) // 使用3作为标识符以显示WebView
                        val args = Bundle()
                        args.putString("url", url)
                        fragment.arguments = args
                        val transaction = parentFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment_container, fragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }

                }
                val activity = requireActivity()
                if (activity is AppCompatActivity) {
                    val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
                    toolbar?.title = name
                    val menu = toolbar?.menu
                    menu?.findItem(R.id.lang)?.isVisible = false
                    menu?.findItem(R.id.go_back)?.setOnMenuItemClickListener {
                     activity.onBackPressed()
                     true
                    }?.isVisible =true
                }



                return v
            }
            3 ->{
                val v = LayoutInflater.from(context).inflate(R.layout.fragment_webview, container, false)


                val model = MyModel()
                presenter = MyPresenter(model, this)

                presenter.getContext(context)
                presenter.fetchData()

                val webView: WebView = v.findViewById(R.id.webview)
                val url = arguments?.getString("url")

                if (!url.isNullOrEmpty()) {
                    webView.loadUrl(url)
                }

                return v
            }

            else ->{
                return null
            }
        }

    }

    override fun showData(data: JSONArray?) {

    }

    override fun showAttractions(attractions: List<Attraction>) {

    }

    override fun showError(message: String) {
        Log.e("Error", message)
    }

    override fun onItemClick(attraction: Attraction) {

        val bundle = Bundle()
        bundle.putString("imgUrl", attraction.imgUrl)
        bundle.putString("name", attraction.name)
        bundle.putString("introduction", attraction.introduction)
        bundle.putString("open_time", attraction.open_time)
        bundle.putString("modified", attraction.modified)
        bundle.putString("url", attraction.url)
        bundle.putString("address",attraction.address)
        bundle.putString("imgUrlarray",attraction.imgUrlarray)

        val fragment = DetailsFragment(2)
        fragment.arguments = bundle
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

}
