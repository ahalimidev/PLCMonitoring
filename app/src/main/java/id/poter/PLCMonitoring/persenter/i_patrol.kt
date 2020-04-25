package id.poter.PLCMonitoring.persenter

import android.app.Activity
import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import id.poter.PLCMonitoring.view.v_filter

interface i_patrol {

    fun all (context: Context, LineChart : LineChart, id_instrumen : String,tanggal : String,bulan : String)
    fun filter_intrumen (context: Context, viewv_filter: v_filter)

}