package com.example.planningappfrontend.pagesFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.example.planningappfrontend.MainActivity;
import com.example.planningappfrontend.R;

import java.util.Calendar;


public class TodoFragment extends Fragment {

    float xFinger = 0;
    float yFinger = 0;

    float originalX = 0;
    float originalY = 0;

    public TodoFragment() {
        // Required empty public constructor
    }

    public static TodoFragment newInstance() {
        return new TodoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_todo, container, false);

        CoordinatorLayout todoBg = v.findViewById(R.id.todoBg);

        View lineView = v.findViewById(R.id.lineView);

        TextView todoText = v.findViewById(R.id.todoText);
        todoText.setOnClickListener(v12 -> {
            System.out.println("alpha is : "+lineView.getAlpha());
            if (lineView.getAlpha() == 1.0) {
                animate(lineView, false);
            } else {
                animate(lineView, true);
            }
        });

        ImageButton todoStatus = v.findViewById(R.id.todoStatus);
        todoStatus.setOnClickListener(v1 -> chooseStatus(todoStatus));

        TextView todoTimeStamp = v.findViewById(R.id.todoTimeStamp);
        todoTimeStamp.setText(Calendar.getInstance().getTime().toString());

        ImageButton todoDrag = v.findViewById(R.id.todoDrag);
        originalX = todoDrag.getX();
        originalY = todoDrag.getY();
        todoDrag.setOnClickListener(v13 -> {
            todoDrag.setX(originalX);
            todoDrag.setY(originalY);
        });
        todoDrag.setOnTouchListener((v14, event) -> {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:

                    xFinger = event.getX();
                    yFinger = event.getY();

                    break;

                case MotionEvent.ACTION_MOVE:
                    float fingerMovedX, fingerMovedY;
                    fingerMovedX = event.getX();
                    fingerMovedY = event.getY();

                    float distanceX = fingerMovedX - xFinger;
                    float distanceY = fingerMovedY - yFinger;


                    float newX = todoBg.getX() + distanceX;
                    float newY = todoBg.getY() + distanceY;

                    todoBg.setX(newX);
                    todoBg.setY(newY);

                    break;

                case MotionEvent.ACTION_UP:

                    break;

                default:
                    return false;
            }
            return true;
        });


        return v;
    }

    private void animate(View iv, boolean in) {

        float min;
        float max;

        if (in) {
            min = 0.0f;
            max = 1.0f;
        } else {
            min = 1.0f;
            max = 0.0f;
        }

        System.out.println("visible " + in + " alpha : "+max);

        AlphaAnimation animation1 = new AlphaAnimation(min, max);

        animation1.setDuration(1500);
        animation1.setStartOffset(50);
       // animation1.setFillAfter(!in);
        iv.startAnimation(animation1);
    }

    private void chooseStatus(ImageButton button) {
        Dialog d = new Dialog(requireContext());
        d.setContentView(R.layout.set_todo_status_dialog);
        ImageButton success = d.findViewById(R.id.setSuccessB), fail = d.findViewById(R.id.setFailB);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button cancel = d.findViewById(R.id.dismissButton);
        cancel.setOnClickListener(v15 -> d.dismiss());
        d.show();
        success.setOnClickListener(v12 -> {
            button.setImageResource(R.drawable.ic_tick);
            d.dismiss();
        });
        fail.setOnClickListener(v1 -> {
            button.setImageResource(R.drawable.ic_cancel);
            d.dismiss();
        });
    }


}