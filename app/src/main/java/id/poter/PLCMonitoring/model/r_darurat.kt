package id.poter.PLCMonitoring.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class r_darurat {

    @SerializedName("id_instrumen")
    @Expose
    lateinit var id_instrumen: String

    @SerializedName("nama")
    @Expose
    lateinit var nama: String

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

    @SerializedName("aktif")
    @Expose
    lateinit var aktif: String
}
