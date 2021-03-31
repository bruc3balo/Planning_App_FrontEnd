package com.example.planningappfrontend.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planningappfrontend.R;

import static com.example.planningappfrontend.MainActivity.goToActivityFromPosition;

public class MenuPagerAdapter extends RecyclerView.Adapter<MenuPagerAdapter.SliderViewHolder> {

    private Context context;
    private final String[] titles;
    private SliderViewHolder vHolder;
    private ItemClickListener mClickListener;
    private int current_position;


    public MenuPagerAdapter(Context context, String[] titles) {
        this.titles = titles;
        this.context = context;
    }


    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutItem;
        layoutItem = R.layout.cover_view_pager;
        return new SliderViewHolder(LayoutInflater.from(context).inflate(layoutItem, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {


        this.vHolder = holder;
        holder.coverTitle.setText(titles[position]);

        //Glide.with(context).load(pageIcons[position]).into(holder.coverImage);
       // Glide.with(context).load(R.drawable.black_dot).into(holder.coverImage);

        holder.coverImage.setOnClickListener(v -> goToActivityFromPosition(position,context));

        holder.coverImage.setOnLongClickListener(v -> {
           // showShortcutGrid(position, menu_items, menu_titles);
            return false;
        });

        holder.viewHolderBackground.setOnLongClickListener(v -> {
           // showShortcutGrid(position, menu_items, menu_titles);
            return false;
        });




    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView coverImage;
        private final TextView coverTitle;
        private final LinearLayout viewHolderBackground;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImage = itemView.findViewById(R.id.menuCover);
            coverTitle = itemView.findViewById(R.id.coverTitle);
            viewHolderBackground = itemView.findViewById(R.id.viewHolderBackground);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public Drawable getCurrentImage(SliderViewHolder holder) {
        return holder.coverImage.getDrawable();
    }

    public SliderViewHolder getViewHolder() {
        return vHolder;
    }



    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public int getCurrent_position() {
        return current_position;
    }

    public void setCurrent_position(int current_position) {
        this.current_position = current_position;
    }

}
