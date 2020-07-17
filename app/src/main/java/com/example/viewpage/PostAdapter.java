package com.example.viewpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {


    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_custom_image, parent, false
        ),mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.setPostImage(postItems.get(position));
    }

    @Override
    public int getItemCount() {
        return postItems.size();
    }

    private List<PostItem> postItems;

    public PostAdapter(List<PostItem> postItems) {
        this.postItems = postItems;
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView postImage;

        public PostViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            postImage = itemView.findViewById(R.id.imagePost);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v,position);
                        }
                    }
                }
            });
        }

        void setPostImage(PostItem postItem) {

            //image internet picasso..
            postImage.setImageResource(postItem.getImage());
        }

    }

}
