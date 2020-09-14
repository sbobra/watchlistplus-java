package com.example.watchlistplusj.ui.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.watchlistplusj.R;
import com.example.watchlistplusj.databinding.FragmentDashboardBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class DashboardFragment extends Fragment {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 2;


    private DashboardViewModel dashboardViewModel;

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
//        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        /**
         * The pager widget, which handles animation and allows swiping horizontally to access previous
         * and next wizard steps.
         */
        ViewPager2 viewPager;

        /**
         * The pager adapter, which provides the pages to the view pager widget.
         */
        FragmentStateAdapter pagerAdapter;

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = binding.pager;
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());

        TabLayout tabLayout = binding.tabLayout;
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("OBJECT " + (position + 1))
        ).attach();

        return binding.getRoot();
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new Test1Fragment();
            } else {
                return new Test2Fragment();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}