package com.devfie.thoth.homepage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.devfie.thoth.R;
import com.devfie.thoth.base.BaseActivity;
import com.devfie.thoth.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomepageFragment extends BaseFragment {




    public HomepageFragment() {
        // Required empty public constructor
    }

    public static HomepageFragment newInstance() {

        Bundle args = new Bundle();

        HomepageFragment fragment = new HomepageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbar();
    }

    private void setToolbar() {

        View view = initToolbar(R.layout.toolbar_home_page);
     final  ImageButton more = (ImageButton) view.findViewById(R.id.btnMore);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuBuilder menuBuilder =new MenuBuilder(getContext());
                MenuInflater inflater = new MenuInflater(getContext());
                inflater.inflate(R.menu.main, menuBuilder);
                MenuPopupHelper optionsMenu = new MenuPopupHelper(getContext(), menuBuilder, more);
                optionsMenu.setForceShowIcon(true);

// Set Item Click Listener
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.opt1: // Handle option1 Click
                                return true;
                            case R.id.opt2: // Handle option2 Click
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {}
                });

                optionsMenu.show();
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
