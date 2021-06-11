package com.app.pengeluaranku.view.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.pengeluaranku.adapter.PengeluaranAdapter
import com.app.pengeluaranku.adapter.PengeluaranAdapter.PengeluaranAdapterCallback
import com.app.pengeluaranku.databinding.ActivityMainBinding
import com.app.pengeluaranku.model.entity.Pengeluaran
import com.app.pengeluaranku.utils.FunctionHelper.rupiahFormat
import com.app.pengeluaranku.view.add.AddDataActivity.Companion.startActivity
import java.util.*

class MainActivity : AppCompatActivity(), PengeluaranAdapterCallback {
    private var binding: ActivityMainBinding? = null
    private var pengeluaranAdapter: PengeluaranAdapter? = null
    private var mainViewModel: MainViewModel? = null
    private val mPengeluarans: List<Pengeluaran> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initAdapter()
        observeData()
        initAction()
    }

    private fun initAction() {
        binding!!.fabAdd.setOnClickListener {
            startActivity(this@MainActivity, false,
                    null)
        }
        binding!!.btnHapus.setOnClickListener {
            mainViewModel!!.deleteAllData()
            binding!!.tvTotal.text = "0"
        }
    }

    private fun initAdapter() {
        pengeluaranAdapter = PengeluaranAdapter(this, mPengeluarans.toMutableList(), this)
        binding!!.rvPengeluarans.layoutManager = LinearLayoutManager(this)
        binding!!.rvPengeluarans.itemAnimator = DefaultItemAnimator()
        binding!!.rvPengeluarans.adapter = pengeluaranAdapter
    }

    private fun observeData() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel!!.pengeluarans.observe(this,
                { pengeluarans ->
                    if (pengeluarans.isEmpty()) {
                        binding!!.btnHapus.visibility = View.GONE
                    } else {
                        binding!!.btnHapus.visibility = View.VISIBLE
                    }
                    pengeluaranAdapter!!.addData(pengeluarans.toMutableList())
                })
        mainViewModel!!.totalPrice.observe(this,
                { integer ->
                    if (integer == null) {
                        val totalPrice = 0
                        val initPrice = rupiahFormat(totalPrice)
                        binding!!.tvTotal.text = initPrice
                    } else {
                        val initPrice = rupiahFormat(integer)
                        binding!!.tvTotal.text = initPrice
                    }
                })
    }

    override fun onEdit(pengeluaran: Pengeluaran?) {
        startActivity(this, true, pengeluaran)
    }

    override fun onDelete(pengeluaran: Pengeluaran?) {
        val uid = pengeluaran!!.uid
        mainViewModel!!.deleteSingleData(uid)
    }
}