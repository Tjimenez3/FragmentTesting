package com.vogella.android.fragmenttesting.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vogella.android.fragmenttesting.constants.FormatConstant.OUTPUT_DATE_FORMAT
import com.vogella.android.fragmenttesting.uttilities.DateParsingUtils
import com.vogella.android.fragmenttesting.entity.NewsItem
import com.vogella.android.fragmenttesting.repository.INewsRepository
import com.vogella.android.fragmenttesting.database.DatabaseService
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.InputStream

class NewsViewModel(private val repository: INewsRepository): ViewModel(){

    private val _isProgressBarShown : MutableLiveData<Boolean> = MutableLiveData()
    val isProgressBarShown : LiveData<Boolean> get() {
        return _isProgressBarShown
    }

    private val _isError: MutableLiveData<Throwable> = MutableLiveData()
    val isError: LiveData<Throwable> get() {
        return _isError
    }

    private val _responseNewsItems : MutableLiveData<List<NewsItem>> = MutableLiveData()
    val responseNewsItems : LiveData<List<NewsItem>>  get() {
        return _responseNewsItems
    }
    private val _isNewsListEmpty : MutableLiveData<Boolean> = MutableLiveData()
    val isNewsListEmpty : LiveData<Boolean>  get() {
        return _isNewsListEmpty
    }

    fun makeApiRequest(){
        viewModelScope.launch {
            runCatching {
                repository.fetchNews()
            }.onSuccess {
                processWithNewsResponse(it)
            }.onFailure {
                processWithError(it)
            }
        }
    }

    private fun processWithNewsResponse(newsResponse: List<NewsItem>?) {
        var sortedNewsResponse = newsResponse?.filter {
            it.description?.isEmpty() == false

        }?.sortedBy {
            it.publishedAt?.let { it1 -> DateParsingUtils().getLongDate(it1,OUTPUT_DATE_FORMAT) }
        }

        if (sortedNewsResponse?.isEmpty() == true) {
            _isNewsListEmpty.postValue(true)
        }
        else {
            _responseNewsItems.postValue(sortedNewsResponse)
        }
        _isProgressBarShown.postValue(false)
    }

    private fun processWithError(t: Throwable) {
        Timber.e(t)
        _isProgressBarShown.postValue(false)
        _isError.postValue(t)
    }

}