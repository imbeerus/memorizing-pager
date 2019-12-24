package com.lockwood.pagerdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main), View.OnClickListener {

    private val buttons = arrayOf(R.id.pager, R.id.pager2, R.id.frame)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buttons.forEach { findViewById<View>(it).setOnClickListener(this) }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.pager -> launchActivity<PagerActivity>()
            R.id.pager2 -> launchActivity<PagerSecondActivity>()
            R.id.frame -> launchActivity<FrameActivity>()
            else -> {
            }
        }
    }

    private inline fun <reified T : Any> Context.launchActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }


}
