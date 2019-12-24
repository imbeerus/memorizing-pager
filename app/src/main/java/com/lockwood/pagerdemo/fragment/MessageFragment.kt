package com.lockwood.pagerdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.lockwood.pagerdemo.R

class MessageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.frag_message, container, false)
        val textView = rootView.findViewById<TextView>(R.id.tv_message)
        val position = arguments?.getInt(
            ARG_POSITION,
            DEF_POSITION
        )
        textView.text = (position?.plus(1)).toString()
        return rootView
    }

    companion object {

        private const val ARG_POSITION = "position"
        private const val DEF_POSITION = 0

        fun newInstance(position: Int): MessageFragment {
            val fragment = MessageFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }
}