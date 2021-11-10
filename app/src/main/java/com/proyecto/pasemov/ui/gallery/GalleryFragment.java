package com.proyecto.pasemov.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.proyecto.pasemov.R;
import com.proyecto.pasemov.databinding.FragmentGalleryBinding;
import com.proyecto.pasemov.ui.gallery.controler.PagerController;

public class GalleryFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tab1,tab2,tab3;
    PagerController pagerAdapter;
    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);


        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tabLayout=root.findViewById(R.id.tablayout);
        viewPager=root.findViewById(R.id.viewpager);
        tab1=root.findViewById(R.id.tabcontacto);
        tab2=root.findViewById(R.id.correo);
        tab3=root.findViewById(R.id.tabnotas);
        pagerAdapter=new PagerController(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    pagerAdapter.notifyDataSetChanged();
                }
                if(tab.getPosition()==1){
                    pagerAdapter.notifyDataSetChanged();
                }
                if(tab.getPosition()==2){
                    pagerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}