package com.example.viewpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomImageActivity extends AppCompatActivity {
    PostAdapter postAdapter;
    public static int imagechon=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customimage);
        Checkinternet.startTimer(this);
        RecyclerView postRecyc = findViewById(R.id.postRecyclerView);
        postRecyc.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );
        final List<PostItem> postItems = new ArrayList<>();
        postItems.add(new PostItem(R.drawable.hinh1));
        postItems.add(new PostItem(R.drawable.hinh11));
        postItems.add(new PostItem(R.drawable.hinh3));
        postItems.add(new PostItem(R.drawable.hinh2));
        postItems.add(new PostItem(R.drawable.bitaxco));
        postItems.add(new PostItem(R.drawable.hinh5));
        postItems.add(new PostItem(R.drawable.hinh13));
        postItems.add(new PostItem(R.drawable.hinh6));
        postItems.add(new PostItem(R.drawable.hinh7));
        postItems.add(new PostItem(R.drawable.hinh8));
        postItems.add(new PostItem(R.drawable.hinh9));
        postItems.add(new PostItem(R.drawable.hinh10));

        postAdapter = new PostAdapter(postItems);
        postRecyc.setAdapter(postAdapter);
        postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {

                imagechon =  postItems.get(position).getImage();
                //Toast.makeText(CustomImageActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(CustomImageActivity.this, MainActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CustomImageActivity.this,
                            Pair.create(view, "imageTransition"));
                    startActivity(intent, options.toBundle());
                }

            }
        });

    }
}