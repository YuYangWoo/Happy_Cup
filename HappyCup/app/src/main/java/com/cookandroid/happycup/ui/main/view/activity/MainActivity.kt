package com.cookandroid.happycup.ui.main.view.activity

import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.ActivityMainBinding
import com.cookandroid.happycup.ui.base.BaseActivity
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import net.daum.mf.map.api.MapView


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).navController
    }

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(R.id.mainFragment),
            binding.drawer
        )
    }

    override fun init() {
        super.init()
        initSupportActionBar()
        initNavigationView()
    }

    // 툴바 구현 + 툴바에 Controller와 appBarConfiguration 결합
    private fun initSupportActionBar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    // 네비게이션뷰와 navController 결합
    private fun initNavigationView() {
        with(binding) {
            navigation.setupWithNavController(navController)
        }
    }

    // AppBar에 생성되는 뒤로가기 버튼
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // 뒤로 가기 버튼을 누르면
    override fun onBackPressed() {
        with(binding) {
            if (drawer.isDrawerOpen(navigation)) {
                drawer.closeDrawer(navigation)
            } else {
                super.onBackPressed()
            }
        }
    }
}