package com.vogella.android.fragmenttesting

import com.vogella.android.fragmenttesting.api.APIRequest
import com.vogella.android.fragmenttesting.entity.LoginResponse
import com.vogella.android.fragmenttesting.entity.NewsItem
import com.vogella.android.fragmenttesting.entity.NewsModel
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

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

    @Test
    fun apiNewsRequest3() = runBlocking {
        val data = getLongNewsCall()
        assertTrue(data.articleList?.size!! > 0)

    }

    private suspend fun getLongNewsCall(): NewsModel {
        val mockAPIRequest = Mockito.mock(APIRequest::class.java)
        val newsModel = readJSON2()
        Mockito.`when`(mockAPIRequest.getNews()).thenReturn(newsModel)

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
        val newsModel = readJSON()
        assertTrue(newsModel.status == "ok")
    }

    private fun readJSON(): NewsModel {
        val fileInString =
            File("/Users/tony/AndroidStudioProjects/FragmentTesting/app/src/main/assets/data.json").readText()
        val mapper = jacksonObjectMapper()
        return mapper.readValue(fileInString)
    }

    private fun readJSON2(): NewsModel {
        val fileInString =
            File("/Users/tony/AndroidStudioProjects/FragmentTesting/app/src/main/assets/data2.json").readText()
        val mapper = jacksonObjectMapper()
        return mapper.readValue(fileInString)
    }


}