package com.vogella.android.fragmenttesting

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
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun emailIsCorrect() {
        assertTrue(AuthenticateViewModel(NewsRepositoryImpl(App.getFakeApi(),App.getDatabase())).isEmail("Tony.jimenez@mphasis.com"))

    }

    @Test
    fun emailIsIncorrect() {
        assertFalse(AuthenticateViewModel(NewsRepositoryImpl(App.getFakeApi(),App.getDatabase())).isEmail("google.com"))
    }

    @Test
    fun passwordIsNotLongEnough() {
        assertFalse(AuthenticateViewModel(NewsRepositoryImpl(App.getFakeApi(),App.getDatabase())).passwordLengthCheck("1234"))
    }
    @Test
    fun passwordIsLongEnough() {
        assertTrue(AuthenticateViewModel(NewsRepositoryImpl(App.getFakeApi(),App.getDatabase())).passwordLengthCheck("Banana123"))
    }
    @Test
    fun apiNewsRequest() = runBlocking {
        var data = getNewsCall()
        assertTrue(data.articleList?.get(0)?.author == "Joe Mario Pedersen")
    }
    private suspend fun getNewsCall(): NewsModel {
        val mockAPIRequest = Mockito.mock(APIRequest::class.java)
        val dataList: List<NewsItem> = listOf(
            NewsItem(
                "https://www.orlandosentinel.com/resizer/jbTZ-j-lTlPukZO95ED5GaDFZTk=/1200x630/filters:format(jpg):quality(70)/cloudfront-us-east-1.images.arcpublishing.com/tronc/TPGGYELWEVCWLPXQQ2KE4HEHYA.jpg",
                "Hurricane center still eyeing westbound Caribbean; forecast to become Tropical Storm Bonnie tonight - Orlando Sentinel",
                "A westbound Caribbean disturbance still has poor organization but is still strongly suspected to become Tropical Storm Bonnie later today, according to the National Hurricane Center.",
                "2022-06-29T12:32:16Z",
                "https://www.orlandosentinel.com/weather/os-ne-hurricane-center-tropical-waves-wednesday-update-20220629-aiu7y7mkrzav7lpwaqvrl73aki-story.html",
                "Joe Mario Pedersen",
                "A westbound Caribbean disturbance still has poor organization but is still strongly suspected of becoming Tropical Storm Bonnie later today, according to the National Hurricane Center.\r\nIf I just too… [+4014 chars]"
            )
        )
        val newsModel = NewsModel(dataList, "ok", 1)
        Mockito.`when`(mockAPIRequest.getNews()).thenReturn(newsModel)

        return mockAPIRequest.getNews()
    }
    //test with empty json
    //test with publishedAt as a long

    @Test
    fun apiLoginRequest() = runBlocking {
        var data = getLoginCall()
        assertTrue(data.name == "Tony")
    }
    private suspend fun getLoginCall(): LoginResponse {
        val mockAPIRequest = Mockito.mock(APIRequest::class.java)

        val loginResponse = LoginResponse("200","Tony")
        Mockito.`when`(mockAPIRequest.getFakeCall()).thenReturn(loginResponse)
        return mockAPIRequest.getFakeCall()
    }


}