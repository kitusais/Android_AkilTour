package com.kitusais.akiltourv2.ui.compte;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.controler.AccountController;
import com.kitusais.akiltourv2.controler.LoginSigninControler;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    private Button logOutButton;
    private Context context;
    private TextView usernameTv;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_compte, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("tool fragment");
            }
        });
        context = root.getContext();
        usernameTv = root.findViewById(R.id.compte_username);
        usernameTv.setText("Username : "+authPlayer.getPseudo());
        usernameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountController.popupEdit(context, "Username");
            }
        });
        logOutButton = root.findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSigninControler.logOut(context);
            }
        });
        return root;
    }
}