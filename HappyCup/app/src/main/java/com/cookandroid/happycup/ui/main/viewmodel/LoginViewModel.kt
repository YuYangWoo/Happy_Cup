package com.cookandroid.happycup.ui.main.viewmodel

import android.accounts.Account
import android.util.Log
import androidx.lifecycle.*
import com.cookandroid.happycup.data.model.request.LoginRequest
import com.cookandroid.happycup.data.repository.LoginRepository
import com.cookandroid.happycup.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    val TAG = "LOGIN_VIEWMODEL"

//    private var _loginData = MutableLiveData<Resource<Account>>()
//    val loginData: LiveData<Resource<Account>>
//        get() = _loginData
//
//    fun loginApiCall(loginRequest: LoginRequest) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _loginData.postValue(Resource.loading(null))
//            try {
//                _loginData.postValue(Resource.success(loginRepository.login(loginRequest).body()) as Resource<Account>?)
//            } catch (e: Exception) {
//                _loginData.postValue(Resource.error(null, e.message ?: "Error Occurred!"))
//            }
//        }
//    }

fun loginApiCall(loginRequest: LoginRequest) = liveData {
    emit(Resource.loading(null))
    try {
        emit(Resource.success(loginRepository.login(loginRequest)))
    } catch (e: Exception) {
        emit(Resource.error(null, e.message ?: "Error Occurred!"))
        Log.d(TAG, "loginApiCall: ${e.toString()}")
    }
}

}