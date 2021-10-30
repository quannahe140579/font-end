package com.example.instademo.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.instademo.MainActivity;
import com.example.instademo.R;
import com.example.instademo.activity.EditProfileActivity;
import com.example.instademo.activity.LoginActivity;
import com.example.instademo.adapter.ImageAdapter;
import com.example.instademo.model.Post;
import com.example.instademo.utils.LocalConst;
import com.example.instademo.utils.LogedUser;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private Button btnEditProfile;
    private TextView tvUsername, tvFullName, tvNumberFollower, tvNumberFollowing, tvNumberPost, tvIntro;
    private CircleImageView circleImageView;
    private GridView gvImage;
    private ImageView imgOptions;
    boolean isImageFitToScreen;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private String mParam1;
    private String mParam2;
    private Intent intent;
    private Activity activity;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = activity.getMenuInflater();
        if(v.getId() == R.id.options){
            menuInflater.inflate(R.menu.profile_context_menu,menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            activity.finishAffinity();
            Intent intent = new Intent(activity, MainActivity.class);
            LogedUser.logedUser = null;
            LogedUser.listPost = null;
            activity.startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnEditProfile = view.findViewById(R.id.btnAddPost);
        tvUsername = view.findViewById(R.id.tv_username_profile);
        tvFullName = view.findViewById(R.id.tv_fullName_profile);
        circleImageView = view.findViewById(R.id.img_avt_profile);
        tvNumberFollower = view.findViewById(R.id.number_follower);
        tvNumberFollowing = view.findViewById(R.id.number_following);
        tvNumberPost = view.findViewById(R.id.number_post);
        tvIntro = view.findViewById(R.id.txtIntro);
        imgOptions = view.findViewById(R.id.options);
        gvImage = view.findViewById(R.id.gr_listFriend);

        List<Post> listPost = LogedUser.logedUser.getListPos();
        if(listPost != null){
            List<String> listImg = new ArrayList<>();
            for (Post p: listPost
                 ) {
                listImg.add(p.getListImage().get(0).getName());
            }
            ImageAdapter adapter = new ImageAdapter(getActivity(),listImg);
            gvImage.setAdapter(adapter);
        }
        setOnClickForBtn();
        fillDate();
        registerForContextMenu(imgOptions);
    }

    private void fillDate() {
        tvUsername.setText(LogedUser.logedUser.getUsername());
        tvFullName.setText(LogedUser.logedUser.getFullName());
        Picasso.with(getActivity())
                .load(LocalConst.URL + "/uploads/" + LogedUser.logedUser.getAvatar())
                .error(R.drawable.icon_defaul_account)
                .into(circleImageView);
        if(LogedUser.logedUser.getListFriend() != null){
            tvNumberFollowing.setText(LogedUser.logedUser.getListFriend().size() + "");
        }
        if(LogedUser.logedUser.getListPos() != null){
            tvNumberPost.setText(LogedUser.logedUser.getListPos().size() + "");
        }
        tvNumberFollower.setText("0");
        if(LogedUser.logedUser.getDateOfBirth() != null){
            tvIntro.setText(sdf.format(LogedUser.logedUser.getDateOfBirth()));
        }else{
            tvIntro.setText("Please update your information");
        }

    }

    public void setOnClickForBtn(){
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
         intent = new Intent(getActivity(), EditProfileActivity.class);
         activity = getActivity();
    }

}