package com.cookandroid.happycup.ui.main.view.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cookandroid.happycup.R
import com.cookandroid.happycup.databinding.FragmentShopBinding
import com.cookandroid.happycup.ui.base.BaseFragment
import com.cookandroid.happycup.ui.main.view.activity.MainActivity
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.cookandroid.happycup.ui.adapter.ViewPagerAdapter2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator

// search view
import android.util.Log
import android.view.MotionEvent
import android.widget.*
import androidx.constraintlayout.widget.ConstraintSet
import com.cookandroid.happycup.R.layout.fragment_my2
import com.cookandroid.happycup.data.api.RetrofitManager
import com.cookandroid.happycup.util.Constants.TAG
import com.cookandroid.happycup.util.RESPONSE_STATUS
import com.cookandroid.happycup.util.SEARCH_TYPE
import com.cookandroid.happycup.util.onMyTextChanged
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import kotlinx.android.synthetic.main.fragment_my2.*
import kotlinx.android.synthetic.main.layout_button_search.*
import kotlinx.android.synthetic.main.layout_button_search.view.*
import kotlinx.android.synthetic.main.fragment_my2.*
import java.lang.IndexOutOfBoundsException

class ShopFragment : BaseFragment<FragmentShopBinding>(R.layout.fragment_shop) {

    private val MIN_SCALE = 0.85f // 뷰가 몇퍼센트로 줄어들 것인지
    private val MIN_ALPHA = 0.5f // 어두워지는 정도
    private var NUM_PAGES: Int = 0// 페이지 수를 정해둠


    override fun init() {
        super.init()
        binding.bnv.setupWithNavController(findNavController())
        var viewPager_icon1: ViewPager2 = binding.viewPager2
        var tabLayout_menu1: TabLayout = binding.tabLayoutMenu

        var viewPager_icon2: ViewPager2 = binding.viewPager3
        var tabLayout_menu2: TabLayout = binding.tabLayoutMenu2

        val menulist1: ArrayList<String> = ArrayList()
        val menulist2: ArrayList<String> = ArrayList()

        menulist1.add("플라스틱")
        menulist1.add("종이")
        menulist1.add("유리")
        menulist1.add("나무")

        menulist2.add("세일")
        menulist2.add("업데이트")
        menulist2.add("랭킹")
        menulist2.add("스페셜")

        search()
        logoSetOnClick()
        categorySetOnClick()
        recommendProductOnClick()
        brandOnClick()
        viewpage()
        scrollpage(viewPager_icon1, tabLayout_menu1, menulist1)
        scrollpage(viewPager_icon2, tabLayout_menu2, menulist2)
    }

    private fun search() {
        //var searchtermtextlayout: TextInputLayout = binding.searchTermTextLayout
        var searchtermedittext: TextInputEditText = binding.searchTermEditText
        var mainscrollview: ScrollView = binding.mainScrollview
        var btn_search1: MaterialButton = binding.includedSearchbtn.btn_search
        var bottomview: BottomNavigationView = binding.bnv

        searchtermedittext.setOnFocusChangeListener{ view, b ->
           if(b) bottomview.visibility = View.GONE
            else bottomview.visibility = View.VISIBLE
        }

        // 텍스트가 변경이 되었을때
        searchtermedittext.onMyTextChanged {

            // 입력된 글자가 하나라도 있다면
            if (it.toString().count() > 0) {
                // 검색 버튼을 보여준다.
                btn_search1.visibility = View.VISIBLE //included_btn 에 존재

                // 스크롤뷰를 올린다.
                mainscrollview.scrollTo(0, 200)

            } else {
                btn_search1.visibility = View.INVISIBLE
            }

            if (it.toString().count() == 12) {
                Log.d(TAG, "MainActivity - 에러 띄우기 ")
                Toast.makeText(context, "검색어는 12자 까지만 입력 가능합니다.", Toast.LENGTH_SHORT).show()
            }

        }

        // 검색 버튼 클릭시
        btn_search1.setOnClickListener {

            handleSearchButtonUi()

            val userSearchInput = searchtermedittext.text.toString()

            // 검색 api 호출
            RetrofitManager.instance.searchPhotos(
                searchTerm = searchtermedittext.text.toString(),
                completion = { responseState, responseDataArrayList ->

                    when (responseState) {
                        RESPONSE_STATUS.OKAY -> {

                            Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show()

                            Log.d(TAG, "api 호출 성공 : ${responseDataArrayList?.size}")

                            //val intent = Intent(context, PhotoCollectionActivity::class.java)

                            val intent = Intent(context, PhotoCollectionActivity::class.java)

                            val bundle = Bundle()

                            bundle.putSerializable("photo_array_list", responseDataArrayList)

                            intent.putExtra("array_bundle", bundle)

                            intent.putExtra("search_term", userSearchInput)

                            startActivity(intent)

                        }
                        RESPONSE_STATUS.FAIL -> {
                            Toast.makeText(context, "api 호출 에러입니다.", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "api 호출 실패 : $responseDataArrayList")
                        }

                        RESPONSE_STATUS.NO_CONTENT -> {
                            Toast.makeText(context, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    btn_progress.visibility = View.INVISIBLE
                    btn_search1.text = "검색"
                    searchtermedittext.setText("")

                })

        }
    }

    private fun handleSearchButtonUi() {

        var btn_search1: MaterialButton = binding.includedSearchbtn.btn_search
        var btn_progress: ProgressBar? = binding.includedSearchbtn.progressBar

        btn_progress?.visibility = View.VISIBLE

        btn_search1.text = ""

    }

    // 스크롤 카테고리
    private fun scrollpage(v2: ViewPager2, tb: TabLayout, arg: ArrayList<String>) {

        val tabTextList: ArrayList<String> = ArrayList()
        tabTextList.addAll(arg)

        //val tabTextList2 = arrayListOf(arg) // 메뉴에 들어갈 이름 (순서대로)
        NUM_PAGES = arg.size

        v2.adapter = ScreenSlidePagerAdapter(this.requireActivity())
        v2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // 뷰페이저와 탭레이아웃을 붙임
        TabLayoutMediator(tb, v2) { tab, position ->
            if (position != tabTextList.size) {
                tab.text = tabTextList[position]
            }
        }.attach()


    }

    //슬라이드 탭
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> MyFragment2()
                1 -> MyFragment2()
                2 -> MyFragment2()
                3 -> MyFragment2()
                else -> MyFragment2()
            }
        }
    }

    // 광고배너
    private fun viewpage() {
        var viewPager_icon: ViewPager2 = binding.viewPagerIdol

        var springDotsIndicator: SpringDotsIndicator = binding.springDotsIndicator

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pagerWidth)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        viewPager_icon.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        viewPager_icon.offscreenPageLimit = 1 //몇개의 페이지를 미리 로드해줄것인지
        viewPager_icon.adapter = ViewPagerAdapter2(getIconList())
        viewPager_icon.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager_icon.setPageTransformer(ZoomOutPageTransformer()) // 애니메이션 적용

        springDotsIndicator.setViewPager2(viewPager_icon) // indicator 설정
    }


    private fun getIconList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.ad, R.drawable.ad, R.drawable.ad)
    }

    inner class ZoomOutPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 1 -> { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        // Fade the page relative to its size.
                        alpha = (MIN_ALPHA +
                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }

    private fun logoSetOnClick() {
        binding.logo.setOnClickListener() {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

    private fun categorySetOnClick() {
        binding.category.setOnClickListener() {
            //카테고리 전환 메뉴바
            binding.category.isSelected = binding.category.isSelected != true
            Handler().postDelayed({
                binding.category.isSelected = false
            }, 500)
        }
    }

    private fun recommendProductOnClick() {

        binding.recommendProduct.setOnClickListener() {
            //추천상품 띄워주기
            binding.recommendProduct.isSelected = binding.recommendProduct.isSelected != true
            Handler().postDelayed({
                binding.recommendProduct.isSelected = false
            }, 500)

            binding.mainScrollview.smoothScrollBy(0, 3200)
        }
    }

    private fun brandOnClick() {
        binding.brand.setOnClickListener() {
            //브랜드 종류 보여주기
            binding.brand.isSelected = binding.brand.isSelected != true
            Handler().postDelayed({
                binding.brand.isSelected = false
            }, 500)

            binding.mainScrollview.smoothScrollBy(0, 1100)

        }
    }
}