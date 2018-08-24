package com.examples.memorizingpager_demo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MessageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_message, container, false)
            val textView = rootView.findViewById<TextView>(R.id.tv_message)

            val position = arguments?.getInt("position", 0)
            textView.text = (position?.plus(1)).toString()
            return rootView
        }

    companion object {

        fun newInstance(position: Int): MessageFragment {
            val fragment = MessageFragment()
            val args = Bundle()
            args.putInt("position", position)
            fragment.arguments = args
            return fragment
        }
    }

}
