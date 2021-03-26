package com.example.planningappfrontend;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;


public class MenuPagerAdapter extends RecyclerView.Adapter<MenuPagerAdapter.SliderViewHolder> {

    private Context context;
    private LinkedList<String> titles;
    private static final int FRAGMENT_PROFILE = 0;
    private static final int FRAGMENT_MTAA = 1;
    private static final int FRAGMENT_EVENTS = 2;
    private static final int FRAGMENT_COMMUNITY = 3;
    private static final int FRAGMENT_MESSAGES = 4;
    private static final int FRAGMENT_CLAN = 5;
    private static final int FRAGMENT_GAZETTE = 6;
    private static final int FRAGMENT_HOT = 7;
    private static final int FRAGMENT_MAP = 8;
    private static final int FRAGMENT_FAQ = 9;
    private static final int FRAGMENT_SEARCH = 10;
    private static final int FRAGMENT_TRADE = 11;
    private static final int FRAGMENT_WALLET = 12;
    private SliderViewHolder vHolder;
    private int shortcutDestination;
    private ItemClickListener mClickListener;
    private int current_position;


    public MenuPagerAdapter(Context context, LinkedList<String> titles) {
        this.titles = titles;
        this.context = context;
    }


    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutItem = 0;

        switch (viewType) {
            case 0:
            default:
                layoutItem = R.layout.cover_view_pager;
                break;
            case 1:
                layoutItem = R.layout.cover_view_pager;
                break;

            case 2:
                layoutItem = R.layout.cover_view_pager;
                break;

            case 3:
                layoutItem = R.layout.cover_view_pager;
                break;

        }

        return new SliderViewHolder(LayoutInflater.from(context).inflate(layoutItem, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        int[] menu_items = new int[]{R.drawable.black_dot, R.drawable.black_dot, android.R.drawable.ic_menu_my_calendar, R.drawable.black_dot};
        String[] menu_titles = new String[]{"1", "2", "3", "4",};
        Class[] menuDestinations = new Class[]{DummyActivity.class, DummyActivity.class, DummyActivity.class, DummyActivity.class};

        this.vHolder = holder;
        holder.coverTitle.setText(menu_titles[position]);

        holder.coverImage.setOnClickListener(v -> {
            switch (getCurrent_position()) {

                case 0:
                case 1:
                case 2:

                case 3:

                    Intent openPage = new Intent(context, menuDestinations[getCurrent_position()]);
                    context.startActivity(openPage);
                    //Animatoo.animateZoom(context);
                    break;


                default:
                    break;
            }
        });

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
        return titles.size();
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
        int viewType = FRAGMENT_PROFILE;
        switch (position) {
            case 0:
            default:
                viewType = FRAGMENT_PROFILE;
                break;

            case 1:
                viewType = FRAGMENT_MTAA;
                break;

            case 2:
                viewType = FRAGMENT_EVENTS;

                break;

            case 3:
                viewType = FRAGMENT_COMMUNITY;
                break;


        }

        return viewType;
    }

    /* public LinkedList<String> getMenuTitles() {
        titles.add("PROFILE");
        titles.add("MTAA");
        titles.add("EVENTS");
        titles.add("COMMUNITY");
        titles.add("MESSAGES");
        titles.add("CLAN");
        titles.add("GAZETTE");
        titles.add("HOT");
        titles.add("MAP");
        titles.add("FAQ");
        titles.add("SEARCH");
        titles.add("WALLET");
        return titles;
    }*/

    public Drawable getCurrentImage(SliderViewHolder holder) {
        return holder.coverImage.getDrawable();
    }

    public SliderViewHolder getViewHolder() {
        return vHolder;
    }

    public void setShortcutDestination(int position) {
        shortcutDestination = position;
    }

    public int getShortcutDestination() {
        return shortcutDestination;
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

/*    private void showShortcutGrid(int position, int[] menu_items, String[] menu_titles) {
        Dialog shortcutDialog = new Dialog(context);
        shortcutDialog.setContentView(R.layout.shortcut_dialog_layout);
        ImageButton closeShortcut = shortcutDialog.findViewById(R.id.closeShortcut);
        GridView shortcutGrid = shortcutDialog.findViewById(R.id.shortcutGrid);
        shortcutDialog.show();

        closeShortcut.setOnClickListener(v1 -> shortcutDialog.cancel());

        ShortcutAdapter shortcutAdapter = new ShortcutAdapter(context, menu_items, menu_titles);
        shortcutGrid.setAdapter(shortcutAdapter);
        shortcutAdapter.setCurrentPosition(position);

        shortcutGrid.setOnItemClickListener((parent, view, position1, id) -> {
            setShortcutDestination(position1);
            shortcutDialog.cancel();
            MainActivity.changeMenuPosition(position1);
        });

    }*/
}
