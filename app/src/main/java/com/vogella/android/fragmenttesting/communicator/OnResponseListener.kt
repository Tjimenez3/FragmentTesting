package com.vogella.android.fragmenttesting.communicator

interface OnResponseListener<T> {
    fun onSuccess(data: T)
    fun onError(message: Throwable)
}