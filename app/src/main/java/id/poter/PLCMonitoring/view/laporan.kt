package id.poter.PLCMonitoring.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.poter.PLCMonitoring.R
import id.poter.PLCMonitoring.apdater.laporanAdapter
import id.poter.PLCMonitoring.model.instrumen
import id.poter.PLCMonitoring.model.laporan
import id.poter.PLCMonitoring.persenter.p_laporan
import id.poter.PLCMonitoring.server.constant
import kotlinx.android.synthetic.main.activity_laporan.*

class laporan : AppCompatActivity(), laporanAdapter.AdapterCallback, v_laporan {


    var mAdapter: laporanAdapter? = null
    lateinit var RecyclerView : RecyclerView
    lateinit var p_laporan : p_laporan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan)
        p_laporan = p_laporan()
        RecyclerView = findViewById(R.id.rv_tampil_laporan)
        iv_laporan_back.setOnClickListener {
            finish()
        }
        p_laporan.laporan(this,this)
        sfl_laporan.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary);
        sfl_laporan.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                sfl_laporan.setRefreshing(false)
                p_laporan.laporan(this,this)

            }, 3000)
        }
    }

    override fun onRowAdapterClicked(position: Int) {


    }

    override fun onDestroy() {
        super.onDestroy()
        p_laporan.disposable.dispose()
    }

    override fun berhasil(laporan: laporan) {
        if (laporan.success.equals("1")){
            mAdapter = laporanAdapter(this,laporan.data,this)
            val linearLayoutManager = LinearLayoutManager(this)
            linearLayoutManager.scrollToPositionWithOffset(0, 0)
            RecyclerView.setLayoutManager(linearLayoutManager)
            RecyclerView.adapter = mAdapter
            RecyclerView.smoothScrollToPosition(0);
        }else{
            constant.toast(this,laporan.message)
        }
    }
}
