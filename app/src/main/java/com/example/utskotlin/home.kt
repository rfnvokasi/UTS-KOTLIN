package com.example.utskotlin

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class home : AppCompatActivity() {

    var arrayList = ArrayList<Penduduk>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home);

        supportActionBar?.title = "Data Mahasiswa"

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        mFloatingActionButton.setOnClickListener{
            startActivity(Intent(this, ManagePendudukActivity::class.java))
        }

        button.setOnClickListener{
            val sharedPreferences=getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("STATUS","0")
            editor.apply()

            startActivity(Intent(this@home,MainActivity::class.java))
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        loadAllStudents()
    }


    private fun loadAllStudents(){

        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    arrayList.clear()

                    val jsonArray = response?.optJSONArray("result")

                    if(jsonArray?.length() == 0){
                        loading.dismiss()
                        Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()
                    }

                    for(i in 0 until jsonArray?.length()!!){

                        val jsonObject = jsonArray?.optJSONObject(i)
                        arrayList.add(Penduduk(jsonObject.getString("nama_penduduk"),
                            jsonObject.getString("ttl_penduduk"),
                            jsonObject.getString("hp_penduduk"),
                            jsonObject.getString("alamat_penduduk")))

                        if(jsonArray?.length() - 1 == i){

                            loading.dismiss()
                            val adapter = RVAAdapterStudent(applicationContext,arrayList)
                            adapter.notifyDataSetChanged()
                            mRecyclerView.adapter = adapter

                        }

                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure",Toast.LENGTH_SHORT).show()
                }
            })


    }
}
