package com.example.demo_musicstreamfirebase.adapters;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.demo_musicstreamfirebase.activities.SongListActivity;
import com.example.demo_musicstreamfirebase.databinding.CategoryItemRecyclerRowBinding;
import com.example.demo_musicstreamfirebase.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private final List<CategoryModel> categoryList;

    // Constructor để khởi tạo danh sách các category
    public CategoryAdapter(List<CategoryModel> categoryList) {
        this.categoryList = categoryList;
    }

    // ViewHolder để giữ các view trong từng row
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final CategoryItemRecyclerRowBinding binding;

        // Constructor để khởi tạo binding
        public MyViewHolder(CategoryItemRecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // Ràng buộc dữ liệu với các view
        public void bindData(CategoryModel category) {
            binding.nameTextView.setText(category.getName());
            Glide.with(binding.coverImageView.getContext())
                    .load(category.getCoverUrl())
                    .apply(new RequestOptions().transform(new RoundedCorners(32)))
                    .into(binding.coverImageView);

            // Bắt đầu SongsList Activity
            View.OnClickListener listener = v -> {
                SongListActivity.setCategory(category);
                Intent intent = new Intent(binding.getRoot().getContext(), SongListActivity.class);
                binding.getRoot().getContext().startActivity(intent);
            };
            binding.getRoot().setOnClickListener(listener);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CategoryItemRecyclerRowBinding binding = CategoryItemRecyclerRowBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindData(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
