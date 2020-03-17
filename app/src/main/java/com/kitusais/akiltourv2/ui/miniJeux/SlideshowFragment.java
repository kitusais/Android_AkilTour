package com.kitusais.akiltourv2.ui.miniJeux;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.controler.GameDoubleButtonControler;
import com.kitusais.akiltourv2.controler.GetGameDAO;
import com.kitusais.akiltourv2.view.ResultFragment;

import static com.kitusais.akiltourv2.MainActivity.numRandomGame;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    public Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = null;
        context = this.getContext();

        // POUR ENVOYER UN JEU ALEATOIR
//        if(numRandomGame == 1){
            slideshowViewModel =
                    ViewModelProviders.of(this).get(SlideshowViewModel.class);
            root = inflater.inflate(R.layout.fragment_double_button, container, false);
            GameDoubleButtonControler.startGame(context,root);
//        }else if(numRandomGame == 2){
//            slideshowViewModel =
//                    ViewModelProviders.of(this).get(SlideshowViewModel.class);
//            root = inflater.inflate(R.layout.fragment_chifoumi, container, false);
//        }else if(numRandomGame == 3){
//            slideshowViewModel =
//                    ViewModelProviders.of(this).get(SlideshowViewModel.class);
//            root = inflater.inflate(R.layout.fragment_bigger_button, container, false);
//        }
//        final TextView textView = root.findViewById(R.id.text_slideshow);
        GetGameDAO getGameDAO  =new GetGameDAO(this.getContext());
        getGameDAO.execute("random");
//        slideshowViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(""+numRandomGame);
//            }
//        });
        Button testButton = root.findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("JEU","click !!!");
                Fragment fragment = new ResultFragment();
                replaceFragment(fragment);
            }
        });
        return root;
    }


    public void replaceFragment(Fragment someFragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
//        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onPause() {
        Log.i("Fragment minijeux","onPause called");
        super.onPause();

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
    }
}