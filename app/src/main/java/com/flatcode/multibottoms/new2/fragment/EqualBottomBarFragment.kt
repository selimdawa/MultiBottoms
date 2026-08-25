package com.flatcode.multibottoms.new2.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flatcode.multibottoms.R
import com.flatcode.multibottoms.databinding.FragmentEqualBottomBarBinding
import com.flatcode.multibottoms.new2.adapters.ScreenSlidePagerAdapter

class EqualBottomBarFragment : Fragment() {

    private var _binding: FragmentEqualBottomBarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEqualBottomBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragList = ArrayList<ScreenSlidePageFragment>()
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.shop), R.color.blue_inactive))
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.photos), R.color.purple_inactive))
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.call), R.color.green_inactive))
        val pagerAdapter = ScreenSlidePagerAdapter(fragList, childFragmentManager)
        binding.viewPager.adapter = pagerAdapter
        //disable swipe
        binding.viewPager.setOnTouchListener { _, _ ->
            return@setOnTouchListener true
        }

        binding.equalNavigationBar.setNavigationChangeListener { _, position ->
            binding.viewPager.setCurrentItem(position, true)
        }

        //change the initial activate element
        val newInitialPosition = 2
        binding.equalNavigationBar.setCurrentActiveItem(newInitialPosition)
        binding.viewPager.setCurrentItem(newInitialPosition, false)
    }


}
