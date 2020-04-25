package id.poter.PLCMonitoring.persenter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.poter.PLCMonitoring.apdater.daruratAdapter
import id.poter.PLCMonitoring.model.darurat
import id.poter.PLCMonitoring.model.instrumen
import id.poter.PLCMonitoring.server.api
import id.poter.PLCMonitoring.server.constant
import id.poter.PLCMonitoring.server.koneksi
import id.poter.PLCMonitoring.view.v_filter
import id.poter.PLCMonitoring.view.v_instrumen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class p_instrumen :i_instrumen {

    var disposable = CompositeDisposable()

    override fun filter_intrumen(context: Context, viewv_filter: v_filter) {
        disposable.add(
            koneksi.getClient(context)!!.create(api::class.java)
                .instrumennama()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<instrumen>() {
                    override fun onSuccess(instrumen: instrumen) {
                        viewv_filter!!.berhasil(instrumen)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )
    }

    override fun history(context: Context, recyclerView:RecyclerView) {
        var mAdapter: daruratAdapter? = null
        disposable.add(
            koneksi.getClient(context)!!.create(api::class.java)
                .darurat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<darurat>() {
                    override fun onSuccess(darurat: darurat) {
                       if (darurat.success.equals("1")){
                           mAdapter = daruratAdapter(context,darurat.data)
                           val linearLayoutManager = LinearLayoutManager(context)
                           linearLayoutManager.scrollToPositionWithOffset(0, 0)
                           recyclerView.setLayoutManager(linearLayoutManager)
                           recyclerView.adapter = mAdapter
                           recyclerView.smoothScrollToPosition(0)
                       }else{
                           constant.toast(context,darurat.message)
                       }
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )
    }


    override fun all(context: Context,view_filter: v_instrumen,tanggal : String,bulan : String) {
        disposable.add(
            koneksi.getClient(context)!!.create(api::class.java)
                .instrumen(tanggal,bulan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<instrumen>() {
                    override fun onSuccess(instrumen: instrumen) {
                        view_filter.berhasil(instrumen)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )
    }

}