package com.example.albertsonscoding.model

import com.example.albertsonscoding.model.remote.AcronymService
import com.example.albertsonscoding.model.remote.dto.LongformDTO
import com.example.albertsonscoding.model.remote.dto.ResponseDTO
import com.example.albertsonscoding.model.remote.dto.VariationDTO
import com.example.albertsonscoding.testutils.CoroutineTestExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
internal class AcronymRepoTest {

    @RegisterExtension
    private val coroutineTestExtension = CoroutineTestExtension()

    private val mockService = mockk<AcronymService>()
    private val repo = AcronymRepo(mockService)


    @Test
    @DisplayName("Test getting Longforms")
    fun testGetLongforms() = runTest(coroutineTestExtension.dispatcher) {
        val shortForm = "ta"
        val longform = "test answers"
        val testVars = listOf(VariationDTO(lf = "test answers", freq = 1, since = 10))
        val testLongform = LongformDTO(lf = longform, freq = 5, since = 11, vars = testVars)
        val testResponse = listOf( ResponseDTO(sf = shortForm, lfs = listOf(testLongform)))

        val expectedResult = listOf(longform)

        coEvery { mockService.getLongforms(shortForm) } coAnswers { Response.success(testResponse) }

        val actualResult = repo.getLongforms(shortForm)
        Assertions.assertEquals(expectedResult, actualResult)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @DisplayName("Test getting 0 results")
    fun testGetLongformZero() = runTest(coroutineTestExtension.dispatcher)  {
        val shortForm = "ta"
        val testResponse = emptyList<ResponseDTO>()

        val expectedResult = emptyList<String>()

        coEvery { mockService.getLongforms(shortForm) } coAnswers { Response.success(testResponse) }

        val actualResult = repo.getLongforms(shortForm)

        Assertions.assertEquals(expectedResult, actualResult)
    }
}