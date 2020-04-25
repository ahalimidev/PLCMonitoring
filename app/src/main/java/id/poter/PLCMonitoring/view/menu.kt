package id.poter.PLCMonitoring.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import id.poter.PLCMonitoring.R
import id.poter.PLCMonitoring.persenter.p_user
import id.poter.PLCMonitoring.persenter.p_instrumen
import kotlinx.android.synthetic.main.activity_menu.*



class menu : AppCompatActivity(){
    lateinit var p_instrumen: p_instrumen
    lateinit var p_user : p_user
    lateinit var RecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        p_instrumen = p_instrumen()
        p_user = p_user()
        val sharedPreferences = getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        val nama = sharedPreferences.getString("nama", "Nama Kosong")
        menu_nama.text = nama
        RecyclerView = findViewById(R.id.menu_tampil)

        menu_keluar.setOnClickListener {
            val pesan = AlertDialog.Builder(this)
            pesan.setTitle("Logout")
            pesan.setMessage("Apakah anda yakin untuk logout dari aplikasi?")
            pesan.setPositiveButton("Ya",{dialog, which ->
                p_user.logout(this,this)
                dialog.dismiss()
            })
            pesan.setNegativeButton("Tidak",{dialog, which ->
                dialog.dismiss()
            })
            pesan.create()
            pesan.show()
        }
        menu_password.setOnClickListener {
            startActivity(Intent(this,password::class.java))
        }
        menu_instrumen.setOnClickListener {
            startActivity(Intent(this,instrumen::class.java))

        }
        menu_laporan.setOnClickListener {
            startActivity(Intent(this,laporan::class.java))

        }
        sfl_menu.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary);
        sfl_menu.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                sfl_menu.setRefreshing(false)
                p_instrumen.history(this, RecyclerView)
            }, 3000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        p_user.disposable.dispose()
        p_instrumen.disposable.dispose()
    }
    override fun onResume() {
        super.onResume()
        p_instrumen.history(this, RecyclerView)
        val sapa = SimpleDateFormat("H", Locale.US).format(Date()).toInt()
        if (sapa >= 0 && sapa <= 10){
            menu_hari.text = "Selamat Pagi"
            menu_gambar.setImageResource(R.drawable.ic_pagi)
        }else if (sapa >= 11 && sapa <= 13 ){
            menu_hari.text = "Selamat Siang"
            menu_gambar.setImageResource(R.drawable.ic_siang)
        }else if (sapa >= 14 && sapa <= 18 ){
            menu_hari.text = "Selamat Sore"
            menu_gambar.setImageResource(R.drawable.ic_sore)
        }else if (sapa >= 19 && sapa <= 23 ){
            menu_hari.text = "Selamat Malam"
            menu_gambar.setImageResource(R.drawable.ic_malam)

        }
    }
}
