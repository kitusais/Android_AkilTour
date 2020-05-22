package com.kitusais.akiltourv2.ui.miniJeux;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.controler.GameDoubleButtonControler;
import com.kitusais.akiltourv2.dao.GetGameDAO;
import com.kitusais.akiltourv2.view.ResultFragment;

public class SlideshowFragment extends Fragment {

    public Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root;
        context = this.getContext();

        // POUR ENVOYER UN JEU ALEATOIR
//        if(numRandomGame == 1){
        SlideshowViewModel slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
            root = inflater.inflate(R.layout.fragment_double_button, container, false);
            GameDoubleButtonControler.startGame(context,root);
        //root = inflater.inflate(R.layout.fragment_slideshow, container, false);
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
        // a decommenter pour test ifnlate game
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


    private void replaceFragment(Fragment someFragment) {

        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
//        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onPause() {
        super.onPause();
        // c'était un test pour le backPressed et quit direct mais ça fonctionne apparement sans :)
        //assert getFragmentManager() != null;
        //if (getFragmentManager().getBackStackEntryCount() > 0) {
        //    getFragmentManager().popBackStack();
        //}
    }
}