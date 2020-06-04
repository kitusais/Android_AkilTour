package com.kitusais.akiltourv2.view;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitusais.akiltourv2.MainAppActivity;
import com.kitusais.akiltourv2.R;
import com.kitusais.akiltourv2.dao.FirebaseDatabaseHelper;
import com.kitusais.akiltourv2.controler.GameControler;
import com.kitusais.akiltourv2.controler.RecyclerView_Config;
import com.kitusais.akiltourv2.model.Player;

import java.util.List;

public class ResultFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ResultViewModel mViewModel;
    private TextView resultTW;
    private int countJoueurs = 0;
    public static String resultGagnant = "";

    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        Log.i("ResultFragment","lancéééé!!");
        View root = null;
//        root = inflater.inflate(R.layout.result_fragment, container, false);
//
//        mRecyclerView = root.findViewById(R.id.recyclerview_joueurs);
//        resultTW = root.findViewById(R.id.result_tw);
//        new FirebaseDatabaseHelper().readNews(new FirebaseDatabaseHelper.DataStatus() {
////            mRecyclerView =  findViewById(R.id.recyclerview_joueurs);
//            @Override
//            public void DataIsLoaded(){
//            }
//
//            @Override
//            public void DataIsInserted(List<Player> joueurs, List<String> keys) {
//                    Log.i("RESULTGAME",""+joueurs.size());
//                new RecyclerView_Config().setConfig(mRecyclerView,getContext(),
//                        joueurs, keys);
//                if(countJoueurs < joueurs.size()){
//                    countJoueurs = joueurs.size();
//                    resultGagnant = GameControler.gagnant(joueurs, resultTW);
//
//                    resultTW.setText(resultTW.getText()+"\n avec "+countJoueurs+" joueurs");
//                }
//            }
//
//            @Override
//            public void DataIsUpdated() {
//
//            }
//
//            @Override
//            public void DataIsDeleted() {
//
//            }
//        });
//// This callback will only be called when MyFragment is at least Started.
//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                Intent i1 = new Intent(getContext(), MainAppActivity.class);
//                i1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                getContext().startActivity(i1);
//                getActivity().finish();
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
//
//        // The callback can be enabled or disabled here or in handleOnBackPressed()
        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ResultViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onPause() {
        Log.i("ResultFragment", "onPause() was called");
//        if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
//        } else {
//
//        }
        super.onPause();
    }

//    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }
//    }

}
