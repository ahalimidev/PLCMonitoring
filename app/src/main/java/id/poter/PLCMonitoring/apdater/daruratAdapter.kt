package id.poter.PLCMonitoring.apdater

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.poter.PLCMonitoring.R
import id.poter.PLCMonitoring.view.patrol
import id.poter.PLCMonitoring.model.r_darurat
import java.util.*


class daruratAdapter(private val context: Context, results: ArrayList<r_darurat>) :
    RecyclerView.Adapter<daruratAdapter.ItemViewHolder>() {

    private var Items = ArrayList<r_darurat>()

    init {
        this.Items = results

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nama: TextView
        var arus: TextView
        var tegangan: TextView
        var tanggal: TextView

        init {

            nama = view.findViewById(R.id.id_nama)
            arus = view.findViewById(R.id.id_arus)
            tegangan = view.findViewById(R.id.id_tegangan)
            tanggal = view.findViewById(R.id.id_tanggal)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_darurat, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.nama.text = result.nama
        myHolder.arus.text = result.nilaiArus
        myHolder.tegangan.text = result.nilaiTegangan
        myHolder.tanggal.text = result.waktu
        myHolder.itemView.setOnClickListener {
            val intent = Intent(context, patrol::class.java)
            intent.putExtra("id",result.id_instrumen)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return Items.size
    }
}