package id.poter.PLCMonitoring.persenter

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import id.poter.PLCMonitoring.MainActivity
import id.poter.PLCMonitoring.model.user
import id.poter.PLCMonitoring.server.api
import id.poter.PLCMonitoring.server.constant
import id.poter.PLCMonitoring.server.koneksi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import id.poter.PLCMonitoring.view.menu


class p_user : i_user {

    var disposable = CompositeDisposable()

    override fun ganti_password(context: Context, activity: Activity, password: String) {
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        disposable.add(
            koneksi.getClient(context)!!.create(api::class.java)
                .ganti_password(sharedPreferences.getString("id","kosong").toString(),password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<user>() {
                    override fun onSuccess(user: user) {
                        if (user.success.equals("1")){
                            activity.finish()
                        }else{
                            constant.toast(context,user.message)
                        }
                    }

                    override fun onError(e: Throwable) {
                        progressDialog.dismiss()

                    }
                })
        )
    }

    override fun logout(context: Context, activity: Activity) {
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        disposable.add(
            koneksi.getClient(context)!!.create(api::class.java)
                .logout(sharedPreferences.getString("device","kosong").toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<user>() {
                    override fun onSuccess(user: user) {
                        progressDialog.dismiss()
                        val editor = sharedPreferences.edit()
                        editor.remove("id")
                        editor.remove("data")
                        editor.remove("status")
                        editor.commit()
                        activity.startActivity(Intent(context, MainActivity::class.java))
                        activity.finish()
                    }
                    override fun onError(e: Throwable) {
                        progressDialog.dismiss()

                    }
                })
        )
    }


    override fun login(context: Context, activity: Activity, username: String, password: String,device : String, token : String) {
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        disposable.add(
                koneksi.getClient(context)!!.create(api::class.java)
                .login(username,password,device,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<user>() {
                    override fun onSuccess(user: user) {
                        progressDialog.dismiss()
                        if (user.success.equals("1")){
                            val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("status", true)
                            editor.putString("id", user.data.id)
                            editor.putString("nama", user.data.nama)
                            editor.putString("device", device)
                            editor.commit()
                            activity.startActivity(Intent(context,menu::class.java))
                            activity.finish()
                        }else{
                            constant.toast(context,user.message)
                        }
                    }

                    override fun onError(e: Throwable) {
                        progressDialog.dismiss()
                    }
                })
        )
    }

}