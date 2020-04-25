package id.poter.PLCMonitoring.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class laporan {
    @SerializedName("success")
    @Expose
    lateinit var success: String

    @SerializedName("message")
    @Expose
    lateinit var message: String

    @SerializedName("data")
    @Expose
    lateinit var data: ArrayList<r_laporan>

}