package com.ravi.practicaltaskmvvm.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravi.practicaltaskmvvm.data.local.RepositoryDao
import com.ravi.practicaltaskmvvm.data.model.RepositoryEntity
import com.ravi.practicaltaskmvvm.data.network.Api
import com.ravi.practicaltaskmvvm.data.network.ApiResponse
import com.ravi.practicaltaskmvvm.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val api: Api,
    private val repositoryDao: RepositoryDao
) : ViewModel() {


    private val _repositoryList = MutableLiveData<ApiResponse<List<RepositoryEntity>>>()
    val repositoryList: LiveData<ApiResponse<List<RepositoryEntity>>> = _repositoryList

    private val _selectedItemClick = SingleLiveEvent<RepositoryEntity>()
    val selectedId: LiveData<RepositoryEntity> = _selectedItemClick


    init {
        getRepositories()
    }

    private fun getRepositories() {
        _repositoryList.value = ApiResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = runCatching {
                val cachedRepositories = repositoryDao.getAllRepositories()
                if (cachedRepositories.isNotEmpty()) {
                    return@runCatching ApiResponse.Success(cachedRepositories)
                }
                api.getRepositoryList()
            }

            result.onSuccess { response ->
                _repositoryList.postValue(response)
            }
            result.onFailure { exception ->
                _repositoryList.postValue(ApiResponse.ServerError("An error occurred: ${exception.message}"))
            }
        }
    }

    fun onItemClick(repositoryEntity: RepositoryEntity) {
        _selectedItemClick.value = repositoryEntity
    }

    fun toggleBookmark(repository: RepositoryEntity) {
        viewModelScope.launch {
            val updatedRepository = repository.copy(bookmarked = !repository.bookmarked)
            repositoryDao.updateBookmarkStatus(repository.id, updatedRepository.bookmarked)

        }
    }

}



