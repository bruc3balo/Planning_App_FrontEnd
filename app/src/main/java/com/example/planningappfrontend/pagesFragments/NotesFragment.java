package com.example.planningappfrontend.pagesFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planningappfrontend.R;

import java.util.Calendar;


public class NotesFragment extends Fragment {

    public static final String CONTENT = "Content";

    public NotesFragment() {
    }

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.fragment_notes, container, false);

        TextView noteTitle = v.findViewById(R.id.noteTitle);
        TextView noteContent = v.findViewById(R.id.noteContent);
        TextView noteCreatedAt = v.findViewById(R.id.noteCreatedAt);
        noteCreatedAt.setText(Calendar.getInstance().getTime().toString());

        noteTitle.setOnClickListener(v1 -> {
            if (noteContent.getText().toString().equals(CONTENT)) {
                hideTitle(noteContent,noteTitle);
            } else {
                showTitle(noteContent,noteTitle);
            }
        });

        return v;
    }

    private void showTitle(TextView content,TextView title) {
        content.setText(CONTENT);
        title.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.eye_open,0);
        Toast.makeText(requireContext(), "Show", Toast.LENGTH_SHORT).show();
    }

    private void hideTitle(TextView content,TextView title) {
        content.setText(replaceWithX(content.getText().toString()));
        title.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.no_entry,0);
        Toast.makeText(requireContext(), "Hide", Toast.LENGTH_SHORT).show();
    }

    static String replaceWithX (String s) {
        String xx = "";
        for (int i = 0; i <= s.length() - 1; i++) {
            xx = xx.concat("x  ");
        }

        return xx;
    }

}