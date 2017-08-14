package com.examples.memorizingpager_demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MessageFragment extends Fragment {

  public static MessageFragment newInstance(int position) {
    MessageFragment fragment = new MessageFragment();
    Bundle args = new Bundle();
    args.putInt("position", position);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_message, container, false);
    TextView textView = rootView.findViewById(R.id.tv_message);

    int position = getArguments().getInt("position", 0);
    textView.setText(String.valueOf(position + 1));
    return rootView;
  }

}
