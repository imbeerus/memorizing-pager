package com.lockwood.memorizingpager_demo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MessageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.frag_message, container, false)
        val textView = rootView.findViewById<TextView>(R.id.tv_message)
        val position = arguments?.getInt(ARG_POSITION, 0)
        textView.text = (position?.plus(1)).toString()
        return rootView
    }

    companion object {

       private  const val ARG_POSITION = "position"

        fun newInstance(position: Int): MessageFragment {
            val fragment = MessageFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }
}