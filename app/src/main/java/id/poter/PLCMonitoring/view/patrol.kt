package id.poter.PLCMonitoring.view

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.poter.PLCMonitoring.R
import id.poter.PLCMonitoring.apdater.instrumenFilterAdapter
import id.poter.PLCMonitoring.model.instrumen
import id.poter.PLCMonitoring.persenter.p_patrol
import id.poter.PLCMonitoring.persenter.p_instrumen
import id.poter.PLCMonitoring.server.constant
import kotlinx.android.synthetic.main.activity_patrol.*
import kotlinx.android.synthetic.main.item_filter.*
import java.util.*


class patrol : AppCompatActivity(), v_filter, instrumenFilterAdapter.AdapterCallback {

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
    var mAdapter: instrumenFilterAdapter? = null
    lateinit var p_patrol: p_patrol
    var id_instrumen: String = "";
    var tanggal: String = "";
    var bulan: String = "";
    private var runningThread = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patrol)
        p_patrol = p_patrol()
        iv_patrol_filter.setOnClickListener {
            showBottomSheetDialog()
        }
        iv_patrol_back.setOnClickListener {
            finish()
        }
        p_patrol.filter_intrumen(this, this)

        id_instrumen = intent.getStringExtra("id")
        if (intent.getStringExtra("tanggal") == null){

        }else{
            tanggal = intent.getStringExtra("tanggal")

        }
        if (intent.getStringExtra("bulan") == null){

        }else{
            bulan = intent.getStringExtra("bulan")

        }
    }
    override fun onResume() {
        super.onResume()
        if (tanggal.isEmpty() || bulan.isEmpty()){
            runningThread = true
            realtimedata()
        }else{
            runningThread = false
            p_patrol.all(this,lineChart,id_instrumen, tanggal, bulan)

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        runningThread = false
        p_patrol.disposable.dispose()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        runningThread = false
    }
    private fun realtimedata() {
        Thread(Runnable {
            while (true) {
                try {
                    Thread.sleep(3000)
                    if (!runningThread) {
                        return@Runnable
                    }
                    p_patrol.all(this,lineChart,id_instrumen, tanggal, bulan)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }).start()
    }
    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.item_filter, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val mBehavior = BottomSheetBehavior.from(view.getParent() as View)
        dialog.setOnShowListener({ dialogInterface ->
            mBehavior.peekHeight = view.getHeight()//get the height dynamically
        })
        val tampil = dialog.findViewById<RecyclerView>(R.id.if_tampil)
        val horizontalLayoutManagaer = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        tampil!!.setLayoutManager(horizontalLayoutManagaer)
        tampil.adapter = mAdapter

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
                    p_patrol.all(this,lineChart,id_instrumen, tanggal, bulan)
                    dialog.dismiss()

                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }
        dialog.if_bt_bulan.setOnClickListener {
            if (bulan.equals("0")) {
                bulan = ""
                p_patrol.all(this,lineChart,id_instrumen, tanggal, bulan)
                dialog.dismiss()

            } else {
                dialog.dismiss()
                p_patrol.all(this,lineChart,id_instrumen, tanggal, bulan)

            }
        }
        dialog.show()
    }
    override fun onRowAdapterClicked(position: Int) {
        id_instrumen = position.toString()
        p_patrol.all(this,lineChart,id_instrumen, tanggal, bulan)

    }

    override fun berhasil(instrumen: instrumen) {
       if(instrumen.success.equals("1")){
           mAdapter = instrumenFilterAdapter(this, instrumen.data, this)
       }else{
           constant.toast(this,instrumen.message)
       }
    }

}
