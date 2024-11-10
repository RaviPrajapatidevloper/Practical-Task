package com.ravi.practicaltaskmvvm.common

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ravi.practicaltaskmvvm.R
import com.ravi.practicaltaskmvvm.utils.ext.inflate
import kotlinx.android.extensions.LayoutContainer


open class BaseAdapter<T>(val dataList: ArrayList<T?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val LIST_ITEM_PROGRESS = 101
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProgressViewHolder(parent.inflate(R.layout.list_item_progress))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataList.getOrNull(position) == null) {
            LIST_ITEM_PROGRESS
        } else super.getItemViewType(position)
    }

    fun addAll(list: List<T?>) {
        dataList.addAll(list)
        notifyItemChanged(
            dataList.lastIndex - list.lastIndex,
            dataList.lastIndex
        )
    }

    fun clearAllItems() {
        dataList.clear()
        dataList.trimToSize()
        notifyDataSetChanged()
    }

    fun addLoadMore() {
        if (dataList[dataList.lastIndex] != null) {
            dataList.add(null)
            notifyItemInserted(dataList.lastIndex)
        }
    }

    fun removeLoadMore(isNotify: Boolean = false) {
        if (dataList.isNotEmpty() && dataList[dataList.lastIndex] == null) {
            if (isNotify)
                notifyItemRemoved(dataList.lastIndex)
            dataList.removeAt(dataList.lastIndex)
        }
    }

    fun isLoadMore() = dataList.getOrNull(dataList.lastIndex) == null

    fun getItemsList() = dataList

    fun getSingleItem(position: Int) = dataList[position]

    fun updateItem(positionToUpdate: Int) {
        notifyItemChanged(positionToUpdate, dataList[positionToUpdate])
    }

    fun removeItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ProgressViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer
}
