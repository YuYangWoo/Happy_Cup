package com.cookandroid.happycup.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookandroid.happycup.data.repository.MainRepository
import com.cookandroid.happycup.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private var _plasticData = MutableLiveData<Resource<MultipartBody.Part>>()
    val plasticData: LiveData<Resource<MultipartBody.Part>>
        get() = _plasticData

    fun plasticApiCall(file: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO) {
            _plasticData.postValue(Resource.loading(null))
            try {
                _plasticData.postValue(Resource.success(mainRepository.plastic(file).body()) as Resource<MultipartBody.Part>)
            } catch (e: Exception) {
                _plasticData.postValue(Resource.error(null, e.message ?:"Error Occurred!"))
            }
        }
    }
}