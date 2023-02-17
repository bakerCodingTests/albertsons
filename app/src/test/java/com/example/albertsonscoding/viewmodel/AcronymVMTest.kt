package com.example.albertsonscoding.viewmodel

import androidx.lifecycle.Observer
import com.example.albertsonscoding.R
import com.example.albertsonscoding.model.AcronymRepo
import com.example.albertsonscoding.testutils.CoroutineTestExtension
import com.example.albertsonscoding.testutils.InstantTaskExecutor
import com.example.albertsonscoding.view.search.SearchState
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantTaskExecutor::class)
internal class AcronymVMTest {

    @RegisterExtension
    private val coroutineTestExtension = CoroutineTestExtension()

    private val mockRepo = mockk<AcronymRepo>()

    @Test
    @DisplayName("Test getting Longforms")
    fun testGetLongForms() = runTest(coroutineTestExtension.dispatcher) {
        val shortform = "ta"
        val testLongforms = listOf("test answer")
        val viewModel = AcronymVM(mockRepo)

        val state = mutableListOf<SearchState>()
        val observer = Observer<SearchState> {
            state.add(it)
        }

        viewModel.state.observeForever(observer)

        coEvery { mockRepo.getLongforms(shortform) } coAnswers { testLongforms }
        viewModel.query.value = shortform
        viewModel.getLongforms()

        val firstState = SearchState()
        val secondState = SearchState(isLoading = true)
        val thirdState =
            SearchState(isLoading = false, longforms = testLongforms, showResultCount = true)
        Assertions.assertEquals(firstState, state[0])
        Assertions.assertEquals(secondState, state[1])
        Assertions.assertEquals(thirdState, state[2])

        viewModel.state.removeObserver(observer)
    }

    @Test
    @DisplayName("Test error handling")
    fun testErrorHandling() = runTest(coroutineTestExtension.dispatcher) {
        val exceptions = listOf(
            ExceptionWithMessage(IOException("Network Error"), R.string.network_error),
            ExceptionWithMessage(
                HttpException(
                    Response.error<List<String>>(
                        500,
                        ResponseBody.create(MediaType.get("plain/text"), "HTTP Error")
                    )
                ), R.string.http_error
            ),
            ExceptionWithMessage(
                SerializationException("Serialization error"),
                R.string.serial_error
            ),
            ExceptionWithMessage(NullPointerException("NPE"), R.string.generic_error),
            ExceptionWithMessage(IllegalAccessException("Other Exception"), R.string.generic_error)
        )
        val shortform = "ta"

        exceptions.forEach { exception ->
            val viewModel = AcronymVM(mockRepo)
            val state = mutableListOf<SearchState>()
            val observer = Observer<SearchState> {
                state.add(it)
            }
            viewModel.state.observeForever(observer)
            coEvery { mockRepo.getLongforms(shortform) } coAnswers { throw exception.exception }
            viewModel.query.value = shortform
            viewModel.getLongforms()

            val firstState = SearchState()
            val secondState = SearchState(isLoading = true)
            val thirdState = SearchState(
                isLoading = false,
                showResultCount = false,
                errorStringId = exception.errorInt,
                showError = true
            )
            Assertions.assertEquals(firstState, state[0])
            Assertions.assertEquals(secondState, state[1])
            Assertions.assertEquals(thirdState, state[2])

            viewModel.state.removeObserver(observer)
        }
    }

    private data class ExceptionWithMessage(
        val exception: Exception,
        val errorInt: Int
    )
}