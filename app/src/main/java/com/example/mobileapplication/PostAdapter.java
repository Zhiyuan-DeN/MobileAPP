package com.example.mobileapplication;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapplication.databinding.PostExampleBinding;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> localDataSet;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final ImageView userProfileImage;
        private final ImageButton play;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            userProfileImage = view.findViewById(R.id.post_example_image);
            userName = view.findViewById(R.id.post_example_text);
            play = view.findViewById(R.id.play);
        }

        public TextView getUserNameView() {
            return userName;
        }

        public ImageView getUserProfileImageView() {
            return userProfileImage;
        }

        public ImageButton getPlayView() {
            return play;
        }
    }

    public PostAdapter(List<Post> objects) {
        localDataSet = objects;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_example, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getUserNameView().setText(localDataSet.get(position).getUserName());
        viewHolder.getUserProfileImageView().setImageResource(localDataSet.get(position).getProfileImage());
        viewHolder.getPlayView().setFocusable(false);
        viewHolder.getPlayView().setFocusableInTouchMode(false);
        //viewHolder.getPlayView().setText(localDataSet.get(position).getAudioTrack());
    }

    @Override
    // to get the size of the fruits array
    public int getItemCount() {
        return localDataSet.size();
    }


    /**
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.post_example, parent, false);
        }
        Post currentPostPosition = getItem(position);
        ImageView numbersImage = currentItemView.findViewById(R.id.post_example_image);
        assert currentPostPosition != null;
        numbersImage.setImageResource(currentPostPosition.getProfileImage());

        TextView userName = currentItemView.findViewById(R.id.post_example_text);
        userName.setText(currentPostPosition.getUserName());

        ImageButton play = currentItemView.findViewById(R.id.play);
        play.setFocusable(false);
        play.setFocusableInTouchMode(false);

        // TODO 下面加入播放post内容
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), userName.getText() + " start playing", Toast.LENGTH_SHORT).show();
            }
        });

        return currentItemView;
    }
    */
}