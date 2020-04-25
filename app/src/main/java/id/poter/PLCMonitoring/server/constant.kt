package id.poter.PLCMonitoring.server

import android.content.Context
import android.widget.Toast

object  constant {

    //link
    val BASE_URL = "http://plc.poter.id/api/"

    fun toast(context: Context,data : String){
        Toast.makeText(context,data,Toast.LENGTH_LONG).show()
    }


}