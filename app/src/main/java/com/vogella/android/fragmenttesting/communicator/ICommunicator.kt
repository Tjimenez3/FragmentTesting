package com.vogella.android.fragmenttesting.communicator

interface ICommunicator<T> {
    fun onCommunicate(data : T)
}