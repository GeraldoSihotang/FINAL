package com.app.pengeluaranku.view.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.app.pengeluaranku.R
import com.app.pengeluaranku.databinding.ActivityAddDataBinding
import com.app.pengeluaranku.model.entity.Pengeluaran

class AddDataActivity : AppCompatActivity() {
    private var binding: ActivityAddDataBinding? = null
    private var addDataViewModel: AddDataViewModel? = null
    private var mIsEdit = false
    private var mUid = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        addDataViewModel = ViewModelProviders.of(this).get(AddDataViewModel::class.java)
        loadData()
        initAction()
    }

    private fun initAction() {
        binding!!.btnSimpan.setOnClickListener {
            val note = binding!!.etKeterangan.text.toString()
            val price = binding!!.etHarga.text.toString()
            if (note.isEmpty() || price.isEmpty()) {
                Toast.makeText(this@AddDataActivity, getString(R.string.error_message_form_empty),
                        Toast.LENGTH_SHORT).show()
            } else {
                if (mIsEdit) {
                    addDataViewModel!!.updatePengeluaran(mUid, note, price.toInt())
                } else {
                    addDataViewModel!!.addPengeluaran(note, price.toInt())
                }
                finish()
            }
        }
    }

    private fun loadData() {
        mIsEdit = intent.getBooleanExtra(KEY_IS_EDIT, false)
        if (mIsEdit) {
            val pengeluaran: Pengeluaran = intent.getParcelableExtra(KEY_DATA)
            if (pengeluaran != null) {
                mUid = pengeluaran.uid
                val note = pengeluaran.keterangan
                val price = pengeluaran.harga
                binding!!.etKeterangan.setText(note)
                binding!!.etHarga.setText(price.toString())
            }
        }
    }

    companion object {
        private const val KEY_IS_EDIT = "key_is_edit"
        private const val KEY_DATA = "key_data"

        // Untuk kebutuhan data yang akan dipakai pada Activitu AddData
        @JvmStatic
        fun startActivity(context: Context, isEdit: Boolean, pengeluaran: Pengeluaran?) {
            val intent = Intent(Intent(context, AddDataActivity::class.java))
            intent.putExtra(KEY_IS_EDIT, isEdit)
            intent.putExtra(KEY_DATA, pengeluaran)
            context.startActivity(intent)
        }
    }
}