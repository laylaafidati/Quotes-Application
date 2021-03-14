package com.example.mcs_finalexam_2101701563_laylanurulafidati;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAuthorList extends RecyclerView.Adapter<AdapterAuthorList.ViewHolder> {
    ArrayList<Author> arrayAuthor;
    Context ctx;

    public AdapterAuthorList(ArrayList<Author> arrayAuthor, Context ctx) {
        this.arrayAuthor = arrayAuthor;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.rv_authorlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Author author = arrayAuthor.get(position);
        holder.tvName.setText(author.getName());
        holder.tvTotalQuotes.setText(author.getTotalQuotes());
        holder.tvBiography.setText(author.getBiography());
    }

    @Override
    public int getItemCount() {
        return arrayAuthor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTotalQuotes, tvBiography;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTotalQuotes = itemView.findViewById(R.id.tv_totalQuotes);
            tvBiography = itemView.findViewById(R.id.tv_biography);
        }
    }
}
