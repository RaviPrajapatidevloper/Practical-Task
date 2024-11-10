package com.ravi.practicaltaskmvvm.data.network

sealed class ApiResponse<out T> {

    data class Success<out T>(val data: T?) : ApiResponse<T>()

    data class Loading(val isRefresh: Boolean = false) : ApiResponse<Nothing>()

    data class ServerError(val errorMessage: String) : ApiResponse<Nothing>()

}
