package id.poter.PLCMonitoring.server

import android.os.StrictMode
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication


class app : MultiDexApplication()  {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

}