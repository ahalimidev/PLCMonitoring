package id.poter.PLCMonitoring.view

import androidx.appcompat.app.AppCompatActivity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast

import id.poter.PLCMonitoring.R
import id.poter.PLCMonitoring.persenter.p_user
import kotlinx.android.synthetic.main.activity_password.*

class password : AppCompatActivity() {
    lateinit var  p_user : p_user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        p_user = p_user()
        iv_password_back.setOnClickListener { finish() }
        bt_ganti_password.setOnClickListener {
            if (tv_password_baru.text.toString().isEmpty()){
               tv_password_baru.error = "Password Baru Kosong"
            }else if (tv_password_baru_ulang.text.toString().isEmpty()){
                tv_password_baru_ulang.error = "Password Baru Ulang Kosong"

            }else if (tv_password_baru.text.toString().equals(tv_password_baru_ulang.text.toString())){
                p_user.ganti_password(this,this,tv_password_baru.text.toString())
            }else{
                Toast.makeText(this,"Password tidak sama",Toast.LENGTH_LONG).show()
                tv_password_baru_ulang.error  ="Tidak sama"
                tv_password_baru.error  ="Tidak sama"
            }
        }
        cb_ganti_password_lihat.setOnCheckedChangeListener { buttonView, isChecked ->
            if(!isChecked){
                tv_password_baru.setTransformationMethod(PasswordTransformationMethod.getInstance())
                tv_password_baru_ulang.setTransformationMethod(PasswordTransformationMethod.getInstance())
            } else{
                tv_password_baru_ulang.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                tv_password_baru.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        p_user.disposable.dispose()
    }
}
