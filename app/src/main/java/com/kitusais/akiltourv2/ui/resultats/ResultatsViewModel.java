package com.kitusais.akiltourv2.ui.resultats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResultatsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ResultatsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}