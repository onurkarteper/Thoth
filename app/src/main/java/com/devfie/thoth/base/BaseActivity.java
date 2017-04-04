package com.devfie.thoth.base;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.devfie.thoth.R;
import com.devfie.thoth.events.KeyboardStateChangeEvent;

import org.greenrobot.eventbus.EventBus;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected int container;

    public void baseFragmentTransaction(Fragment fragment, String mTAG) {

        closeKeyboard();
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if (!getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName().equals(mTAG)) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(container, fragment);
                fragmentTransaction.addToBackStack(mTAG);
                fragmentTransaction.commit();
                Log.d("Fragment Transaction: ", this.getClass().getSimpleName() + " " + mTAG);
            }
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(container, fragment);
            fragmentTransaction.addToBackStack(mTAG);
            fragmentTransaction.commit();
            Log.d("Fragment Transaction: ", this.getClass().getSimpleName() + " " + mTAG);
        }
    }


    public void baseFirstFragment(Fragment fragment) {
        closeKeyboard();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(container, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
        Log.d("First Fragment : ", this.getClass().getSimpleName() + " " + fragment.getClass().getSimpleName());
    }

    public void closeKeyboard() {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void setToolbar(Toolbar toolbar) {
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void showToast(int msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    final int MIN_KEYBOARD_HEIGHT_PX = 150;

    public void listenKeyboard() {
        final View decorView = this.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private final Rect windowVisibleDisplayFrame = new Rect();
            private int lastVisibleDecorViewHeight;

            @Override
            public void onGlobalLayout() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
                        final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();

                        // Decide whether keyboard is visible from changing decor view height.
                        if (lastVisibleDecorViewHeight != 0) {
                            if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                                // Calculate current keyboard height (this includes also navigation bar height when in fullscreen mode).
                                int currentKeyboardHeight = decorView.getHeight() - windowVisibleDisplayFrame.bottom;
                                // Notify listener about keyboard being shown.
                                // listener.onKeyboardShown(currentKeyboardHeight);
                                EventBus.getDefault().post(new KeyboardStateChangeEvent(true));

                            } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                                // Notify listener about keyboard being hidden.
                                EventBus.getDefault().post(new KeyboardStateChangeEvent(false));

                            }
                        }
                        // Save current decor view height for the next call.
                        lastVisibleDecorViewHeight = visibleDecorViewHeight;
                    }
                });
                // Retrieve visible rectangle inside window.

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public int getScreenHeight() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return height;
    }


    public int getScreenWidght() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return width;
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(container, fragment);
     //   fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
        Log.d("Fragment Added: ", this.getClass().getSimpleName() + " " + fragment.getClass().getSimpleName());
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
        Log.d("Fragment Show: ", this.getClass().getSimpleName() + " " + fragment.getClass().getSimpleName());
    }

    public void hideFragment(Fragment fragment) {
     FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
        Log.d("Fragment Hide: ", this.getClass().getSimpleName() + " " + fragment.getClass().getSimpleName());
    }
}
