package com.shibainu.li.httplib.base

import com.shibainu.li.httplib.entity.*

open class BaseHttpRepository {

    suspend fun <T> executeHttp(block: suspend () -> ApiResponse<T>): ApiResponse<T>{
        kotlin.runCatching {
            block.invoke()
        }.onSuccess {
            return handlerHttpOk(it)
        }.onFailure {
            return handleHttpError(it)
        }
        return ApiEmptyResponse()
    }

    private fun <T> handlerHttpOk(data: ApiResponse<T>): ApiResponse<T> {
        return if (data.isSuccess) {
            getHttpSuccessResponse(data)
        } else {
            ApiFailedResponse(data.errorCode,data.errorMsg)
        }
    }

    private fun <T> handleHttpError(e:Throwable): ApiErrorResponse<T>{
        return ApiErrorResponse(e)
    }


    private fun <T> getHttpSuccessResponse(response: ApiResponse<T>): ApiResponse<T>{
        val data = response.data
        return if(data == null || data is List<*> && (data as List<*>).isEmpty()){
            ApiEmptyResponse()
        }else{
            ApiSuccessResponse(data)
        }
    }

}