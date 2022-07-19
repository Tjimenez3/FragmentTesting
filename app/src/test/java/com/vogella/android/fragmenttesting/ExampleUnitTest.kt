package com.vogella.android.fragmenttesting

import android.app.Application
import android.app.Instrumentation
import android.os.ParcelFileDescriptor.open
import androidx.core.content.ContentProviderCompat.requireContext
import com.vogella.android.fragmenttesting.api.APIRequest
import com.vogella.android.fragmenttesting.database.DatabaseService
import com.vogella.android.fragmenttesting.entity.LoginResponse
import com.vogella.android.fragmenttesting.entity.NewsItem
import com.vogella.android.fragmenttesting.entity.NewsModel
import com.vogella.android.fragmenttesting.repository.NewsRepositoryImpl
import com.vogella.android.fragmenttesting.viewModel.AuthenticateViewModel
import com.vogella.android.fragmenttesting.viewModel.NewsViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito
import java.io.InputStream
import java.lang.RuntimeException
import android.content.Context
import android.hardware.Camera.open
import androidx.test.platform.app.InstrumentationRegistry
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.nio.file.Files

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun apiNewsRequest() = runBlocking {
        val data = getNewsCall()
        assertTrue(data.articleList?.get(0)?.author == "Martha Schwendener")
    }
    private suspend fun getNewsCall(): NewsModel {
        val mockAPIRequest = Mockito.mock(APIRequest::class.java)
        val newsModel = readJSON()
        Mockito.`when`(mockAPIRequest.getNews()).thenReturn(newsModel)

        return mockAPIRequest.getNews()
    }

    @Test
    fun apiNewsRequest2() = runBlocking {
        val data = getEmptyNewsCall()
        assertTrue(data.articleList?.size!! == 0)
    }


    private suspend fun getEmptyNewsCall(): NewsModel {
        val mockAPIRequest = Mockito.mock(APIRequest::class.java)
        val dataList: List<NewsItem> = listOf()
        val newsModel = NewsModel(dataList, "fail", 0)
        Mockito.`when`(mockAPIRequest.getNews()).thenReturn(newsModel)

        return mockAPIRequest.getNews()
    }

    @Test()
    fun apiNewsRequest3() = runBlocking {
        val data = getLongNewsCall()
        assertTrue(data.articleList?.size!! > 0)

    }

    private suspend fun getLongNewsCall(): NewsModel {
        val mockAPIRequest = Mockito.mock(APIRequest::class.java)
        val newsModel = readJSON2()
        Mockito.`when`(mockAPIRequest.getNews()).thenReturn(newsModel)
        //read from the asset folder, NewsItem should be in Json file
        //create feature branch, make some changes, and then merge
        //read about pull request

        return mockAPIRequest.getNews()
    }

    @Test
    fun apiLoginRequest() = runBlocking {
        val data = getLoginCall()
        assertTrue(data.name == "Tony")
    }
    private suspend fun getLoginCall(): LoginResponse {
        val mockAPIRequest = Mockito.mock(APIRequest::class.java)

        val loginResponse = LoginResponse("200","Tony")
        Mockito.`when`(mockAPIRequest.getFakeCall()).thenReturn(loginResponse)
        return mockAPIRequest.getFakeCall()
    }
    @Test
    fun testJsonRead() {
        var newsModel = readJSON()
        assertTrue(newsModel.status == "ok")
    }

    private fun readJSON(): NewsModel{
        var fileInString= File("/Users/tony/AndroidStudioProjects/FragmentTesting/app/src/main/assets/data.json").readText()
        val mapper = jacksonObjectMapper()
        val newsModelFromJson = mapper.readValue<NewsModel>(fileInString)
        return newsModelFromJson
    }

    private fun readJSON2(): NewsModel{
        var fileInString= File("/Users/tony/AndroidStudioProjects/FragmentTesting/app/src/main/assets/data2.json").readText()
        val mapper = jacksonObjectMapper()
        val newsModelFromJson = mapper.readValue<NewsModel>(fileInString)
        return newsModelFromJson
    }


}