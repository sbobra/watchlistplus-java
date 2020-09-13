package com.example.watchlistplusj.ui.results;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.watchlistplusj.R;
import com.example.watchlistplusj.databinding.FiveStarLayoutBinding;
import com.example.watchlistplusj.ui.home.HomeViewModel;

public class FiveStarView extends ConstraintLayout {
    FiveStarLayoutBinding binding;
    AppCompatImageView[] imageViews = new AppCompatImageView[5];;

    public FiveStarView(Context context) {
        super(context);
        init();
    }

    public FiveStarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FiveStarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        binding = FiveStarLayoutBinding.inflate(LayoutInflater.from(getContext()), this, true);
        imageViews[0] = binding.star0;
        imageViews[1] = binding.star1;
        imageViews[2] = binding.star2;
        imageViews[3] = binding.star3;
        imageViews[4] = binding.star4;
    }

    public void setHomeViewModel(HomeViewModel homeViewModel) {
        //attach homeviewmodel to viewbinding for updating star rating on click
        binding.setHomeViewModel(homeViewModel);
    }

    public void setRating(StarRating rating) {
        Drawable d;
        for (AppCompatImageView img : imageViews) {
            d = img.getDrawable();
            d.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(getContext(), R.color.black), PorterDuff.Mode.SRC_ATOP));
        }

        int value = rating.getValue();
        for (int i = 0; i < value; i++) {
            d = imageViews[i].getDrawable();
            d.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP));
        }
    }

    public enum StarRating {
        ZERO_STAR(0), ONE_STAR(1), TWO_STAR(2), THREE_STAR(3), FOUR_STAR(4), FIVE_STAR(5);

        private final int value;

        StarRating(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
