package com.flatcode.multibottoms.new2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flatcode.multibottoms.R
import com.flatcode.multibottoms.databinding.FragmentScreenSlidePageBinding

private const val ARG_TITLE = "arg_title"
private const val ARG_BG_COLOR = "arg_bg_color"

/**
 * A simple [Fragment] subclass.
 * Use the [ScreenSlidePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ScreenSlidePageFragment : Fragment() {
    private var title: String? = "Default title."
    private var bgColorResId: Int = R.color.blue_inactive

    private var _binding: FragmentScreenSlidePageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            bgColorResId = it.getInt(ARG_BG_COLOR)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentScreenSlidePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), bgColorResId))
        binding.screenSlideTitle.text = title
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param title Title parameter
         * @param bgColorId Background Color
         * @return A new instance of fragment ScreenSlidePageFragment.
         */
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            ScreenSlidePageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putInt(ARG_BG_COLOR, bgColorId)
                }
            }
    }
}
