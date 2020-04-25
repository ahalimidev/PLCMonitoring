package id.poter.PLCMonitoring

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import id.poter.PLCMonitoring.persenter.p_user
import id.poter.PLCMonitoring.view.menu
import kotlinx.android.synthetic.main.activity_main.*
import android.hardware.usb.UsbDevice.getDeviceId
import android.content.pm.PackageManager
import android.Manifest.permission
import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.telephony.TelephonyManager
import android.os.Build

import androidx.annotation.RequiresApi


class MainActivity : AppCompatActivity() {

    lateinit var p_user: p_user
    lateinit var token: String
    lateinit var IME: String

    private val PERMISSIONS_REQUEST_READ_PHONE_STATE = 999
    var loggedIn: Boolean = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        p_user = p_user()

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("token", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                token = task.result?.token.toString()

                // Log and toast
                Log.d("token", token)
            })

        bt_login.setOnClickListener {
            if (et_username.text.toString().isEmpty()) {
                et_username.error = "Username Kosong"
            } else if (et_password.text.toString().isEmpty()) {
                et_password.error = "Password Kosong"
            } else {
                if (checkSelfPermission(READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                        READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                        WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(READ_PHONE_STATE,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_READ_PHONE_STATE
                    )
                } else {
                    imei()
                    p_user.login(
                        this,
                        this,
                        et_username.text.toString(),
                        et_password.text.toString(),
                        IME,
                        token
                    )
                }

            }
        }

        if (checkSelfPermission(READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(READ_PHONE_STATE,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST_READ_PHONE_STATE
            )
        } else {
            imei()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            imei()
        }
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    fun imei() {
        val mTelephony = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (mTelephony.phoneCount == 2) {
                    IME = mTelephony.getImei(0)
                } else {
                    IME = mTelephony.imei
                }
            } else {
                if (mTelephony.phoneCount == 2) {
                    IME = mTelephony.getDeviceId(0)
                } else {
                    IME = mTelephony.deviceId
                }
            }
        } else {
            IME = mTelephony.deviceId
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        loggedIn= sharedPreferences.getBoolean("status", false)
        if (loggedIn) {
            val intent = Intent(this, menu::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        p_user.disposable.dispose()
    }
}
