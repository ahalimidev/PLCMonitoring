package id.poter.PLCMonitoring.persenter

import android.app.Activity
import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import id.poter.PLCMonitoring.view.v_filter
import id.poter.PLCMonitoring.view.v_laporan

interface i_laporan {

    fun laporan (context: Context, view_laporan: v_laporan)

}