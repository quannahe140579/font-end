package com.example.instademo.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.instademo.R;
import com.example.instademo.activity.HomeActivity;
import com.example.instademo.adapter.PostAdapterCustome;
import com.example.instademo.api.ApiService;
import com.example.instademo.dto.PostDTO;
import com.example.instademo.mapper.PostMapper;
import com.example.instademo.model.Post;
import com.example.instademo.model.User;
import com.example.instademo.utils.LogedUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private PostAdapterCustome adapter;
    private ListView lvPost;

    private String mParam1;
    private String mParam2;
    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lvPost = view.findViewById(R.id.lv_post);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllPost();
    }

    public void getAllPost(){
        ApiService.apiService.getAllPost(LogedUser.logedUser.getId()).enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse(Call<List<PostDTO>> call, Response<List<PostDTO>> response) {
                List<PostDTO> listDTO = response.body();
                if(listDTO != null){
                    LogedUser.listPost = PostMapper._toListModel(listDTO);
                    adapter = new PostAdapterCustome(getActivity(),LogedUser.listPost);
                    lvPost.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PostDTO>> call, Throwable t) {
                Log.i("error","Error");
            }
        });
    }
}