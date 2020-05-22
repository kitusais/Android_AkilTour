package com.kitusais.akiltourv2.ui.resultats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kitusais.akiltourv2.R;

public class ResultatsFragment extends Fragment {

    private ResultatsViewModel resultatsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resultatsViewModel =
                ViewModelProviders.of(this).get(ResultatsViewModel.class);
        View root = inflater.inflate(R.layout.result_fragment, container, false);
        //final TextView textView = root.findViewById(R.id.text_share);
        //resultatsViewModel.getText().observe(this, new Observer<String>() {
        //    @Override
         //   public void onChanged(@Nullable String s) {
         //       textView.setText(s);
         //   }
        //});
        return root;
    }
}