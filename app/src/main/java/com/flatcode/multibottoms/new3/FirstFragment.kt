package com.flatcode.multibottoms.new3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import com.flatcode.multibottoms.R

/**
 * A simple [Fragment] subclass.
 */
class FirstFragment : Fragment(R.layout.fragment_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView=view.findViewById<TextView>(R.id.textView)
        textView.setOnClickListener {
            (requireActivity() as MainActivity).setSelectedItem(2)
            (requireActivity() as MainActivity).removeBadge(2)
        }


        view.post {
            if (isAdded) {
                (requireActivity() as MainActivity).setBadge(2)
                (requireActivity() as MainActivity).setBadge(0)
            }
        }
    }

}
