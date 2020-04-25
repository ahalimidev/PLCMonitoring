package id.poter.PLCMonitoring.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class r_laporan {

    @SerializedName("id_instrumen")
    @Expose
    lateinit var id_instrumen: String

    @SerializedName("nama")
    @Expose
    lateinit var nama: String

    @SerializedName("baik")
    @Expose
    lateinit var baik: String

    @SerializedName("tidak_baik")
    @Expose
    lateinit var tidak_baik: String

    @SerializedName("Year")
    @Expose
    lateinit var Year: String

    @SerializedName("Januari")
    @Expose
    lateinit var Januari: String

    @SerializedName("Febuari")
    @Expose
    lateinit var Febuari: String

    @SerializedName("Maret")
    @Expose
    lateinit var Maret: String

    @SerializedName("April")
    @Expose
    lateinit var April: String

    @SerializedName("Mei")
    @Expose
    lateinit var Mei: String

    @SerializedName("Juni")
    @Expose
    lateinit var Juni: String

    @SerializedName("Juli")
    @Expose
    lateinit var Juli: String

    @SerializedName("Agustus")
    @Expose
    lateinit var Agustus: String

    @SerializedName("September")
    @Expose
    lateinit var September: String

    @SerializedName("Oktober")
    @Expose
    lateinit var Oktober: String

    @SerializedName("November")
    @Expose
    lateinit var November: String

    @SerializedName("Desember")
    @Expose
    lateinit var Desember: String
}
