package id.poter.PLCMonitoring.apdater

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import id.poter.PLCMonitoring.R
import id.poter.PLCMonitoring.model.r_instrumen
import id.poter.PLCMonitoring.model.r_laporan
import java.util.*
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet





class laporanAdapter(private val context: Context, results: ArrayList<r_laporan>, adapterCallback: AdapterCallback) :
    RecyclerView.Adapter<laporanAdapter.ItemViewHolder>() {
    private var mAdapterCallback: AdapterCallback? = null

    private var Items = ArrayList<r_laporan>()
    init {
        this.Items = results
        this.mAdapterCallback = adapterCallback;

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var BarChart : BarChart
        var nama : TextView
        init {
            BarChart = itemView.findViewById(R.id.BarChart)
            nama = itemView.findViewById(R.id.il_instrumen)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_laporan, parent,false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.nama.text = result.nama
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, result.Januari.toFloat()))
        entries.add(BarEntry(1f, result.Febuari.toFloat()))
        entries.add(BarEntry(2f, result.Maret.toFloat()))
        entries.add(BarEntry(3f, result.April.toFloat()))
        entries.add(BarEntry(4f, result.Mei.toFloat()))
        entries.add(BarEntry(5f, result.Juni.toFloat()))
        entries.add(BarEntry(6f, result.Juli.toFloat()))
        entries.add(BarEntry(7f, result.Agustus.toFloat()))
        entries.add(BarEntry(8f, result.September.toFloat()))
        entries.add(BarEntry(9f, result.Oktober.toFloat()))
        entries.add(BarEntry(10f, result.November.toFloat()))
        entries.add(BarEntry(11f, result.Desember.toFloat()))

        val dataSet = BarDataSet(entries, "Horizontal Bar")

        val data = BarData(dataSet)
        myHolder.BarChart.setData(data)
        myHolder.BarChart.animateXY(2000, 2000)
        myHolder.BarChart.invalidate()


        val xLabels = ArrayList<String>()
        xLabels.add("Januari")
        xLabels.add("Febuari")
        xLabels.add("Maret")
        xLabels.add("April")
        xLabels.add("Mei")
        xLabels.add("Juni")
        xLabels.add("Juli")
        xLabels.add("Agustus")
        xLabels.add("September")
        xLabels.add("Oktober")
        xLabels.add("November")
        xLabels.add("Desember")

        val xAxis =  myHolder.BarChart.getXAxis()
        xAxis.setValueFormatter(
            com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xLabels)
        )
    }

    override fun getItemCount(): Int {
        return Items.size
    }
    interface AdapterCallback {

        fun onRowAdapterClicked(position: Int)
    }

}