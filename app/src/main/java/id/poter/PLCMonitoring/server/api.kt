package id.poter.PLCMonitoring.server

import id.poter.PLCMonitoring.model.*
import io.reactivex.Single
import retrofit2.http.*
import retrofit2.http.POST
interface api {

    @FormUrlEncoded
    @POST("login")
    fun login (@Field("username") username: String,
               @Field("password") password: String,
               @Field("device") device: String,
               @Field("token") token: String): Single<user>

    @FormUrlEncoded
    @POST("logout")
    fun logout (@Field("device") device: String):Single<user>

    @FormUrlEncoded
    @PUT("ganti_password/{id}")
    fun ganti_password (@Path("id") id: String,
                        @Field("password") password: String): Single<user>

    @GET("darurat")
    fun darurat (): Single<darurat>

    @GET("instrumen/nama")
    fun instrumennama() : Single<instrumen>

    @FormUrlEncoded
    @POST("instrumen")
    fun instrumen  (@Field("tanggal") tanggal: String,
                    @Field("bulan") bulan: String): Single<instrumen>

    @FormUrlEncoded
    @POST("patrol")
    fun patrol  (@Field("id_instrumen") id_instrumen: String,
                 @Field("tanggal") tanggal: String,
                 @Field("bulan") bulan: String): Single<patrol>

    @GET("laporan/bulan")
    fun laporan() : Single<laporan>
}