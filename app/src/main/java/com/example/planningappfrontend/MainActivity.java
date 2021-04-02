package com.example.planningappfrontend;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.planningappfrontend.adapter.BottomPagerAdapter;
import com.example.planningappfrontend.adapter.MainPageGrid;
import com.example.planningappfrontend.adapter.MenuPagerAdapter;
import com.example.planningappfrontend.pagesActivities.ActivityFragmentHolder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import static androidx.drawerlayout.widget.DrawerLayout.STATE_DRAGGING;
import static androidx.drawerlayout.widget.DrawerLayout.STATE_IDLE;
import static androidx.drawerlayout.widget.DrawerLayout.STATE_SETTLING;
import static com.example.planningappfrontend.pagesActivities.ActivityFragmentHolder.CHOSEN_FRAGMENT;
import static com.example.planningappfrontend.pagesActivities.ActivityFragmentHolder.getFragmentFromPosition;
import static com.example.planningappfrontend.pagesFragments.SettingsFragment.LAYOUT_SETTING_KEY;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LAYOUT_SP = "Layout Shared Preferences";
    private String currentOption = "";
    public static final String CURRENT_OPTION = "Current option";

    public static final String GRID = "Grid";
    public static final String DRAWER = "Drawer";
    public static final String TABS = "Tabs";
    public static final String BOTTOM_NAVIGATION = "Bottom Navigation";
    public static final String VIEWPAGER = "View Pager";
    public static final String DRAG_CENTER = "Drag Center Spot";

    public static final String JOURNAL = "Journals";
    public static final String GOAL = "Goals";
    public static final String NOTES = "Notes";
    public static final String REMINDER = "Reminder";
    public static final String TIMETABLE = "TimeTable";
    public static final String TODO = "Todo";
    public static final String SUMMARY = "Summary";
    public static final String SETTINGS = "Settings";

    public static final String[] pageTitles = new String[]{JOURNAL, GOAL, NOTES, REMINDER, TIMETABLE, TODO, SUMMARY, SETTINGS};
    public static final int[] pageIcons = new int[]{R.drawable.ic_book, R.drawable.ic_goal, R.drawable.ic_note, R.drawable.ic_clock_, R.drawable.ic_calendar, R.drawable.ic_checklist, R.drawable.ic_report_, R.drawable.ic_setting};

    int lastAction;
    private boolean backPressed;
    private Toolbar mainMenuTb;
    private int current_position;

    private DrawerLayout mainDrawer;

    private SharedPreferences defaultSharedPreference;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainMenuTb = findViewById(R.id.mainMenuTb);
        setSupportActionBar(mainMenuTb);

        backPressed = false;

        LinearLayout linearLayout = findViewById(R.id.mainPageBg);
        setAnimatedBg(linearLayout);

        defaultSharedPreference = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        currentOption = defaultSharedPreference.getString(LAYOUT_SETTING_KEY, BOTTOM_NAVIGATION);
        chooseLayout(currentOption);
        defaultSharedPreference.registerOnSharedPreferenceChangeListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void chooseLayout(String layoutChosen) {
        switch (layoutChosen) {
            default:
            case BOTTOM_NAVIGATION:
                setBottomNavigation();
                break;

            case GRID:
                setGrid();
                break;

            case DRAG_CENTER:
                setBottomNavigation();
                break;

            case VIEWPAGER:
                setViewPager();
                break;

            case TABS:
                setTabs();
                break;

            case DRAWER:
                setDrawer();
                break;
        }
    }

    private void setAnimatedBg(ViewGroup viewGroup) {
        AnimationDrawable animationDrawable = (AnimationDrawable) viewGroup.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
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
        gridView.setOnItemClickListener((parent, view, position, id) -> goToActivityFromPosition(position, MainActivity.this));

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        setAnimatedBg(gridLayout);
    }

    public static void goToActivityFromPosition(int position, Context context) {
        context.startActivity(new Intent(context, ActivityFragmentHolder.class).putExtra(CHOSEN_FRAGMENT, position));
    }

    public static void restartApp(Context context) {
        Intent mStartActivity = new Intent(context, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }

    public static void restartActivity(Activity activity, Intent intent) {
        activity.finish();
        activity.startActivity(intent);
    }

    private void setDrawer() {
        setContentView(R.layout.drawer_layout);
        mainMenuTb = findViewById(R.id.mainMenuTb);
        setSupportActionBar(mainMenuTb);
        mainDrawer = findViewById(R.id.mainDrawer);
        NavigationView mainMenuNav = findViewById(R.id.mainMenuNav);
        View pagesHeader = mainMenuNav.getHeaderView(0);
        mainDrawer.addDrawerListener(setupDrawerToggle(mainDrawer));
        mainMenuNav.setNavigationItemSelectedListener(item -> {
            chooseMenuItem(item);
            return false;
        });

        addPagesMenu(mainMenuNav.getMenu());

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

        setAnimatedBg(mainDrawer);
    }

    private void chooseMenuItem(MenuItem item) {
        switch (item.getTitle().toString()) {
            default:
            case JOURNAL:
                goToActivityFromPosition(0, MainActivity.this);
                break;

            case GOAL:
                goToActivityFromPosition(1, MainActivity.this);
                break;

            case NOTES:
                goToActivityFromPosition(2, MainActivity.this);
                break;

            case REMINDER:
                goToActivityFromPosition(3, MainActivity.this);
                break;

            case TIMETABLE:
                goToActivityFromPosition(4, MainActivity.this);
                break;

            case TODO:
                goToActivityFromPosition(5, MainActivity.this);
                break;

            case SUMMARY:
                goToActivityFromPosition(6, MainActivity.this);
                break;

            case SETTINGS:
                goToActivityFromPosition(7, MainActivity.this);
                break;

        }
    }

    private void setTabs() {
        setContentView(R.layout.tabs_layout);
        mainMenuTb = findViewById(R.id.mainMenuTb);
        setSupportActionBar(mainMenuTb);
        ViewPager tabViewPager = findViewById(R.id.tabsViewPager);
        TabLayout tabLayout = findViewById(R.id.tabsView);

        BottomPagerAdapter pageAdapter = new BottomPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabViewPager.setAdapter(pageAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(pageIcons[tab.getPosition()]);

                switch (tab.getPosition()) {
                    default:
                    case 0:
                        //toolbar.setBackgroundColor(ContextCompat.getColor(Home.this, R.color.colorAccent));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiBlack));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.semiBlack));

                        }

                        //fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
                        break;
                    case 1:
                        // toolbar.setBackgroundColor(ContextCompat.getColor(Home.this, R.color.colorHover));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(android.R.color.darker_gray));
                        }

                        // fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
                        break;
                    case 2:
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiGray));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.semiGray));
                        }

                        //  fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
                        break;
                    case 3:
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.semiWhite));
                        }

                        //  fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
                        break;

                    case 4:
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.green));
                        }

                        //  fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
                        break;

                    case 5:
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.yellow));
                        }

                        // fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
                        break;

                    case 6:
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.blue));
                        }

                        //  fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
                        break;

                    case 7:
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().setStatusBarColor(getColor(R.color.black));
                        }


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

        pageAdapter.setAllTabIcons(pageIcons, tabLayout);

        LinearLayout tabsLayout = findViewById(R.id.tabsLayout);

        setAnimatedBg(tabsLayout);
    }

    private void addPagesMenu(Menu menu) {
        for (int i = 0; i <= pageTitles.length - 1; i++) {
            menu.add(pageTitles[i]);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setBottomNavigation() {
        setContentView(R.layout.bottom_nav);
        mainMenuTb = findViewById(R.id.mainMenuTb);
        setSupportActionBar(mainMenuTb);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FrameLayout bottomNavFrame = findViewById(R.id.bottomNavFrame);
        TabLayout bottomTab = findViewById(R.id.bottomNav);
        BottomPagerAdapter bottomPagerAdapter = new BottomPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        bottomTab.setTabsFromPagerAdapter(bottomPagerAdapter);
        bottomPagerAdapter.setAllTabIcons(pageIcons, bottomTab);
        bottomTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(pageIcons[tab.getPosition()]);
                fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), getFragmentFromPosition(tab.getPosition())).commit();
                bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getWindow().setStatusBarColor(getColor(R.color.black));
                }
//                switch (tab.getPosition()) {
//                    default:
//                    case 0:
//                        //toolbar.setBackgroundColor(ContextCompat.getColor(Home.this, R.color.colorAccent));
//                       // bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiBlack));
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                            getWindow().setStatusBarColor(getColor(R.color.semiBlack));
////
////                        }
//
//                        //fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
//                        break;
//                    case 1:
//                        // toolbar.setBackgroundColor(ContextCompat.getColor(Home.this, R.color.colorHover));
//                      //  bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray));
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                            getWindow().setStatusBarColor(getColor(android.R.color.darker_gray));
////                        }
//
//                        // fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
//                        break;
//                    case 2:
//                       // bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiGray));
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                            getWindow().setStatusBarColor(getColor(R.color.semiGray));
////                        }
//
//                        //  fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
//                        break;
//                    case 3:
//                     //   bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                            getWindow().setStatusBarColor(getColor(R.color.semiWhite));
////                        }
//
//                        //  fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
//                        break;
//
//                    case 4:
//                      //  bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                            getWindow().setStatusBarColor(getColor(R.color.green));
////                        }
//
//                        //  fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
//                        break;
//
//                    case 5:
//                      //  bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                            getWindow().setStatusBarColor(getColor(R.color.yellow));
////                        }
//
//                        // fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
//                        break;
//
//                    case 6:
//                      //  bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                            getWindow().setStatusBarColor(getColor(R.color.blue));
////                        }
//
//                        //  fragmentManager.beginTransaction().replace(bottomNavFrame.getId(), new DummyFragment()).commit();
//                        break;
//
//                    case 7:
//                      //  bottomTab.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.semiWhite));
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                            getWindow().setStatusBarColor(getColor(R.color.black));
////                        }
//
//
//                        break;
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setText("");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fragmentManager.beginTransaction().add(bottomNavFrame.getId(), getFragmentFromPosition(0)).commit();

        LinearLayout bottomNavLayout = findViewById(R.id.bottomNavLayout);
        setAnimatedBg(bottomNavLayout);

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
        mainContainerPager.setPadding(200, 50, 200, 50);
        mainContainerPager.setClipToPadding(false);
        mainContainerPager.setClipChildren(false);
        mainContainerPager.setOffscreenPageLimit(3);

        MenuPagerAdapter menuPagerAdapter = new MenuPagerAdapter(this, pageTitles);
        mainContainerPager.setAdapter(menuPagerAdapter);
        menuPagerAdapter.setClickListener((view, position) -> goToActivityFromPosition(position, MainActivity.this));


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleX(0.85f + r * 0.15f);
        });

        mainContainerPager.setPageTransformer(compositePageTransformer);

        mainContainerPager.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> Toast.makeText(MainActivity.this, "Scrolled x from " + oldScrollX + "to " + scrollX + " and y from " + oldScrollY + " to " + scrollY, Toast.LENGTH_SHORT).show());

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

        LinearLayout viewPagerLayout = findViewById(R.id.viewPagerLayout);
        setAnimatedBg(viewPagerLayout);
    }

    public static Point getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Point(location[0], location[1]);
    }

    public int getYOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }

    public int getXOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[0];
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setDragCenterSpot() {


        setContentView(R.layout.drag_center_spot);
        mainMenuTb = findViewById(R.id.mainMenuTb);
        setSupportActionBar(mainMenuTb);

        TextView textTopMid = findViewById(R.id.textTopMid);
        TextView textTopRight = findViewById(R.id.textTopRight);
        TextView textLeftTop = findViewById(R.id.textLeftTop);

        TextView textLeftMid = findViewById(R.id.textLeftMid);
        TextView textMidRight = findViewById(R.id.textMidRight);

        TextView textLeftBottom = findViewById(R.id.textLeftBottom);
        TextView textBottomMid = findViewById(R.id.textBottomMid);
        TextView textRightBottom = findViewById(R.id.textRightBottom);

        ImageButton centerB = findViewById(R.id.imageButton);

        ConstraintLayout dragConstrainLayout = findViewById(R.id.dragConstrainLayout);


        final boolean[] firstTouch = {false};

        final float layoutWidth = 400;
        final float layoutHeight = 500;

        System.out.println("height is " + layoutHeight + " width is " + layoutWidth);

        final float[] minX = {100};
        final float[] maxX = {400};
        final float[] maxY = {500};
        final float[] minY = {100};
        final float[] rawX = new float[1];
        final float[] rawY = new float[1];
        final float[] originalX = {centerB.getX()};
        final float[] originalY = {centerB.getY()};

        System.out.println("max x is " + maxX[0] + " and min x is " + minX[0] + " : max Y is " + maxY[0] + " and min Y is " + minY[0]);

        final float[] xFinger = new float[1];
        final float[] yFinger = new float[1];

        centerB.setOnTouchListener((view, event) -> {

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:

                    xFinger[0] = event.getX();
                    yFinger[0] = event.getY();

                    // minX[0] = getXOnScreen(textLeftMid);
                    // maxX[0] = getXOnScreen(textMidRight);

                    //  maxY[0] = getYOnScreen(textTopMid);
                    //  minY[0] = getYOnScreen(textBottomMid);

                    System.out.println("max x is " + maxX[0] + " and min x is " + minX[0] + " : max Y is " + maxY[0] + " and min Y is " + minY[0]);

                    if (!firstTouch[0]) {
                        originalX[0] = getXOnScreen(centerB);
                        originalY[0] = getYOnScreen(centerB);
                        System.out.println("Original x is " + originalX[0] + " : original y is " + originalY[0]);
                    }

                    firstTouch[0] = true;

                    lastAction = MotionEvent.ACTION_DOWN;
                    break;

                case MotionEvent.ACTION_MOVE:
                    float fingerMovedX, fingerMovedY;
                    fingerMovedX = event.getX();
                    fingerMovedY = event.getY();

                    float distanceX = fingerMovedX - xFinger[0];
                    float distanceY = fingerMovedY - yFinger[0];

                    rawX[0] = event.getRawX();
                    rawY[0] = event.getRawY();

                    float newX = view.getX() + distanceX;
                    float newY = view.getY() + distanceY;

                    System.out.println("get left : " + view.getLeft() + " get top : " + view.getTop());

                    view.setX(newX);
                    view.setY(newY);


                    System.out.println("x : " + newX + " y is " + newY);

                    /*if (newX >= minX[0] && newX <= maxX[0]) {
                        System.out.println("x is : " + newX);
                        view.setX(newX);
                    } else {
                        System.out.println("x is out of bounds");
                    }*/

                   /* if (newY <= minY[0] && newY >= maxY[0]) {
                        System.out.println("Y is : " + newY);
                        view.setY(newY);
                    } else {
                        System.out.println("Y is out of bounds : " + newY);
                    }
*/
                    //    System.out.println("new Y is " + newY + " and is less than " + maxY[0] + " and more than " + minY[0]);


                    new Handler(Looper.myLooper()).postDelayed(() -> {
                        //  centerB.setX(layoutWidth / 2);
                        // centerB.setY(layoutHeight / 2);
                        System.out.println("Original x is set to " + originalX[0] + " : original y is set to" + originalY[0]);

                    }, 2000);

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
        LinearLayout linearLayout = findViewById(R.id.dragCenterSpot);
        setAnimatedBg(linearLayout);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // addMenus(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backPressed = false;
        defaultSharedPreference.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        defaultSharedPreference.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        if (currentOption.equals(DRAWER)) {
            if (mainDrawer.isDrawerOpen(GravityCompat.START)) {
                mainDrawer.closeDrawer(GravityCompat.START);
            } else {
                if (!backPressed) {
                    backPressed = true;
                    Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                } else {
                    super.onBackPressed();
                }
            }
        } else {
            if (!backPressed) {
                backPressed = true;
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
            }
        }
    }

    public void showDragCenter(View view) {
        setDragCenterSpot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showViewPager(View view) {
        setViewPager();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showBottomNav(View view) {
        setBottomNavigation();
    }

    public void showTabs(View view) {
        setTabs();
    }

    public void showDrawer(View view) {
        setDrawer();
    }

    public void showGrid(View view) {
        setGrid();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(LAYOUT_SETTING_KEY)) {
            chooseLayout(sharedPreferences.getString(key, BOTTOM_NAVIGATION));
        }
    }
}