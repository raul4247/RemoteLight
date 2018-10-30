package com.company.raulfm.remotelight

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import kotlinx.android.synthetic.main.activity_main.*
import android.preference.PreferenceManager
import android.content.SharedPreferences




class MainActivity : AppCompatActivity(){
    var serverIp  : String? = null
    var serverPort: String? = null

    private fun serverRequest(ip: String, port: String,endPoint: String, successMsg: String,
                              errorMsg: String, timeOut: Int){
        val url = "http://" + ip + ':' + port + endPoint
        System.out.println("making to" + url)
        Fuel.get(url).timeout(timeOut).timeoutRead(timeOut).responseString { request, response, result ->
            result.fold({ d ->
                Toast.makeText(applicationContext, successMsg, Toast.LENGTH_SHORT).show()
            }, { err ->
                Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        this.serverIp = sharedPref.getString("IP", null)
        this.serverPort = sharedPref.getString("PORT", null)

        switch_btn.setOnClickListener{
            serverRequest(this.serverIp!!, this.serverPort!!, "/switch", "Switch Activated", "Server Status: Offline", 3000)
        }

        ping_btn.setOnClickListener{
            serverRequest(this.serverIp!!, this.serverPort!!, "/status", "Server Status: Online", "Server Status: Offline", 3000)
        }

        connection_btn.setOnClickListener{
            val myFragment : ConfigsDialog = ConfigsDialog()
            myFragment.show(supportFragmentManager,"configs_dialog")
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(this.applicationContext)
            this.serverIp = sharedPref.getString("IP", null)
            this.serverPort = sharedPref.getString("PORT", null)
        }
    }
}