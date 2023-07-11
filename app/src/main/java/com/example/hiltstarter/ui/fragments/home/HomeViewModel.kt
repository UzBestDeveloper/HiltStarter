package com.example.hiltstarter.ui.fragments.home

import com.example.hiltstarter.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel() {

    fun login(){
        repository.login("998943296949","password").proceed {

        }
    }
}