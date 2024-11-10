package com.ravi.practicaltaskmvvm.data.network

import com.ravi.practicaltaskmvvm.data.model.RepositoryEntity
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("orgs/square/repos")
   suspend fun getRepositoryList():Response<List<RepositoryEntity>>
}