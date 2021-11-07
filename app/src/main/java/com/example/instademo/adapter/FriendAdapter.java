package com.example.instademo.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instademo.Fragment.ProfileFragment;
import com.example.instademo.R;
import com.example.instademo.activity.HomeActivity;
import com.example.instademo.api.ApiService;
import com.example.instademo.model.Friend;
import com.example.instademo.utils.LocalConst;
import com.example.instademo.utils.LogedUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendAdapter extends BaseAdapter {
    Activity activity;
    List<Friend> list;

    public FriendAdapter(Activity activity, List<Friend> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.user_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.imgAvt = convertView.findViewById(R.id.img_post_add);
            viewHolder.tvUsername = convertView.findViewById(R.id.tv_username);
            viewHolder.tvFullName = convertView.findViewById(R.id.tv_fullName);
            viewHolder.btnFollow = convertView.findViewById(R.id.btn_follow);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Friend friend = list.get(position);
        viewHolder.tvFullName.setText(friend.getFullName());
        viewHolder.tvUsername.setText(friend.getUsername());
        Picasso.with(activity)
                .load(LocalConst.URL + "/uploads/" + friend.getUrlAvt())
                .error(R.drawable.icon_defaul_account)
                .into(viewHolder.imgAvt);
        viewHolder.imgAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Friend f = list.get(position);
                if(f != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("username",f.getUsername());
                    bundle.putInt("type",1);
                    ProfileFragment profileFragment = new ProfileFragment();
                    profileFragment.setArguments(bundle);
                    HomeActivity activity1 = (HomeActivity) activity;
                    activity1.replaceFragment(profileFragment);
                }
            }
        });
        if (isFriend(friend.getId())) {
            viewHolder.btnFollow.setText("UNFOLLOW");
            viewHolder.btnFollow.setBackgroundColor(Color.GRAY);
        }

        viewHolder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = viewHolder.btnFollow.getText().toString().trim();
                if ("UNFOLLOW".equals(type)) {
                    ApiService.apiService.removeFriend(LogedUser.logedUser.getId(), friend.getId()).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            int num = response.body().intValue();
                            if (num > 0) {
                                viewHolder.btnFollow.setText("FOLLOW");
                                viewHolder.btnFollow.setEnabled(true);
                                viewHolder.btnFollow.setBackgroundColor(Color.parseColor("#FFA39A"));
                                removeFriend(friend.getId());
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                        }
                    });

                } else {
                    ApiService.apiService.addFriend(LogedUser.logedUser.getId(), friend.getId()).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            int num = response.body().intValue();
                            if (num > 0) {
                                viewHolder.btnFollow.setText("UNFOLLOW");
                                viewHolder.btnFollow.setBackgroundColor(Color.GRAY);
                                LogedUser.logedUser.getListFriend().add(friend);
                            }

                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                        }
                    });
                }

            }
        });
        return convertView;
    }

    private static class ViewHolder {
        ImageView imgAvt;
        TextView tvUsername, tvFullName;
        Button btnFollow;
    }

    private boolean isFriend(long friendId) {
        if (LogedUser.logedUser.getListFriend() == null) {
            return false;
        }
        for (Friend f : LogedUser.logedUser.getListFriend()
        ) {
            if (f.getId() == friendId) {
                return true;
            }
        }
        return false;
    }

    private void removeFriend(long friendId) {
        if (LogedUser.logedUser.getListFriend() == null) {
            return;
        }
        for(int i =0; i < LogedUser.logedUser.getListFriend().size(); i++){
            if(LogedUser.logedUser.getListFriend().get(i).getId() == friendId){
                LogedUser.logedUser.getListFriend().remove(i);
                i--;
            }
        }
    }
}
