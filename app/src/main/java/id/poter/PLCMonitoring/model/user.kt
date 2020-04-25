package id.poter.PLCMonitoring.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class user {


    @SerializedName("success")
    @Expose
    lateinit var success: String

    @SerializedName("message")
    @Expose
    lateinit var message: String

    @SerializedName("data")
    @Expose
    lateinit var data: r_user

}