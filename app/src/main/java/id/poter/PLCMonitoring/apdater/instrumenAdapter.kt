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
import id.poter.PLCMonitoring.R
import id.poter.PLCMonitoring.model.r_instrumen
import id.poter.PLCMonitoring.view.patrol
import kotlinx.android.synthetic.main.activity_menu.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*


class instrumenAdapter(private val context: Context, results: ArrayList<r_instrumen>, adapterCallback: AdapterCallback) :
    RecyclerView.Adapter<instrumenAdapter.ItemViewHolder>() {
    private var mAdapterCallback: AdapterCallback? = null

    private var Items = ArrayList<r_instrumen>()

    init {
        this.Items = results
        this.mAdapterCallback = adapterCallback;

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nama: TextView
        var tanggal: TextView
        var arus: TextView
        var tegangan: TextView
        var status_y: ImageView
        var status_n: ImageView
        var grafik: ImageView

        init {

            nama = view.findViewById(R.id.ii_nama)
            tanggal = view.findViewById(R.id.ii_tanggal)
            arus = view.findViewById(R.id.ii_arus)
            tegangan = view.findViewById(R.id.ii_tegangan)

            status_y = view.findViewById(R.id.ii_status_y)
            status_n = view.findViewById(R.id.ii_status_n)
            grafik = view.findViewById(R.id.ii_grafik)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_insturmen, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.nama.text = result.nama
        myHolder.tanggal.text = result.waktu
        myHolder.arus.text = result.nilaiArus
        myHolder.tegangan.text = result.nilaiTegangan


        if (result.aktif.equals("N") && result.status.equals("1")){

            myHolder.status_y.visibility = View.GONE
            myHolder.status_n.visibility = View.VISIBLE
            myHolder.tanggal.setTextColor(Color.parseColor("#FFF44336"))
            myHolder.arus.setTextColor(Color.parseColor("#FFF44336"))
            myHolder.tegangan.setTextColor(Color.parseColor("#FFF44336"))

        }else if (result.aktif.equals("N")){
            myHolder.status_y.visibility = View.GONE
            myHolder.status_n.visibility = View.VISIBLE
        }else if (result.status.equals("1")){
            myHolder.tanggal.setTextColor(Color.parseColor("#FFF44336"))
            myHolder.arus.setTextColor(Color.parseColor("#FFF44336"))
            myHolder.tegangan.setTextColor(Color.parseColor("#FFF44336"))
        }else{
            myHolder.status_y.visibility = View.VISIBLE
            myHolder.status_n.visibility = View.GONE

        }

        myHolder.grafik.setOnClickListener {
            mAdapterCallback!!.onRowAdapterClicked(result.id_instrumen.toInt())

        }

    }

    override fun getItemCount(): Int {
        return Items.size
    }
    interface AdapterCallback {
        /*
        Disini kalian bisa membuat beberapa fungsi dengan parameter sesuai kebutuhan. Kebutuhan
        disini adalah untuk mendapatkan pada posisi mana user mengklik listnya.
         */
        fun onRowAdapterClicked(position: Int)
    }
}