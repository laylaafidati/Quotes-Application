package com.example.mcs_finalexam_2101701563_laylanurulafidati;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterFavorites extends RecyclerView.Adapter<AdapterFavorites.ViewHolder> {
    ArrayList<Favorite> arrayFavorites;
    ArrayList<Integer> array_id;
    Context ctx;
    DatabaseHelper dbHelper;

    public AdapterFavorites(ArrayList<Favorite> arrayFavorites, ArrayList<Integer> array_id, Context ctx) {
        this.arrayFavorites = arrayFavorites;
        this.array_id = array_id;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AdapterFavorites.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.rv_favorites, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterFavorites.ViewHolder holder, final int position) {
        Favorite favorite = arrayFavorites.get(position);
        int id = array_id.get(position);
        holder.tvTags.setText(favorite.tags);
        holder.tvAuthor.setText(favorite.author);
        holder.tvContent.setText(favorite.content);
        holder.itemView.setTag(id);
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(v.getContext());
                deleteDialog.setTitle("Unfavorite Quotes")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int id = (int) holder.itemView.getTag();
                            dbHelper = new DatabaseHelper(ctx);
                            dbHelper.dbDelete(id);
                            Toast.makeText(v.getContext(), "Quotes deleted", Toast.LENGTH_LONG).show();
                            arrayFavorites.remove(position);
                            notifyItemRemoved(position);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            deleteDialog.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayFavorites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTags, tvAuthor, tvContent;
        Button btn_delete;
        Dialog deleteDialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTags = itemView.findViewById(R.id.tv_tagsFav);
            tvAuthor = itemView.findViewById(R.id.tv_authorFav);
            tvContent = itemView.findViewById(R.id.tv_contentFav);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            deleteDialog = new Dialog(itemView.getContext());
        }
    }
}
