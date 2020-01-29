package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.modayakamoz.lenovo_.trendmoda.R;


public class ProfilTabHost extends Fragment {


    public static FragmentTabHost mTabHost;
    Intent intent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profil_tab_host, container, false);
        mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Profil").setContent(intent),
                ProfilFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Para Durumu").setContent(intent),
                ParaDurumuFragment.class, null);

        return view;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }


}
