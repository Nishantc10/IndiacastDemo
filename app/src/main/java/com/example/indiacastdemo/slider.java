package com.example.indiacastdemo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.channguyen.rsv.RangeSliderView;


public class slider extends Fragment {
    RangeSliderView smallSlider;
    RangeSliderView smallSlider1;
    RangeSliderView smallSlider2;
    RangeSliderView smallSlider3;

    public slider() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_slider, container, false);
    smallSlider = v.findViewById(R.id.rsv_small);
    smallSlider1 = v.findViewById(R.id.rsv_large);
    smallSlider2 = v.findViewById(R.id.rsv_custom);
    smallSlider3 = v.findViewById(R.id.rsv_another);
        return v;

    }
    final RangeSliderView.OnSlideListener listener = new RangeSliderView.OnSlideListener() {
        @Override
        public void onSlide(int index) {
            Toast.makeText(
                    getContext(),
                    "Hi index: " + index,
                    Toast.LENGTH_SHORT)
                    .show();
        }
    };
}
