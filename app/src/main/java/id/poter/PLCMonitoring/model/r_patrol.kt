package id.poter.PLCMonitoring.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class r_patrol {

    @SerializedName("id_patrol")
    @Expose
    lateinit var id_patrol: String

    @SerializedName("id_instrumen")
    @Expose
    lateinit var id_instrumen: String

    @SerializedName("id_setting")
    @Expose
    lateinit var id_setting: String

    @SerializedName("nilaiArus")
    @Expose
    lateinit var nilaiArus: String

    @SerializedName("nilaiTegangan")
    @Expose
    lateinit var nilaiTegangan: String

    @SerializedName("waktu")
    @Expose
    lateinit var waktu: String

    @SerializedName("status")
    @Expose
    lateinit var status: String
}
