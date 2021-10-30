package com.example.mobileapplication;

import android.content.Intent;
import android.icu.text.Edits;
import android.media.AudioTrack;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mobileapplication.R;
import com.example.mobileapplication.database.DatabaseModel;
import com.example.mobileapplication.database.User;
import com.example.mobileapplication.databinding.FragmentHomeBinding;
import com.example.mobileapplication.oss.CloudStorageManager;
import com.example.mobileapplication.utils.PathUtils;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    int layout = R.layout.fragment_home;
    RecyclerView recyclerView;
    ArrayList<Post> posts;
    PostAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Tag", "HomeFragment.onCreateView() has been called.");
        User user = MainActivity.globalUser;
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        posts = new ArrayList<Post>();
        initializeList(root);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            Log.d("Tag", "Reached here.");
            adapter.addItem((Post)bundle.getSerializable("value"),0);
        }
        return root;
    }

    // To bind ListView and RecyclerView to the corresponding layout
    private void initializeList(View view) {
        // To bind ListView adapter to ListView
        adapter = new PostAdapter(getPosts());
        recyclerView = view.findViewById(R.id.post_recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    private ArrayList<Post> getPosts() {
        Map<File, String> postMap = LoginActivity.postMap;
        try {
            for (File file: postMap.keySet()) {
                posts.add(new Post(R.drawable.default_avatar, postMap.get(file), file));
            }
        } catch (Exception e) {

        }

        return posts;
    }

    public void addPost(int profileImage, String userName, File audioTrack) {
        Post new_post = new Post(profileImage,userName,audioTrack);
        adapter.addItem(new_post,0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Tag", "HomeFragment.onDestroyView() has been called.");
        //binding = null;
    }
}