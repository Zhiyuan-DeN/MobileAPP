package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mobileapplication.R;
import com.example.mobileapplication.database.DatabaseModel;
import com.example.mobileapplication.database.User;
import com.example.mobileapplication.databinding.FragmentHomeBinding;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    int layout = R.layout.fragment_home;
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        User user = MainActivity.globalUser;
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initializeList(root);
        return root;

    }


    // To bind ListView and RecyclerView to the corresponding layout
    private void initializeList(View view) {
        // To bind ListView adapter to ListView
        PostAdapter adapter = new PostAdapter(getActivity(), R.layout.post_example, getPosts());
        listView = view.findViewById(R.id.post_list_view);
        listView.setAdapter(adapter);

        // 下面这里加入播放语音
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Post post = (Post) adapterView.getItemAtPosition(i);
                Toast.makeText(getContext(), post.getUserName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // TODO 下面这里get所有posts
    private ArrayList<Post> getPosts() {
        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(new Post(R.drawable.default_avatar, "Sample User 1",null));
        posts.add(new Post(R.drawable.default_avatar, "Sample User 2",null));
        posts.add(new Post(R.drawable.default_avatar, "Sample User 3",null));
        posts.add(new Post(R.drawable.default_avatar, "Sample User 4",null));
        posts.add(new Post(R.drawable.default_avatar, "Sample User 5",null));
        posts.add(new Post(R.drawable.default_avatar, "Sample User 6",null));
        posts.add(new Post(R.drawable.default_avatar, "Sample User 7",null));
        posts.add(new Post(R.drawable.default_avatar, "Sample User 8",null));
        return posts;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Tag", "HomeFragment.onDestroyView() has been called.");
        //binding = null;
    }
}