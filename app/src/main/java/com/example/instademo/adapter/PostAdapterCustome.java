package com.example.instademo.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instademo.MainActivity;
import com.example.instademo.R;
import com.example.instademo.activity.CommentActivity;
import com.example.instademo.api.ApiService;
import com.example.instademo.model.Announce;
import com.example.instademo.model.Post;
import com.example.instademo.utils.LocalConst;
import com.example.instademo.utils.LogedUser;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapterCustome extends BaseAdapter {
    private Activity activity;
    private List<Post> listPost;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

    public PostAdapterCustome(Activity activity, List<Post> listPost) {
        this.activity = activity;
        this.listPost = listPost;
    }

    @Override
    public int getCount() {
        if (listPost != null) {
            return listPost.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return listPost.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.post_item, null);
            viewHolder = new ViewHolder();

            viewHolder.imgProfile = convertView.findViewById(R.id.img_profile);
            viewHolder.imgPostImage = convertView.findViewById(R.id.img_post1);
            viewHolder.imgLike = convertView.findViewById(R.id.img_like);
            viewHolder.imgLike.setTag(R.id.img_like);
            viewHolder.tvTotalLike = convertView.findViewById(R.id.txtTotalLike);
            viewHolder.imgComment = convertView.findViewById(R.id.img_comment);
            viewHolder.imgSave = convertView.findViewById(R.id.img_save);
            viewHolder.tvDate = convertView.findViewById(R.id.txtDate);
            viewHolder.tvUsername = convertView.findViewById(R.id.tvUserName);
            viewHolder.tvDes = convertView.findViewById(R.id.txtDes);
            viewHolder.imgMore = convertView.findViewById(R.id.more);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Post post = listPost.get(position);

        viewHolder.imgProfile.setImageResource(R.drawable.naq);

        Picasso.with(activity).load(LocalConst.URL + "/uploads/" + post.getUrlAvt())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.img_default)
                .into(viewHolder.imgProfile);

        viewHolder.imgPostImage.setImageResource(R.drawable.naq);
        Picasso.with(activity).load(LocalConst.URL + "/uploads/" + post.getListImage().get(0).getName())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.img_default)
                .into(viewHolder.imgPostImage);


        viewHolder.tvTotalLike.setText(post.getTotalLike() + "");

        viewHolder.tvDate.setText(sdf.format(post.getCreatedDate()));
        viewHolder.tvUsername.setText(post.getUserName());
        viewHolder.tvDes.setText(post.getContent());
        viewHolder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LogedUser.logedUser.getId() == post.getUser_id()) {
                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(activity, viewHolder.imgMore);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.post_option_menu);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.menu_delete:
                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    ApiService.apiService.deletePost(post.getId()).enqueue(new Callback<Optional>() {
                                                        @Override
                                                        public void onResponse(Call<Optional> call, Response<Optional> response) {
                                                            delete(post);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Optional> call, Throwable t) {
                                                            delete(post);
                                                        }
                                                    });
                                                    break;

                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    //No button clicked
                                                    break;
                                            }
                                        }
                                    };
                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                                            .setNegativeButton("No", dialogClickListener).show();

                                    break;
                                case R.id.menu_edit:
                                    //handle menu2 click
                                    break;
                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();
                }
            }
        });
        viewHolder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _clickImgLike(post.getId(), viewHolder.imgLike, viewHolder.tvTotalLike);
                Announce announce = new Announce();
                announce.setTitle(LogedUser.logedUser.getUsername() + " da thich anh cua ban");
                announce.setUser_id(post.getUser_id());
                announce.setFriend_id(LogedUser.logedUser.getId());
                announce.setPost_id(post.getId());

                ApiService.apiService.sendAnnouce(announce).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
            }
        });
        viewHolder.imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, CommentActivity.class);
                intent.putExtra("id", post.getId());
                activity.startActivity(intent);
            }
        });

        return convertView;
    }


    private static class ViewHolder {
        ImageView imgProfile, imgPostImage, imgLike, imgComment, imgSave, imgMore;
        TextView tvTotalLike, tvDate, tvUsername, tvDes;
    }

    private void _clickImgLike(long postId, ImageView imgLike, TextView tvLike) {
        int id_imageLike = ((int) imgLike.getTag());
        int type = 0;
        if (id_imageLike == R.id.img_like) {
            type = 1;
            imgLike.setImageResource(R.drawable.ic_liked);
            imgLike.setTag(R.drawable.ic_liked);
        } else {
            type = 0;
            imgLike.setImageResource(R.drawable.ic_like);
            imgLike.setTag(R.drawable.ic_like);
        }
        ApiService.apiService.likePost(postId, type).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                Long totolLike = response.body();
                tvLike.setText(totolLike + "");
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });
    }

    private void deletePost(long id) {
        for (int i = 0; i < listPost.size(); i++) {
            if (listPost.get(i).getId() == id) {
                listPost.remove(i);
                i--;
            }
        }
        for (int i = 0; i < LogedUser.logedUser.getListPos().size(); i++) {
            if (LogedUser.logedUser.getListPos().get(i).getId() == id) {
                LogedUser.logedUser.getListPos().remove(i);
                i--;
            }
        }
    }

    private void delete(Post post) {
        deletePost(post.getId());
        notifyDataSetChanged();
        Toast.makeText(activity, "Delete sucsessfully !", Toast.LENGTH_LONG).show();
    }
}
