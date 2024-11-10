package com.ravi.practicaltaskmvvm.data.network


import com.ravi.practicaltaskmvvm.data.model.RepositoryEntity

interface Api {
    suspend fun getRepositoryList(): ApiResponse<List<RepositoryEntity>>
}