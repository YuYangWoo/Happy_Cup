package com.cookandroid.happycup.ui.main.view.activity

import android.content.Intent
import android.util.Log
import com.cookandroid.happycup.R
import com.cookandroid.happycup.data.model.request.LoginRequest
import com.cookandroid.happycup.data.model.response.LoginResponse
import com.cookandroid.happycup.data.singleton.MySharedPreferences
import com.cookandroid.happycup.databinding.ActivityLoginBinding
import com.cookandroid.happycup.ui.base.BaseActivity
import com.cookandroid.happycup.ui.main.view.dialog.ProgressDialog
import com.cookandroid.happycup.ui.main.viewmodel.LoginViewModel
import com.cookandroid.happycup.util.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Observer

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val TAG = "LOGIN_ACTIVITY"
    private val model: LoginViewModel by viewModel()
    private var loginResponse = LoginResponse()
    private var loginRequest = LoginRequest()
    private val dialog by lazy {
        ProgressDialog(this)
    }

    override fun init() {
        super.init()
        checkAutoLogin()
        btnLogin()
        checkBox()
    }

    // 자동 로그인
    private fun checkAutoLogin() {
        if (MySharedPreferences.getCheck(this@LoginActivity) &&
            MySharedPreferences.getUserId(this@LoginActivity).isNotBlank() &&
            MySharedPreferences.getUserPass(this@LoginActivity).isNotBlank()
        ) {
            binding.edtId.editText!!.setText(MySharedPreferences.getUserId(this@LoginActivity))
            binding.edtPassword.editText!!.setText(MySharedPreferences.getUserPass(this@LoginActivity))
            binding.checkBox.isChecked = true
            loginRequest.id = binding.edtId.editText!!.text.toString()
            loginRequest.pw = binding.edtPassword.editText!!.text.toString()
            initViewModel()
        }
    }

    // 로그인 서버 API 통신
    private fun initViewModel() {
        model.loginApiCall(loginRequest).observe(this@LoginActivity,
            androidx.lifecycle.Observer { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        dialog.dismiss()
                        when (resource.data!!.code()) {
                            200 -> {
                                Log.d(TAG, "initViewModel: ${resource.data!!}")
                                loginResponse = resource.data.body()!!
                                loginApi()
                            }
                            else -> {
                                toast(this, "에러요")
                            }
                        }
                    }
                    Resource.Status.LOADING -> {
                        dialog.show()
                    }
                    Resource.Status.ERROR -> {
                        toast(
                            this,
                            resource.message + "\n" + resources.getString(R.string.connect_fail)
                        )
                        dialog.dismiss()
                    }
                }
            })
    }

    // 체크박스가 체크되어있는지 체크되어 있지 않은지
    private fun checkBox() {
        binding.checkBox.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) {
                MySharedPreferences.setCheck(this@LoginActivity, binding.checkBox.isChecked)
            } else {
                MySharedPreferences.setCheck(this@LoginActivity, binding.checkBox.isChecked)
                MySharedPreferences.clearUser(this@LoginActivity)
            }
        }
    }

    // 로그인 버튼 클릭
    private fun btnLogin() {
        binding.btnLogin.setOnClickListener {
//            input["id"] = binding.edtId.editText!!.text.toString()
//            input["password"] = binding.edtPassword.editText!!.text.toString()
            loginRequest.id = binding.edtId.editText!!.text.toString()
            loginRequest.pw = binding.edtPassword.editText!!.text.toString()
            initViewModel()
        }
    }

    // 로그인 체크
    private fun loginApi() {
        if(loginResponse.success) {
            toast(
                this@LoginActivity,
                "${loginResponse.name}님 ${resources.getString(R.string.confirm_login)}"
            )
            MySharedPreferences.setUserId(this@LoginActivity, binding.edtId.editText!!.text.toString())
            MySharedPreferences.setUserPass(this@LoginActivity, binding.edtPassword.editText!!.text.toString())
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()

        }
    }
}