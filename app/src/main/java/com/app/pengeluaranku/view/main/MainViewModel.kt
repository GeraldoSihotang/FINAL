package com.app.pengeluaranku.view.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.app.pengeluaranku.model.entity.Pengeluaran
import com.app.pengeluaranku.utils.database.daos.PengeluaranDao
import io.reactivex.Completable
import kotlin.Throws
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import com.app.pengeluaranku.utils.database.DatabaseClient

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val pengeluarans: LiveData<List<Pengeluaran>>
    private val pengeluaranDao: PengeluaranDao
    val totalPrice: LiveData<Int>
    fun deleteAllData() {
        Completable.fromAction { pengeluaranDao.deleteAllData() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun deleteSingleData(uid: Int) {
        Completable.fromAction { pengeluaranDao.deleteSingleData(uid) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    init {
        pengeluaranDao = DatabaseClient.getInstance(application)
                .appDatabase.pengeluaranDao()
        pengeluarans = pengeluaranDao.all
        totalPrice = pengeluaranDao.totalPrice
    }
}