package com.example.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvm.databinding.FragmentPythagorasBinding;

public class PythagorasFragment extends Fragment {

    private FragmentPythagorasBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentPythagorasBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PythagorasViewModel pythagorasViewModel = new ViewModelProvider(this).get(PythagorasViewModel.class);

        binding.calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float a = Float.parseFloat(binding.a.getText().toString());
                float b = Float.parseFloat(binding.b.getText().toString());

                pythagorasViewModel.calculate(a,b);
            }
        });

        pythagorasViewModel.finalValor.observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                binding.c.setText(String.format("%.4f",aFloat));
            }
        });

        pythagorasViewModel.maxASide.observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aSideMax) {
                if (aSideMax != null) {
                    binding.a.setError("The A Side is too big. It must be inferior than " + aSideMax);
                } else {
                    binding.a.setError(null);
                }
            }
        });

        pythagorasViewModel.maxBSide.observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float bSideMax) {
                if (bSideMax != null) {
                    binding.b.setError("The B Side is too big. It must be inferior than " + bSideMax);
                } else {
                    binding.b.setError(null);
                }
            }
        });

        pythagorasViewModel.loading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (loading) {
                    binding.loading.setVisibility(View.VISIBLE);
                    binding.c.setVisibility(View.GONE);
                } else {
                    binding.loading.setVisibility(View.GONE);
                    binding.c.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}