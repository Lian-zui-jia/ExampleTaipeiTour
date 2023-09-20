package com.example.exampletaipeitour.View;

import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.exampletaipeitour.R
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val firstFragment = DetailsFragment(1)
    private lateinit var sharedPref: SharedPreferences // 声明 SharedPreferences 对象
    private lateinit var editor: SharedPreferences.Editor
    lateinit var app_name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPref = applicationContext.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        editor= sharedPref.edit()
        val lang: String? = sharedPref?.getString("lang", "zh-tw")

        setMainActivityTitle(lang)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)



        replaceFragment(firstFragment)

    }

    private fun addFragment(f: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, f)
        transaction.commit()
    }

    private fun replaceFragment(f: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, f)
        transaction.commit()
    }

    private fun restartActivity(){
        Toast.makeText(this, "即將重新啟動", Toast.LENGTH_SHORT).show()

        val intent = packageManager.getLaunchIntentForPackage(packageName)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, pendingIntent)

        finish()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_my_activity, menu)
        val menuItemgoback = menu?.findItem(R.id.go_back)
        menuItemgoback?.isVisible = false
        return true
    }
    fun setMainActivityTitle(lang:String?){
        when (lang) {
            "zh-tw" -> {
                app_name =  getString(R.string.app_name)
            }
            "zh-cn" -> {
                app_name = getString(R.string.app_name_cn)
            }
            "en" -> {
                app_name = getString(R.string.app_name_en)

            }
            "ja" -> {
                app_name = getString(R.string.app_name_ja)
            }
            "ko" -> {
                app_name = getString(R.string.app_name_ko)

            }
            "es" -> {
                app_name = getString(R.string.app_name_es)

            }
            "id" -> {
                app_name =  getString(R.string.app_name_id)

            }
            "th" -> {
                app_name = getString(R.string.app_name_th)

            }
            "vi" -> {
                app_name = getString(R.string.app_name_vi)

            }
        }
        Log.d("lang",lang.toString())
        this.setTitle(app_name)
//        supportActionBar?.title = app_name

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.lang -> {
                val options = arrayOf("繁體中文", "zh-cn", "English","ja","ko","es","id","th","vi")

                val builder = AlertDialog.Builder(this)
                    .setItems(options) { _, which ->
                        when (which) {
                            0 -> {
                                editor.putString("lang","zh-tw")
                                editor.apply()
                                restartActivity()
                            }
                            1 -> {
                                editor.putString("lang","zh-cn")
                                editor.apply()
                                restartActivity()

                            }
                            2 -> {
                                editor.putString("lang","en")
                                editor.apply()
                                restartActivity()

                            }
                            3 -> {
                                editor.putString("lang","ja")
                                editor.apply()
                                restartActivity()
                            }
                            4 -> {
                                editor.putString("lang","ko")
                                editor.apply()
                                restartActivity()

                            }
                            5 -> {
                                editor.putString("lang","es")
                                editor.apply()
                                restartActivity()

                            }
                            6 -> {
                                editor.putString("lang","id")
                                editor.apply()
                                restartActivity()

                            }
                            7 -> {
                                editor.putString("lang","th")
                                editor.apply()
                                restartActivity()

                            }
                            8 -> {
                                editor.putString("lang","vi")
                                editor.apply()
                                restartActivity()

                            }
                        }
                    }

                val dialog = builder.create()
                dialog.show()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

}
