package com.cookandroid.happycup.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.cookandroid.happycup.data.repository.MainRepository
import com.cookandroid.happycup.util.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private var _plasticData = MutableLiveData<Resource<MultipartBody.Part>>()
    val plasticData: LiveData<Resource<MultipartBody.Part>>
        get() = _plasticData
    private var _kind = MutableLiveData<String>()
    val kind: LiveData<String>
        get() = _kind
//    fun plasticApiCall(file: MultipartBody.Part) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _plasticData.postValue(Resource.loading(null))
//            try {
//                _plasticData.postValue(Resource.success(mainRepository.plastic(file).body()) as Resource<MultipartBody.Part>)
//            } catch (e: Exception) {
//                _plasticData.postValue(Resource.error(null, e.message ?:"Error Occurred!"))
//            }
//        }
//    }

    fun plasticApiCall2(file: RequestBody) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.analsisPlastic(file)))
        } catch (e: java.lang.Exception) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
            Log.d("TAG", "${e.toString()} ")
        }
    }

    fun paperApiCall1(file: RequestBody) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.analsisPaper(file)))
        } catch (e: java.lang.Exception) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
            Log.d("TAG", "${e.toString()} ")
        }
    }

    fun paperApiCall2(file: RequestBody) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.analsisPaperTwo(file)))
        } catch (e: java.lang.Exception) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
            Log.d("TAG", "${e.toString()} ")
        }
    }

    fun getKind(string: String) {
        _kind.value = string
    }
}