package com.example.utskotlin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.student_list.view.*

class RVAAdapterStudent(private val context: Context, private val arrayList: ArrayList<Penduduk>) : RecyclerView.Adapter<RVAAdapterStudent.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.student_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.lbNimList.text = arrayList?.get(position)?.nama_penduduk
        holder.view.lbNameList.text = "TTL : "+arrayList?.get(position)?.ttl_penduduk
        holder.view.lbAddressList.text = "HP : "+arrayList?.get(position)?.hp_penduduk
        holder.view.lbGenderList.text = "ALAMAT : "+arrayList?.get(position)?.alamat_penduduk

        holder.view.cvList.setOnClickListener {

            val i = Intent(context,ManagePendudukActivity::class.java)
            i.putExtra("editmode","1")
            i.putExtra("nama_penduduk",arrayList?.get(position)?.nama_penduduk)
            i.putExtra("ttl_penduduk",arrayList?.get(position)?.ttl_penduduk)
            i.putExtra("hp_penduduk",arrayList?.get(position)?.hp_penduduk)
            i.putExtra("alamat_penduduk",arrayList?.get(position)?.alamat_penduduk)
            context.startActivity(i)

        }

    }

    class Holder(val view:View) : RecyclerView.ViewHolder(view)

}