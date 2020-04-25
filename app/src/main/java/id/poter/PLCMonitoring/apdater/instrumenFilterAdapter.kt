package id.poter.PLCMonitoring.apdater

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import androidx.recyclerview.widget.RecyclerView
import id.poter.PLCMonitoring.R
import id.poter.PLCMonitoring.model.r_instrumen

import java.util.*



class instrumenFilterAdapter(private val context: Context, results: ArrayList<r_instrumen>, adapterCallback: AdapterCallback) :
    RecyclerView.Adapter<instrumenFilterAdapter.ItemViewHolder>() {

    private var Items = ArrayList<r_instrumen>()
    private var mAdapterCallback: AdapterCallback? = null

    init {
        this.Items = results
        this.mAdapterCallback = adapterCallback;

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Button :Button
        init {
            Button = view.findViewById(R.id.ifi_nama)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter_instrumen, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.Button.text = result.nama
        myHolder.Button.setOnClickListener {
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