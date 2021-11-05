package com.example.instademo.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.instademo.R;
import com.example.instademo.adapter.PostAdapterCustome;
import com.example.instademo.model.Post;

import java.util.ArrayList;
import java.util.List;

public class DetailPostFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Post p;
    private ListView lvDetail;

    public DetailPostFragment() {
        // Required empty public constructor
    }
    public static DetailPostFragment newInstance(String param1, String param2) {
        DetailPostFragment fragment = new DetailPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        p = (Post) bundle.getSerializable("post");
        View view = inflater.inflate(R.layout.fragment_detail_post, container, false);
        lvDetail = view.findViewById(R.id.lv_detail_post);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(p != null){
            List<Post> list = new ArrayList<>();
            list.add(p);

            PostAdapterCustome adapterCustome = new PostAdapterCustome(getActivity(),list);
            lvDetail.setAdapter(adapterCustome);
            adapterCustome.notifyDataSetChanged();
        }
    }
}