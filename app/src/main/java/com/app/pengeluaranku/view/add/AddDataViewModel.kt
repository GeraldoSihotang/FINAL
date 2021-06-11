package com.app.pengeluaranku.view.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.app.pengeluaranku.model.entity.Pengeluaran
import com.app.pengeluaranku.utils.database.DatabaseClient
import com.app.pengeluaranku.utils.database.daos.PengeluaranDao
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddDataViewModel(application: Application) : AndroidViewModel(application) {
    private val pengeluaranDao: PengeluaranDao
    fun addPengeluaran(note: String?, price: Int) {
        Completable.fromAction {
            val pengeluaran = Pengeluaran()
            pengeluaran.keterangan = note
            pengeluaran.harga = price
            pengeluaranDao.insertData(pengeluaran)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun updatePengeluaran(uid: Int, note: String?, price: Int) {
        Completable.fromAction { pengeluaranDao.updateData(note, price, uid) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    init {
        pengeluaranDao = DatabaseClient.getInstance(application).appDatabase.pengeluaranDao()
    }
}