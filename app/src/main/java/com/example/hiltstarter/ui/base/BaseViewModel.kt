package com.example.hiltstarter.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltstarter.utils.sharedPref.preferences.PreferencesManager
import com.example.hiltstarter.utils.ErrorData
import com.example.hiltstarter.utils.exception
import com.example.hiltstarter.utils.handle
import com.example.hiltstarter.utils.lifecycle.SingleEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    protected lateinit var prefs: PreferencesManager

    //@Inject
    //protected lateinit var authRepository: AuthRepository

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean> get() = _loader
    private val _errorData = SingleEvent<ErrorData>()
    val errorData: LiveData<ErrorData> get() = _errorData

    fun <T> Flow<T>.proceed(action: suspend (T) -> Unit = { }): Job {
        return onStart { start() }
            .catch {
                it.exception(it.stackTraceToString())
                loader(false)
                catch(it)
            }
            .onEach {
                loader(false)
                action.invoke(it)
            }
            .launchIn(viewModelScope)
    }

    open fun start() {
        loader(true)
    }

    private fun catch(cause: Throwable) {
        val errorData = cause.handle()
        if (errorData != null) {
            if (errorData.code == "401") {
                prefs.token = ""
//                authRepository.refresh().proceed {
//                    prefs.token = it.data.accessToken
//                }
                _errorData.value = errorData
            } else {
                _errorData.value = errorData
            }
        } else println("error ${this.javaClass.name} -> null")
        println("error ${this.javaClass.name} -> ${cause.message ?: cause.localizedMessage}")
    }

    private fun loader(isLoading: Boolean) {
        _loader.postValue(isLoading)
    }
}