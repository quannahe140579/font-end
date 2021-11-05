package com.example.instademo.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.instademo.MainActivity;
import com.example.instademo.R;
import com.example.instademo.activity.HomeActivity;
import com.example.instademo.adapter.NotifyAdapter;
import com.example.instademo.model.Announce;
import com.example.instademo.model.Post;
import com.example.instademo.utils.LogedUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {
    private ListView lvNotify;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {

    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        lvNotify = view.findViewById(R.id.lv_listFriend);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NotifyAdapter adapter = new NotifyAdapter(getActivity(), LogedUser.logedUser.getListAnnounce());
        lvNotify.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lvNotify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Announce announce = LogedUser.logedUser.getListAnnounce().get(i);
                Post post = getPostByAnnounceId(announce.getPost_id());
                if(post != null){
                    HomeActivity activity = (HomeActivity) getActivity();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("post",post);
                    DetailPostFragment fragment = new DetailPostFragment();
                    fragment.setArguments(bundle);
                    activity.replaceFragment(fragment);
                }
            }
        });
    }
    private Post getPostByAnnounceId(long id){
        for(int i = 0; i < LogedUser.listPost.size(); i++){
            if(LogedUser.listPost.get(i).getId() == id){
                return LogedUser.listPost.get(i);
            }
        }
        return null;
    }
}