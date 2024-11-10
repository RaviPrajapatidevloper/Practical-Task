package com.ravi.practicaltaskmvvm.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ravi.practicaltaskmvvm.R
import com.ravi.practicaltaskmvvm.common.BaseAdapter
import com.ravi.practicaltaskmvvm.data.model.RepositoryEntity
import com.ravi.practicaltaskmvvm.databinding.ListItemRepositoryBinding
import com.ravi.practicaltaskmvvm.ui.fragment.RepositoryViewModel
import com.ravi.practicaltaskmvvm.utils.ext.inflate
import com.ravi.practicaltaskmvvm.utils.roundToThousands

class RepositoryListAdapter(private val viewModel: RepositoryViewModel) :
    BaseAdapter<RepositoryEntity>(arrayListOf()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListViewHolder {
        val binding = ListItemRepositoryBinding.bind(parent.inflate(R.layout.list_item_repository))
        return RepositoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        when (holder) {
            is RepositoryListViewHolder -> {
                getSingleItem(position)?.let {
                    holder.bind(it)
                }
            }
        }
    }

    inner class RepositoryListViewHolder(private var binding: ListItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repositoryEntity: RepositoryEntity) = with(repositoryEntity) {
            binding.apply {
                repositoryNameTextView.text = name.plus(fullName)
                countNameTextView.text = roundToThousands(stargazersCount)
                itemLayout.setOnClickListener {
                    viewModel.onItemClick(repositoryEntity)
                }

                bookmarkImageView.setImageResource(if (bookmarked) R.drawable.ic_select_bookmark
                    else R.drawable.ic_unselect_bookmark
                )
            }

        }

    }


}