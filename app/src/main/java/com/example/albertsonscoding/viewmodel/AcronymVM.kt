package com.example.albertsonscoding.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.albertsonscoding.R
import com.example.albertsonscoding.model.AcronymRepo
import com.example.albertsonscoding.view.search.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import retrofit2.HttpException

@HiltViewModel
class AcronymVM @Inject constructor(private val acronymRepo: AcronymRepo) : ViewModel() {
    private val _screenState = MutableLiveData(SearchState())
    val state: LiveData<SearchState> get() = _screenState
    var query = MutableLiveData("")

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorStringId = when (throwable) {
            is IOException -> R.string.network_error
            is HttpException -> R.string.http_error
            is SerializationException -> R.string.serial_error
            is NullPointerException -> R.string.generic_error
            else -> R.string.generic_error
        }
        _screenState.value = _screenState.value?.copy(
            isLoading = false,
            showResultCount = false,
            errorStringId = errorStringId,
            showError = true
        )
    }

    fun getLongforms() {
        viewModelScope.launch(exceptionHandler) {
            _screenState.value = SearchState(isLoading = true)
            val longforms = acronymRepo.getLongforms(query.value!!)
            _screenState.value = _screenState.value!!.copy(
                longforms = longforms,
                isLoading = false,
                showResultCount = true,
            )
        }
    }
}
