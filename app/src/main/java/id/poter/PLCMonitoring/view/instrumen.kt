package id.poter.PLCMonitoring.view

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.poter.PLCMonitoring.R
import id.poter.PLCMonitoring.apdater.instrumenAdapter
import id.poter.PLCMonitoring.model.instrumen
import id.poter.PLCMonitoring.persenter.p_instrumen
import id.poter.PLCMonitoring.persenter.p_patrol
import id.poter.PLCMonitoring.server.constant
import kotlinx.android.synthetic.main.activity_instrumen.*
import kotlinx.android.synthetic.main.activity_patrol.*
import kotlinx.android.synthetic.main.item_filter.*
import java.util.*

class instrumen : AppCompatActivity()  , v_instrumen, instrumenAdapter.AdapterCallback{


    var monthName = arrayOf(
        "Pilih Bulan",
        "Januari",
        "Febuari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "November",
        "Desember"
    )

    var tanggal: String = "";
    var bulan: String = "";
    var mAdapter: instrumenAdapter? = null
    lateinit var RecyclerView : RecyclerView
    lateinit var p_instrumen : p_instrumen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrumen)
        p_instrumen = p_instrumen()

        RecyclerView = findViewById(R.id.rv_tampil_instrumen)
        iv_instrumen_filter.setOnClickListener {
            showBottomSheetDialog()
        }
        iv_instrumen_back.setOnClickListener {
            finish()
        }
        p_instrumen.all(this,this,tanggal, bulan)
        sfl_instrumen.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary);
        sfl_instrumen.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                sfl_instrumen.setRefreshing(false)
                p_instrumen.all(this,this,tanggal, bulan)

            }, 3000)
        }
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.item_filter, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val mBehavior = BottomSheetBehavior.from(view.getParent() as View)
        dialog.setOnShowListener({ dialogInterface ->
            mBehavior.peekHeight = view.getHeight()//get the height dynamically
        })

        dialog.if_instrumen.visibility = View.GONE

        dialog.if_iv_close.setOnClickListener {
            dialog.dismiss()
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, monthName)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialog.if_sp_bulan.setAdapter(adapter)
        if (!bulan.equals("")){
            dialog.if_sp_bulan.setSelection(bulan.toInt())
        }
        dialog.if_sp_bulan.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                bulan = position.toString()


            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        })
        if (!tanggal.equals("")){
            dialog.if_tv_tanggal.text = tanggal

        }
        dialog.if_bt_tanggal.setOnClickListener {
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    tanggal = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString()
                    dialog.if_tv_tanggal.text = tanggal
                    p_instrumen.all(this,this,tanggal, bulan)
                    dialog.dismiss()

                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }
        dialog.if_bt_bulan.setOnClickListener {
            if (bulan.equals("0")) {
                bulan = ""
                p_instrumen.all(this,this,tanggal, bulan)
                dialog.dismiss()

            } else {
                p_instrumen.all(this,this,tanggal, bulan)
                dialog.dismiss()

            }
        }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        p_instrumen.disposable.dispose()
    }
    override fun onRowAdapterClicked(position: Int) {
        val intent = Intent(this,patrol::class.java)
        intent.putExtra("id",position.toString())
        intent.putExtra("tanggal",tanggal)
        intent.putExtra("bulan",bulan)
        startActivity(intent)

    }

    override fun berhasil(instrumen: instrumen) {
        if(instrumen.success.equals("1")){
            mAdapter = instrumenAdapter(this,instrumen.data,this)
            val linearLayoutManager = LinearLayoutManager(this)
            linearLayoutManager.scrollToPositionWithOffset(0, 0)
            RecyclerView.setLayoutManager(linearLayoutManager)
            RecyclerView.adapter = mAdapter
            RecyclerView.smoothScrollToPosition(0)
        }else{
            constant.toast(this,instrumen.message)
        }
    }
}
