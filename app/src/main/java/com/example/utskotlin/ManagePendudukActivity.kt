package com.example.utskotlin

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_manage_penduduk.*
import org.json.JSONObject

class ManagePendudukActivity : AppCompatActivity() {

    lateinit var i:Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_penduduk)


        btnCreate.setOnClickListener {
            create()
        }


    }


    private fun create(){

        val loading = ProgressDialog(this)
        loading.setMessage("Menambahkan data...")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.CREATE)
            .addBodyParameter("nama_penduduk",txNama.text.toString())
            .addBodyParameter("ttl_penduduk",txTtl.text.toString())
            .addBodyParameter("hp_penduduk",txHp.text.toString())
            .addBodyParameter("alamat_penduduk",txAlamat.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()

                    if(response?.getString("message")?.contains("successfully")!!){
                        this@ManagePendudukActivity.finish()
                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()                    }


            })

    }


}
