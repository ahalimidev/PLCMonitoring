package id.poter.PLCMonitoring.persenter

import android.content.Context
import android.graphics.Color

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import id.poter.PLCMonitoring.model.instrumen
import id.poter.PLCMonitoring.model.r_patrol
import id.poter.PLCMonitoring.server.api
import id.poter.PLCMonitoring.server.constant
import id.poter.PLCMonitoring.server.koneksi
import id.poter.PLCMonitoring.model.patrol
import id.poter.PLCMonitoring.view.v_filter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat


class p_patrol : i_patrol {
    var disposable = CompositeDisposable()


    override fun filter_intrumen(context: Context, viewv_filter: v_filter) {
        disposable.add(
            koneksi.getClient(context)!!.create(api::class.java)
                .instrumennama()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<instrumen>() {
                    override fun onSuccess(instrumen: instrumen) {
                        viewv_filter!!.berhasil(instrumen)
                    }

                    override fun onError(e: Throwable) {


                    }
                })
        )
    }

    override fun all(context: Context, LineChart: LineChart, id_instrumen : String,tanggal : String,bulan : String) {
        var results: ArrayList<r_patrol>
        val set1 = ArrayList<Entry>()
        val set2 = ArrayList<Entry>()
        var temperature_dates_entry: ArrayList<String>
        disposable.add(
            koneksi.getClient(context)!!.create(api::class.java)
                .patrol(id_instrumen, tanggal,bulan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<patrol>() {
                    override fun onSuccess(patrol: patrol) {
                        if (patrol.success.equals("1")){
                            var angka = 0
                            results = patrol.data
                            temperature_dates_entry = ArrayList()

                            for (i in results.indices) {
                                var id = angka++
                                var data1 = results.get(i).nilaiArus.toFloat()
                                var data2 = results.get(i).nilaiTegangan.toFloat()

                                set1.add(Entry(id.toFloat(), data1))
                                set2.add(Entry(id.toFloat(), data2))
                                val parts = results.get(i).waktu.split(" ")
                                val time = SimpleDateFormat("hh:mm")
                                val ok = parts[1]
                                val partss = ok.split(":")

                                temperature_dates_entry.add(partss[0]+":"+partss[1])
                            }

                            LineChart.setDrawGridBackground(true);
                            LineChart.getAxisLeft().setDrawGridLines(true);
                            LineChart.getXAxis().setDrawGridLines(false);

                            val lineDataSet1 = LineDataSet(set1, "nilaiArus")
                            lineDataSet1.axisDependency = YAxis.AxisDependency.LEFT
                            lineDataSet1.isHighlightEnabled = false
                            lineDataSet1.setDrawValues(false)
                            lineDataSet1.setDrawCircles(false)
                            lineDataSet1.setCubicIntensity(0.2f);
                            lineDataSet1.setDrawCircles(false);
                            lineDataSet1.setLineWidth(1.8f);
                            lineDataSet1.setCircleRadius(4f);
                            lineDataSet1.setFillColor(Color.parseColor("#1882B9"));
                            lineDataSet1.setColor(Color.parseColor("#1882B9"));
                            lineDataSet1.setFillAlpha(10);
                            lineDataSet1.setValueFormatter(DefaultValueFormatter(0) as ValueFormatter);

                            val lineDataSet2 = LineDataSet(set2, "nilaiTegangan")
                            lineDataSet2.axisDependency = YAxis.AxisDependency.LEFT
                            lineDataSet2.isHighlightEnabled = false
                            lineDataSet2.setDrawValues(false)
                            lineDataSet2.setCubicIntensity(0.2f);
                            lineDataSet2.setDrawCircles(false);
                            lineDataSet2.setLineWidth(1.8f);
                            lineDataSet2.setCircleRadius(4f);
                            lineDataSet2.setFillColor(Color.parseColor("#FF8820"));
                            lineDataSet2.setColor(Color.parseColor("#FF8820"));
                            lineDataSet2.setFillAlpha(10);
                            lineDataSet2.setValueFormatter(DefaultValueFormatter(0))

                            val list = ArrayList<ILineDataSet>()
                            list.add(lineDataSet1)
                            list.add(lineDataSet2)
                            val lineData = LineData(list)
                            lineData.notifyDataChanged()
                            LineChart.data = lineData
                            LineChart.invalidate()
                            LineChart.setVisibleXRangeMaximum(50F);
                            val xAxis = LineChart.xAxis
                            xAxis.setDrawGridLines(false);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setEnabled(true);
                            LineChart.getXAxis().setValueFormatter(
                                com.github.mikephil.charting.formatter.IndexAxisValueFormatter(temperature_dates_entry)
                            )


                        }else{
                            constant.toast(context,patrol.message)

                        }
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )
    }
}