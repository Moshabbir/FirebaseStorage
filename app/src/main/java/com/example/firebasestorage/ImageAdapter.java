package com.example.firebasestorage;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context context;
    private List<Upload> uploadList;
    public OnItemClickListener listener;

    public ImageAdapter(Context context, List<Upload> uploadList) {
        this.context = context;
        this.uploadList = uploadList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.itemlayout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Upload upload = uploadList.get(i);
        viewHolder.textView.setText(upload.getImageName());
        Picasso.with(context)
                .load(upload.getImageUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .fit()
                .centerCrop()
                .into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView textView;
        private ImageView imageView;

        public  ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id._title);
            imageView = itemView.findViewById(R.id._image);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {

            if (listener!=null)
            {
                int position = getAdapterPosition();

                if(position!=RecyclerView.NO_POSITION){
                    listener.onItemClick(position);
                }
            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
              menu.setHeaderTitle("Choose an action");
            MenuItem doAnyTask = menu.add(Menu.NONE,1,1,"Do any task");
            MenuItem delete = menu.add(Menu.NONE,2,2,"Delete");

            doAnyTask.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (listener!=null)
            {
                int position = getAdapterPosition();

                if(position!=RecyclerView.NO_POSITION){

                    switch (item.getItemId()){
                        case 1:
                           listener.onDoAnyTask(position);
                           return true;
                        case 2:
                            listener.onDelete(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDoAnyTask(int position);
        void onDelete(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener=listener;
    }


}






























