package com.flatcode.multibottoms.new2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.multibottoms.R
import com.flatcode.multibottoms.new2.fragment.EqualBottomBarFragment

class EqualBottomBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equal_bottom_bar)

        var fragment =
            supportFragmentManager.findFragmentById(R.id.content_frame) as EqualBottomBarFragment?
        if (fragment == null) {
            fragment = EqualBottomBarFragment()
            addFragment(fragment, R.id.content_frame)
        }
    }

    private fun addFragment(fragment: EqualBottomBarFragment, id: Int) {
        val ft = supportFragmentManager.beginTransaction()
        ft.add(id, fragment)
        ft.commit()
    }

}
