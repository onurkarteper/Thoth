package com.devfie.thoth.main;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.devfie.thoth.R;
import com.devfie.thoth.base.BaseActivity;
import com.devfie.thoth.homepage.HomepageFragment;
import com.devfie.thoth.messages.MessagesFragment;
import com.devfie.thoth.notifications.NotificationsFragment;
import com.devfie.thoth.profile.ProfileFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    HomepageFragment homepageFragment = HomepageFragment.newInstance();
    NotificationsFragment notificationsFragment = NotificationsFragment.newInstance();
    MessagesFragment messagesFragment = MessagesFragment.newInstance();
    ProfileFragment profileFragment = ProfileFragment.newInstance();
    BottomBar bottomBar;
    private List<String> tabStack = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = R.id.main_container;
        setToolbar((Toolbar) findViewById(R.id.toolbar));
        bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        addFragments();
        listenerTabs();
        litenFragments();


    }

    private void litenFragments() {
    }

    private void addFragments() {

    }

    private void listenerTabs() {


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_homepage:
                        hideFragment(notificationsFragment);
                        hideFragment(messagesFragment);
                        hideFragment(profileFragment);
                        if (!homepageFragment.isAdded())
                            addFragment(homepageFragment);
                        showFragment(homepageFragment);
                        changeStack(HomepageFragment.class.getSimpleName());
                        break;
                    case R.id.tab_notifications:
                        hideFragment(homepageFragment);
                        hideFragment(messagesFragment);
                        hideFragment(profileFragment);
                        if (!notificationsFragment.isAdded())
                            addFragment(notificationsFragment);
                        showFragment(notificationsFragment);
                        changeStack(NotificationsFragment.class.getSimpleName());

                        break;
                    case R.id.tab_messages:
                        hideFragment(homepageFragment);
                        hideFragment(notificationsFragment);
                        hideFragment(profileFragment);
                        if (!messagesFragment.isAdded())
                            addFragment(messagesFragment);
                        showFragment(messagesFragment);
                        changeStack(MessagesFragment.class.getSimpleName());

                        break;
                    case R.id.tab_profile:
                        hideFragment(homepageFragment);
                        hideFragment(notificationsFragment);
                        hideFragment(messagesFragment);
                        if (!profileFragment.isAdded())
                            addFragment(profileFragment);
                        showFragment(profileFragment);
                        changeStack(ProfileFragment.class.getSimpleName());

                        break;
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    public void onBackPressed() {

        if (tabStack.size() > 1) {
            removeStack();
        } else {
            super.onBackPressed();
        }
        //   showFragment(getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size()-2));
    }

    public void changeStack(String activeFragmentTag) {
        for (String tag : tabStack) {
            if (tag.equals(activeFragmentTag)) {
                tabStack.remove(tag);
                break;
            }
        }
        tabStack.add(activeFragmentTag);
    }

    public void removeStack() {
        hideLastFragment();
        tabStack.remove(tabStack.size() - 1);
        showCurrentFragment();
    }

    private void hideLastFragment() {
        String tag = tabStack.get(tabStack.size() - 1);
        if (tag.equals(HomepageFragment.class.getSimpleName())) {
            hideFragment(homepageFragment);
        } else if (tag.equals(NotificationsFragment.class.getSimpleName())) {
            hideFragment(notificationsFragment);
        } else if (tag.equals(ProfileFragment.class.getSimpleName())) {
            hideFragment(profileFragment);
        } else if (tag.equals(MessagesFragment.class.getSimpleName())) {
            hideFragment(messagesFragment);
        }
    }

    private void showCurrentFragment() {
        String tag = tabStack.get(tabStack.size() - 1);
        if (tag.equals(HomepageFragment.class.getSimpleName())) {
            showFragment(homepageFragment);
            bottomBar.selectTabAtPosition(0);
        } else if (tag.equals(NotificationsFragment.class.getSimpleName())) {
            showFragment(notificationsFragment);
            bottomBar.selectTabAtPosition(1);

        } else if (tag.equals(ProfileFragment.class.getSimpleName())) {
            showFragment(profileFragment);
            bottomBar.selectTabAtPosition(3);

        } else if (tag.equals(MessagesFragment.class.getSimpleName())) {
            showFragment(messagesFragment);
            bottomBar.selectTabAtPosition(2);

        }
    }

}
