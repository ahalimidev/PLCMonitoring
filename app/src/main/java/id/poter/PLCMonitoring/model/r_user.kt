package id.poter.PLCMonitoring.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class r_user {

    @SerializedName("id")
    @Expose
    lateinit var id: String

    @SerializedName("nama")
    @Expose
    lateinit var nama: String
}