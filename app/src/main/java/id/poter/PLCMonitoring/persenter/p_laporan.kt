package id.poter.PLCMonitoring.persenter

import android.app.ProgressDialog
import android.content.Context
import id.poter.PLCMonitoring.server.api
import id.poter.PLCMonitoring.server.constant
import id.poter.PLCMonitoring.server.koneksi
import id.poter.PLCMonitoring.model.laporan
import id.poter.PLCMonitoring.view.v_laporan
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class p_laporan :i_laporan {
    var disposable = CompositeDisposable()

    override fun laporan(context: Context, view_laporan: v_laporan) {
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        disposable.add(
            koneksi.getClient(context)!!.create(api::class.java)
                .laporan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<laporan>() {
                    override fun onSuccess(laporan: laporan) {
                        progressDialog.dismiss()
                        view_laporan!!.berhasil(laporan)
                    }

                    override fun onError(e: Throwable) {
                        progressDialog.dismiss()

                    }
                })
        )
    }
}