package id.poter.PLCMonitoring.persenter

import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.poter.PLCMonitoring.view.v_filter
import id.poter.PLCMonitoring.view.v_instrumen

interface i_instrumen {

    fun all (context: Context, view_filter: v_instrumen,tanggal : String,bulan : String)
    fun filter_intrumen (context: Context, viewv_filter: v_filter)
    fun history (context: Context, recyclerView:RecyclerView)

}