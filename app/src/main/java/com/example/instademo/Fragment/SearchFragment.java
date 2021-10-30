package com.example.instademo.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.instademo.R;
import com.example.instademo.adapter.FriendAdapter;
import com.example.instademo.api.ApiService;
import com.example.instademo.dto.FriendDTO;
import com.example.instademo.mapper.FriendMapper;
import com.example.instademo.model.Friend;
import com.example.instademo.utils.LogedUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ListView lvFriend;
    private EditText etSearch;
    List<Friend> listFr;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (listFr != null) {
            getData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        lvFriend = view.findViewById(R.id.lv_listFriend);
        etSearch = view.findViewById(R.id.tv_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getData();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        imgAvt = view.findViewById(R.id.img_profile_cmt);
//        tvUsername = view.findViewById(R.id.tvUserName);
//        tvFullName = view.findViewById(R.id.tv_fullName);
//        btnSearch = view.findViewById(R.id.btn_follow);
        return view;
    }

    private void getData() {
        String data = etSearch.getText().toString().trim();
        if (!"".equals(data)) {
            ApiService.apiService.findFriend(data).enqueue(new Callback<List<FriendDTO>>() {
                @Override
                public void onResponse(Call<List<FriendDTO>> call, Response<List<FriendDTO>> response) {
                    List<FriendDTO> listDto = response.body();
                    listFr = FriendMapper._toListModel(listDto);
                    for(int i = 0; i < listFr.size(); i++){
                        if(listFr.get(i).getId() == LogedUser.logedUser.getId()){
                            listFr.remove(i);
                            i--;
                        }
                    }

                    FriendAdapter adapter = new FriendAdapter(getActivity(), listFr);
                    lvFriend.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<FriendDTO>> call, Throwable t) {

                }
            });
        }
    }
}