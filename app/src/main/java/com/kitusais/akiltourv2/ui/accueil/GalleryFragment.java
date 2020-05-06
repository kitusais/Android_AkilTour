package com.kitusais.akiltourv2.ui.accueil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.ui.Calendrier.HomeFragment;
import com.kitusais.akiltourv2.view.ResultFragment;

import static com.kitusais.akiltourv2.MainActivity.authPlayer;
import static com.kitusais.akiltourv2.MainActivity.previousFragment;
import static com.kitusais.akiltourv2.controler.GameDoubleButtonControler.finished;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    public static TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = null;

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            Log.i("dededezd",""+getFragmentManager().toString());
        }else{
            Log.i("fzefefzefer","paaaaaaaaaaas booooon");
        }

            Log.i("AcceuilFragment", "launched");
            Log.i("AcceuilFragment", "********************************************************************************");
            root = inflater.inflate(R.layout.fragment_gallery, container, false);
            textView = root.findViewById(R.id.text_gallery);
            galleryViewModel.getText().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    if (authPlayer != null) {
                        textView.setText("Bonjour "+authPlayer.getPseudo());
                    } else {
                        textView.setText("personne logué");
                    }
                }
            });
        Log.i("Aceeuil Fragment","won preboucle = "+finished);
        if(finished){
            finished = false;
            Log.i("Aceeuil Fragment","won = "+finished);
//            root = inflater.inflate(R.layout.result_fragment, container, false);
//            getActivity().finish();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, new ResultFragment());

            transaction.commit();
//
        }
        Log.i("Acceuil Fragment", ""+previousFragment);
        if("createCalendar".equals(previousFragment)){
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, new HomeFragment());
            transaction.commit();
        }else if("deleteCalendar".equals(previousFragment)){
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, new HomeFragment());
            transaction.commit();
        }
        return root;
    }


}