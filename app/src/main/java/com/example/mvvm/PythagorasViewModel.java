package com.example.mvvm;

import android.app.Application;
import android.net.IpSecManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.loader.content.Loader;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PythagorasViewModel extends AndroidViewModel {
    Executor executor;

    PythagorasSimulator simulator;
    MutableLiveData<Float> finalValor = new MutableLiveData<>();
    MutableLiveData<Float> maxASide = new MutableLiveData<>();
    MutableLiveData<Float> maxBSide = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();


    public PythagorasViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulator = new PythagorasSimulator();
    }

    public void calculate(float a, float b) {
        final PythagorasSimulator.Calc calc = new PythagorasSimulator.Calc(a,b);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                simulator.calculate(calc, new PythagorasSimulator.Callback() {
                    @Override
                    public void whenPythagorasIsCalculated(float c) {
                        maxASide.postValue(null);
                        maxBSide.postValue(null);
                        finalValor.postValue(c);
                    }

                    @Override
                    public void ifASideIsTooBig(float aSideMax){
                        maxASide.postValue(aSideMax);
                    }

                    @Override
                    public void ifBSideIsTooBig(float bSideMax){
                        maxBSide.postValue(bSideMax);
                    }

                    @Override
                    public void startCalculating(){
                        loading.postValue(true);
                    }

                    @Override
                    public void stopCalculating(){
                        loading.postValue(false);
                    }
                });
            }
        });
    }
}
