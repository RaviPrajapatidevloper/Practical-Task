package com.ravi.practicaltaskmvvm.ui.fragment


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ravi.practicaltaskmvvm.R
import com.ravi.practicaltaskmvvm.common.BaseFragment
import com.ravi.practicaltaskmvvm.common.INTENT_REPOSITORT_MODEL
import com.ravi.practicaltaskmvvm.databinding.FragmentRepositoryBinding
import com.ravi.practicaltaskmvvm.ui.adapter.RepositoryListAdapter
import com.ravi.practicaltaskmvvm.utils.ext.handleApiListView
import com.ravi.practicaltaskmvvm.utils.ext.navigate
import com.ravi.practicaltaskmvvm.utils.observeNotNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryFragment : BaseFragment<FragmentRepositoryBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRepositoryBinding
        get() = FragmentRepositoryBinding::inflate

    private val repositoryViewModel: RepositoryViewModel by activityViewModels()

    private lateinit var listAdapter: RepositoryListAdapter

    override fun setup() {
        setupRecyclerView()
        setupObserver()
    }

    private fun setupRecyclerView() {
        listAdapter = RepositoryListAdapter(repositoryViewModel)
        binding?.recycleView?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = listAdapter
        }
    }

    private fun setupObserver() {
        observeNotNull(repositoryViewModel.repositoryList) { apiResponse ->
            apiResponse.handleApiListView(
                progressLayout = binding?.mainLayout,
                isSuccess = {
                    it?.let { it1 -> listAdapter.addAll(it1) }
                }
            )
        }
        observeNotNull(repositoryViewModel.selectedId){
            navigate(R.id.detailsFragment, argsBuilder = {
                putParcelable(INTENT_REPOSITORT_MODEL,it)
            })

        }

    }
}