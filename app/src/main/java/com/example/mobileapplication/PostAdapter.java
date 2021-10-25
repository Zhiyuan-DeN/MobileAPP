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

import com.example.mobileapplication.databinding.PostExampleBinding;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAdapter extends ArrayAdapter<Post> {
    private int resourceId;

    public PostAdapter(Context context, int resource, List<Post> objects) {
        super(context, resource, objects);
        resourceId = resource;

    }

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
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), userName.getText() + " start playing", Toast.LENGTH_SHORT).show();
            }
        });

        return currentItemView;
    }
}