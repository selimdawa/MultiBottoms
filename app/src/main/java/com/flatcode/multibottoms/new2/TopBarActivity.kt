package com.flatcode.multibottoms.new2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.flatcode.multibottoms.R
import com.flatcode.multibottoms.databinding.ActivityTopBarBinding
import com.flatcode.multibottoms.new2.adapters.ScreenSlidePagerAdapter
import com.flatcode.multibottoms.new2.fragment.ScreenSlidePageFragment

class TopBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragList = ArrayList<ScreenSlidePageFragment>()
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.restaurant), R.color.orange_inactive))
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.room), R.color.red_inactive))
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.happy), R.color.green_inactive))
        val pagerAdapter = ScreenSlidePagerAdapter(fragList, supportFragmentManager)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                binding.topNavigationConstraint.setCurrentActiveItem(p0)
            }

        })

        binding.topNavigationConstraint.setNavigationChangeListener { _, position ->
            binding.viewPager.setCurrentItem(position, true)
        }
    }


}
