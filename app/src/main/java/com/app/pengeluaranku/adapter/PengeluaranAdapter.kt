package com.app.pengeluaranku.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.pengeluaranku.databinding.ItemPengeluaranBinding
import com.app.pengeluaranku.model.entity.Pengeluaran
import com.app.pengeluaranku.utils.FunctionHelper.rupiahFormat

class PengeluaranAdapter(private val context: Context, private var list: MutableList<Pengeluaran>, private val mAdapterCallback: PengeluaranAdapterCallback) : RecyclerView.Adapter<PengeluaranAdapter.ViewHolder>() {
    private var binding: ItemPengeluaranBinding? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPengeluaranBinding.inflate(LayoutInflater
                .from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun clear() {
        val size = list.size
        list.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun addData(pengeluarans: MutableList<Pengeluaran>) {
        list = pengeluarans
        notifyDataSetChanged()
    }

    inner class ViewHolder internal constructor(itemView: ItemPengeluaranBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun bindData(item: Pengeluaran) {
            val price = item.harga
            val initPrice = rupiahFormat(price)
            binding!!.tvPrice.text = initPrice
            val note = item.keterangan
            binding!!.tvNote.text = note
        }

        init {
            itemView.root.setOnLongClickListener {
                val pengeluaran = list[adapterPosition]
                mAdapterCallback.onEdit(pengeluaran)
                true
            }
            binding!!.ivDelete.setOnClickListener {
                val pengeluaran = list[adapterPosition]
                mAdapterCallback.onDelete(pengeluaran)
            }
        }
    }

    interface PengeluaranAdapterCallback {
        fun onEdit(pengeluaran: Pengeluaran?)
        fun onDelete(pengeluaran: Pengeluaran?)
    }

    companion object {
        private val TAG = PengeluaranAdapter::class.java.simpleName
    }
}