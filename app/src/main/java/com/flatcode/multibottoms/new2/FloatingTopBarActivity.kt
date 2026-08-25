package com.flatcode.multibottoms.new2

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flatcode.multibottoms.new2.adapters.ScreenSlidePagerAdapter
import com.flatcode.multibottoms.new2.fragment.ScreenSlidePageFragment

import com.flatcode.multibottoms.R
import com.flatcode.multibottoms.databinding.ActivityFloatingTopBarBinding

class FloatingTopBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFloatingTopBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFloatingTopBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingTopBarNavigation.setTypeface(Typeface.createFromAsset(assets, "rubik.ttf"))
        binding.floatingTopBarNavigation.setBadgeValue(0, "3")
        binding.floatingTopBarNavigation.setBadgeValue(1, "9+") //invisible badge

        val fragList = ArrayList<ScreenSlidePageFragment>()
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.home), R.color.red_inactive))
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.search), R.color.blue_inactive))
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.likes), R.color.blue_grey_inactive))
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.notification), R.color.green_inactive))
        val pagerAdapter = ScreenSlidePagerAdapter(fragList, supportFragmentManager)
        binding.viewPager.adapter = pagerAdapter
        //disable swipe
        binding.viewPager.setOnTouchListener { _, _ ->
            return@setOnTouchListener true
        }

        binding.floatingTopBarNavigation.setNavigationChangeListener { _, position ->
            binding.viewPager.setCurrentItem(position, true)
        }
    }


}
