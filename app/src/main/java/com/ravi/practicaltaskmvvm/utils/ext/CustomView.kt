package com.ravi.practicaltaskmvvm.utils.ext


import com.ravi.practicaltaskmvvm.data.network.ApiResponse
import com.ravi.practicaltaskmvvm.utils.widget.ApiViewStateConstraintLayout

fun <T> ApiResponse<T>?.handleApiListView(
    progressLayout: ApiViewStateConstraintLayout?,
    isSuccess: (t: T?) -> Unit = {}
) {
    when (this) {
        is ApiResponse.Loading -> {
            if (!isRefresh)
                progressLayout?.showProgress()
        }
        is ApiResponse.Success -> {
            progressLayout?.showContent()
            isSuccess(data)
        }
        else -> {}
    }
}



