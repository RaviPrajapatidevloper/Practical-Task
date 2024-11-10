package com.ravi.practicaltaskmvvm.data.network

import com.ravi.practicaltaskmvvm.data.local.RepositoryDao
import com.ravi.practicaltaskmvvm.data.model.RepositoryEntity
import javax.inject.Inject

class ApiManger @Inject constructor(private val apiService: ApiService,private val repositoryDao: RepositoryDao):  Api {

    override suspend fun getRepositoryList(): ApiResponse<List<RepositoryEntity>> {
        val response = apiService.getRepositoryList()
        val data = response.body()

        return if (response.isSuccessful && data != null) {
            repositoryDao.apply {
                insertRepositories(data)
            }
            ApiResponse.Success(data)
        } else {
            ApiResponse.ServerError("Failed: ${response.message() ?: "Unknown error"}")
        }
    }
    }
