package id.poter.PLCMonitoring.persenter

import android.app.Activity
import android.content.Context

interface i_user {

    fun login (context: Context, activity: Activity, username : String, password : String,device : String,token : String)
    fun logout (context: Context,activity: Activity)
    fun ganti_password (context: Context,activity: Activity,password : String)

}