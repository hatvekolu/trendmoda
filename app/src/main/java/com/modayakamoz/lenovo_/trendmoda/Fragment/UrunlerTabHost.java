package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UrunlerTabHost extends Fragment {

    private FragmentTabHost mTabHost;
    Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_urunler_tab_host, container, false);

        mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Elbise").setContent(intent),
                ElbiseFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Çanta").setContent(intent),
                CantaFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Tesettür").setContent(intent),
                TesetturFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator("Ayakkabı").setContent(intent),
                AyakkabiFragmnet.class, null);
        TextView x = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        x.setTextSize(12);
        TextView x1 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        x1.setTextSize(12);
        TextView x2 = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        x2.setTextSize(12);
        TextView x3 = (TextView) mTabHost.getTabWidget().getChildAt(3).findViewById(android.R.id.title);
        x3.setTextSize(12);

        return view;
    }

}
