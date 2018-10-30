package com.company.raulfm.remotelight

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.support.v4.app.DialogFragment
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.configs_dialog.*
import android.content.Intent
import android.R.attr.key
import android.R.id.edit
import android.content.SharedPreferences
import android.widget.Toast


class ConfigsDialog : DialogFragment(){
    var updateBtn : Button?   = null
    var ipTxt     : EditText? = null
    var portTxt   : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog{
        var _view: View = getActivity()!!.getLayoutInflater().inflate(R.layout.configs_dialog, null)

        this.ipTxt     = _view.findViewById(R.id.ip_input)
        this.portTxt   = _view.findViewById(R.id.port_input)
        this.updateBtn = _view.findViewById(R.id.update_btn)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)

        this.ipTxt?.setText(sharedPref.getString("IP", null))
        this.portTxt?.setText(sharedPref.getString("PORT", null))

        updateBtn?.setOnClickListener{
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)

            val editor = sharedPreferences.edit()
            editor.putString("IP", ipTxt?.text.toString())
            editor.putString("PORT", portTxt?.text.toString())
            editor.commit()

            Toast.makeText(activity, "Connection configs updated", Toast.LENGTH_SHORT).show()

            dismiss()
        }

        var alert = AlertDialog.Builder(activity)
        alert.setView(_view)

        return alert.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.configs_dialog, container, false)
        return v
    }
}