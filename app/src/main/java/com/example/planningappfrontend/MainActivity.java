package com.example.planningappfrontend;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;
import java.util.Objects;

import static androidx.drawerlayout.widget.DrawerLayout.STATE_DRAGGING;
import static androidx.drawerlayout.widget.DrawerLayout.STATE_IDLE;
import static androidx.drawerlayout.widget.DrawerLayout.STATE_SETTLING;

public class MainActivity extends AppCompatActivity {

    private static final String LAYOUT_SP = "Layout Shared Preferences";
    private int currentOption = 0;
    public static final String CURRENT_OPTION = "Current option";

    public static final String GRID = "Grid";
    public static final String DRAWER = "Drawer";
    public static final String TABS = "Tabs";
    public static final String BOTTOM_NAVIGATION = "Bottom Navigation";
    public static final String VIEWPAGER = "View Pager";
    public static final String DRAG_CENTER = "Drag Center Spot";

    float dX;
    float rawX;
    float rawY;
    float maxLeftX = 200;
    float maxRightX = 890;
    float dY;
    float maxLowY = 735;
    float maxHigY = 1755;

    int lastAction;
    private boolean backPressed;
    private Toolbar mainMenuTb;
    private int current_position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainMenuTb = findViewById(R.id.mainMenuTb);
        setSupportActionBar(mainMenuTb);


        backPressed = false;


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addMenus(Menu menu) {
        menu.add(GRID).setOnMenuItemClickListener(item -> {
            setGrid();
            return false;
        });
        menu.add(DRAWER).setOnMenuItemClickListener(item -> {
            setDrawer();
            return false;
        });

        menu.add(TABS).setOnMenuItemClickListener(item -> {
            setTabs();
            return false;
        });

        menu.add(BOTTOM_NAVIGATION).setOnMenuItemClickListener(item -> {
            setBottomNavigation();
            return false;
        });

        menu.add(VIEWPAGER).setOnMenuItemClickListener(item -> {
            setViewPager();
            return false;
        });

        menu.add(DRAG_CENTER).setOnMenuItemClickListener(item -> {
            setDragCenterSpot();
            return false;
        });

    }

    private void setGrid() {
        setContentView(R.layout.grid_view);
        mainMenuTb = findViewById(R.id.mainMenuTb);
        setSupportActionBar(mainMenuTb);
        GridView gridView = findViewById(R.id.gridView);
        MainPageGrid mainPageGrid = new MainPageGrid();
        gridView.setAdapter(mainPageGrid);
        gridView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show());
    }

    private void setDrawer() {
        setContentView(R.layout.drawer_layout);
        mainMenuTb = findViewById(R.id.mainMenuTb);
        setSupportActionBar(mainMenuTb);
        DrawerLayout mainDrawer = findViewById(R.id.mainDrawer);
        NavigationView mainMenuNav = findViewById(R.id.mainMenuNav);
        View pagesHeader = mainMenuNav.getHeaderView(0);
        mainDrawer.addDrawerListener(setupDrawerToggle(mainDrawer));
        mainMenuNav.setNavigationItemSelectedListener(item -> {
            // chooseMenuItem(item);
            return false;
        });

        // Setup toggle to display hamburger icon with nice animation
        setupDrawerToggle(mainDrawer).setHomeAsUpIndicator(R.drawable.back_arrow);
        setupDrawerToggle(mainDrawer).setDrawerIndicatorEnabled(false);
        setupDrawerToggle(mainDrawer).syncState();

        mainDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                if (slideOffset <= 0) {
                    slideOffset = 1;
                    drawerView.setAlpha(slideOffset);
                } else if (slideOffset >= 255) {
                    slideOffset = 254;
                    drawerView.setAlpha(slideOffset);
                } else {
                    drawerView.setAlpha(slideOffset);
                }
                System.out.println("drawer" + slideOffset);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                // mainPageTb.setNavigationIcon(R.drawable.ic_bucket_pour);
                backPressed = true;
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                //   mainPageTb.setNavigationIcon(R.drawable.ic_bucket_up);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                switch (newState) {
                    default:
                        break;

                    case STATE_DRAGGING:
                        getWindow().setStatusBarColor(getResources().getColor(R.color.tomato));
                        //    mainPageTb.setNavigationIcon(R.drawable.ic_bucket_pour);
                        break;

                    case STATE_IDLE:
                        getWindow().setStatusBarColor(getResources().getColor(R.color.powderBlue));

                        break;

                    case STATE_SETTLING:
                        getWindow().setStatusBarColor(getResources().getColor(R.color.skyBlue));
                        if (!mainDrawer.isDrawerOpen(GravityCompat.START)) {
                            //         mainPageTb.setNavigationIcon(R.drawable.ic_bucket_up);
                        } else {
                            //  mainPageTb.setNavigationIcon(R.drawable.ic_bucket_pour);
                        }
                        break;
                }
            }
        });
        setupDrawerToggle(mainDrawer).setDrawerSlideAnimationEnabled(true);
    }

    private void setTabs() {
        setContentView(R.layout.tabs_layout);
        mainMenuTb = findViewById(R.id.mainMenuTb);
        setSupportActionBar(mainMenuTb);
        ViewPager tabViewPager = findViewById(R.id.tabsViewPager);
        TabLayout tabLayout = findViewById(R.id.tabsView);

        ViewPagerAdapter pageAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        tabViewPager.setAdapter(pageAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabViewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    default:
                    case 0:
                        //toolbar.setBackgroundColor(ContextCompat.getColor(Home.this, R.color.colorAccent));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiBlack));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.semiBlack));

                        }
                        tab.setText(" ");
                        break;
                    case 1:
                        // toolbar.setBackgroundColor(ContextCompat.getColor(Home.this, R.color.colorHover));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(android.R.color.darker_gray));
                        }
                        tab.setText(" ");
                        break;
                    case 2:
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiGray));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.semiGray));
                        }
                        tab.setText(" ");
                        break;
                    case 3:
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.semiWhite));
                        }
                        tab.setText(" ");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setText("");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //sync tabs and viewpager
        tabViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(tabViewPager, true);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_person).setText("Bottom 1");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.ic_person).setText("Bottom 2");
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.ic_person).setText("Bottom 3");
        Objects.requireNonNull(tabLayout.getTabAt(3)).setIcon(R.drawable.ic_person).setText("Bottom 4");
    }

    private void setBottomNavigation() {
        setContentView(R.layout.bottom_nav);
        mainMenuTb = findViewById(R.id.mainMenuTb);
        setSupportActionBar(mainMenuTb);
        ViewPager bottomNavTabs = findViewById(R.id.bottomNavViewPager);
        TabLayout bottomTab = findViewById(R.id.bottomNavTabs);

        ViewPagerAdapter pageAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        bottomNavTabs.setAdapter(pageAdapter);
        bottomTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                bottomNavTabs.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    default:
                    case 0:
                        //toolbar.setBackgroundColor(ContextCompat.getColor(Home.this, R.color.colorAccent));
                        bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiBlack));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.semiBlack));

                        }
                        tab.setText(" ");
                        break;
                    case 1:
                        // toolbar.setBackgroundColor(ContextCompat.getColor(Home.this, R.color.colorHover));
                        bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(android.R.color.darker_gray));
                        }
                        tab.setText(" ");
                        break;
                    case 2:
                        bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiGray));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.semiGray));
                        }
                        tab.setText(" ");
                        break;
                    case 3:
                        bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.semiWhite));
                        }
                        tab.setText(" ");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setText("");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //sync tabs and viewpager
        bottomNavTabs.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(bottomTab));
        bottomTab.setupWithViewPager(bottomNavTabs, true);

        Objects.requireNonNull(bottomTab.getTabAt(0)).setIcon(R.drawable.ic_person).setText("Bottom 1");
        Objects.requireNonNull(bottomTab.getTabAt(1)).setIcon(R.drawable.ic_person).setText("Bottom 2");
        Objects.requireNonNull(bottomTab.getTabAt(2)).setIcon(R.drawable.ic_person).setText("Bottom 3");
        Objects.requireNonNull(bottomTab.getTabAt(3)).setIcon(R.drawable.ic_person).setText("Bottom 4");
    }

    private ActionBarDrawerToggle setupDrawerToggle(DrawerLayout drawerLayout) {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawerLayout, mainMenuTb, R.string.drawer_open, R.string.drawer_close);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setViewPager() {
        setContentView(R.layout.view_pager_layout);
        mainMenuTb = findViewById(R.id.mainMenuTb);
        setSupportActionBar(mainMenuTb);
        ViewPager2 mainContainerPager = findViewById(R.id.viewPager2);
        mainContainerPager.setPadding(150, 50, 150, 50);
        mainContainerPager.setClipToPadding(false);
        mainContainerPager.setClipChildren(false);
        mainContainerPager.setOffscreenPageLimit(3);
        LinkedList<String> titles = new LinkedList<>();
        titles.add("1");
        titles.add("2");
        titles.add("3");
        titles.add("4");
        MenuPagerAdapter menuPagerAdapter = new MenuPagerAdapter(this, titles);
        mainContainerPager.setAdapter(menuPagerAdapter);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleX(0.85f + r * 0.15f);
        });

        mainContainerPager.setPageTransformer(compositePageTransformer);

        mainContainerPager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Toast.makeText(MainActivity.this, "Scrolled x from " + oldScrollX + "to " + scrollX + " and y from " + oldScrollY + " to " + scrollY, Toast.LENGTH_SHORT).show();
            }
        });

        ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                current_position = position;
               // new Handler(Looper.myLooper()).post(() -> changeIndicatorLevel());
                // new Handler().post(() -> doPalletMethod(drawableToBitmap(menuPagerAdapter.getCurrentImage(menuPagerAdapter.getViewHolder())), mainPageLayout));
                //mainToolbar.setSubtitle(titles.get(current_position));
                menuPagerAdapter.setCurrent_position(position);
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        };

        mainContainerPager.registerOnPageChangeCallback(onPageChangeCallback);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setDragCenterSpot() {



        setContentView(R.layout.drag_center_spot);
        mainMenuTb = findViewById(R.id.mainMenuTb);
        setSupportActionBar(mainMenuTb);
        ImageButton centerB = findViewById(R.id.imageButton);
        centerB.setOnTouchListener((view, event) -> {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();
                    lastAction = MotionEvent.ACTION_DOWN;
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (rawX <= maxRightX || rawY >= maxLeftX) {
                        view.setY(event.getRawY() + dY);
                        System.out.println("x : "+event.getRawX());
                    }

                    if (rawY <= maxHigY || rawY >= maxLowY) {
                        view.setX(event.getRawX() + dX);
                        System.out.println("y : "+event.getRawY());
                    }

                    lastAction = MotionEvent.ACTION_MOVE;
                    break;

                case MotionEvent.ACTION_UP:
                    if (lastAction == MotionEvent.ACTION_DOWN)
                        Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    return false;
            }
            return true;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        addMenus(menu);
        return super.onCreateOptionsMenu(menu);
    }


    private boolean setUserSp(int currentOption) {
        SharedPreferences sharedPreferences = getSharedPreferences(LAYOUT_SP, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt(CURRENT_OPTION, currentOption);
        myEdit.apply();
        return myEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backPressed = false;
    }

    private void backPress(DrawerLayout drawerLayout) {
        backPressed = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

        if (!backPressed) {
            backPressed = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }

    }
}