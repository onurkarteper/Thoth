package com.devfie.thoth.base;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.devfie.thoth.R;
import com.devfie.thoth.manager.LocalDataManager;
import com.devfie.thoth.manager.LoginManager;


/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class BaseFragment extends Fragment {
    protected LoginManager loginManager = LoginManager.getInstance();
    protected LocalDataManager localDataManager = LocalDataManager.getInstance();

    protected void baseFragmentTransAction(Fragment fragment) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).baseFragmentTransaction(fragment, fragment.getClass().getSimpleName());
        }
    }

    protected void showToast(String msg) {

        try {
          //  Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            Snackbar.make(getView(),msg,Snackbar.LENGTH_SHORT).show();

        } catch (Exception e) {

        }

    }

    protected void showToast(int msg) {
        try {
            Snackbar.make(getView(),msg,Snackbar.LENGTH_SHORT).show();
        } catch (Exception e) {

        }

    }


    public View initToolbar(int layoutResourceId) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setCustomView(layoutResourceId);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View mCustomView = actionBar.getCustomView();
        return mCustomView;
    }


    protected void childFragmentTransaction(int contaierId, Fragment fragment) {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).closeKeyboard();
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            if (!getChildFragmentManager().getBackStackEntryAt(getChildFragmentManager().getBackStackEntryCount() - 1).getName().equals(fragment.getClass().getSimpleName())) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                //      fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left, R.anim.enter_from_left, R.anim.exit_to_right);
                // fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(contaierId, fragment);
                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
                fragmentTransaction.commit();
            }
        } else {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(contaierId, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commit();
        }
    }

    protected void childFragmentTransaction(int contaierId, Fragment fragment, String TAG) {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).closeKeyboard();
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            if (!getChildFragmentManager().getBackStackEntryAt(getChildFragmentManager().getBackStackEntryCount() - 1).getName().equals(TAG)) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                //      fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left, R.anim.enter_from_left, R.anim.exit_to_right);
                // fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(contaierId, fragment);
                fragmentTransaction.addToBackStack(TAG);
                fragmentTransaction.commit();
            }
        } else {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(contaierId, fragment);
            fragmentTransaction.addToBackStack(TAG);
            fragmentTransaction.commit();
        }
    }

    protected Integer getScreenWidht() {
        return ((BaseActivity) getActivity()).getScreenWidght();
    }

}
