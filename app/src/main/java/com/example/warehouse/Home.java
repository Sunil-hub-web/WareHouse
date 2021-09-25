package com.example.warehouse;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public class Home extends Fragment {

    private static final int NUM_PAGES = 3 ;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    EditText edit_Serach;
    RecyclerView recyclerView,recyclerView1;
    LinearLayoutManager linearLayoutManager,linearLayoutManager1;
    ProductDetailsAdapter productDetailsAdapter;
    ProductDetailsAdapter1 productDetailsAdapter1;
    TextView text_name;
    int Images[]={R.drawable.rectangle49,R.drawable.rectangle51,R.drawable.rectangle49};
    int Images1[]={R.drawable.rectangle56,R.drawable.rectangle_57,R.drawable.rectangle56};

    int currentPage = 0;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4000;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home,container,false);

        viewPager = view.findViewById(R.id.viewPager);

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.home_home,null);
        //text_name = view1.findViewById(R.id.name);


        edit_Serach = view.findViewById(R.id.serach);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView1 = view.findViewById(R.id.recycler1);

        edit_Serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                SerachPage search = new SerachPage();
                ft1.replace(R.id.frame,search);
                ft1.commit();

            }
        });

       linearLayoutManager = new LinearLayoutManager (getContext(),
               LinearLayoutManager.HORIZONTAL,false);
       linearLayoutManager1 = new LinearLayoutManager (getContext(),
               LinearLayoutManager.HORIZONTAL,false);


        productDetailsAdapter = new ProductDetailsAdapter (getContext().getApplicationContext(),Images);
        recyclerView.setLayoutManager (linearLayoutManager);
        recyclerView.setHasFixedSize (true);
        recyclerView.setAdapter (productDetailsAdapter);

        productDetailsAdapter1 = new ProductDetailsAdapter1 (getContext().getApplicationContext(),Images1);
        recyclerView1.setLayoutManager (linearLayoutManager1);
        recyclerView1.setHasFixedSize (true);
        recyclerView1.setAdapter (productDetailsAdapter1);
        //sliderDotspanel = view.findViewById(R.id.sliderDots);

        createViewPager();

        return view;
    }

    public void createViewPager() {


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();

        ScrollViewpager();

        //dots = new ImageView[dotscount];
       /* for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 8, 8, 8);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.active_dot));*/

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            /*    for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.active_dot));*/

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void ScrollViewpager(){

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }
}
