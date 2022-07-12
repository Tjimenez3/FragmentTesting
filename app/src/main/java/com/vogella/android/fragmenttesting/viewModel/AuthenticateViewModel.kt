package com.vogella.android.fragmenttesting.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vogella.android.fragmenttesting.App
import com.vogella.android.fragmenttesting.constants.RegexConstant.EMAIL_REGEX
import com.vogella.android.fragmenttesting.entity.LoginResponse
import com.vogella.android.fragmenttesting.repository.INewsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class AuthenticateViewModel(private val repository: INewsRepository): ViewModel(){

    var emailAddress: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()


    private val _isEmailIdIncorrect : MutableLiveData<Boolean> = MutableLiveData()
    val isEmailIdIncorrect : LiveData<Boolean> get() {
        return _isEmailIdIncorrect
    }

    private val _isPasswordIncorrect : MutableLiveData<Boolean> = MutableLiveData()
    val isPasswordIncorrect : LiveData<Boolean> get() {
        return _isPasswordIncorrect
    }

    private val _isProgressBarShown : MutableLiveData<Boolean> = MutableLiveData()
    val isProgressBarShown : LiveData<Boolean> get() {
        return _isProgressBarShown
    }

    private val _isAuthenticated : MutableLiveData<Boolean> = MutableLiveData()
    val isAuthenticated : LiveData<Boolean> get() {
        return _isAuthenticated
    }

    private val _isSessionActive : MutableLiveData<Boolean>  = MutableLiveData()
    val isSessionActive: LiveData<Boolean> get() {
        return _isSessionActive
    }

    private val _isError : MutableLiveData<Boolean>  = MutableLiveData()
    val isError: LiveData<Boolean> get() {
        return _isError
    }

    fun isEmail(email: String): Boolean{
        return EMAIL_REGEX.toRegex().matches(email)
    }
    fun passwordLengthCheck(password: String): Boolean {
        return password.length >=6
    }

    fun login(emailId:String, password: String) {
        if (!isEmail(emailId)) {
            _isEmailIdIncorrect.postValue(true)
        }
        else if(!passwordLengthCheck(password)) {
            _isPasswordIncorrect.postValue(true)
        }
        else {
            _isProgressBarShown.postValue(true)
            makeApiRequest()
        }
    }
    private fun makeApiRequest(){
        viewModelScope.launch {
            runCatching {
                repository.fetchFakeCall()
            }.onSuccess {
                processWithLoginResponse(it)
            }.onFailure {
                processWithError(it)
            }
        }
    }
    private fun processWithLoginResponse(loginResponse: LoginResponse) {
        _isProgressBarShown.postValue(false)
        if (loginResponse.sessionId.isNotEmpty()) {
            _isAuthenticated.postValue(true)
        }
        else {
            _isSessionActive.postValue(false)
        }
    }

    private fun processWithError(t: Throwable) {
        Timber.e(t)
        _isProgressBarShown.postValue(false)
        _isError.postValue(true)
    }





}