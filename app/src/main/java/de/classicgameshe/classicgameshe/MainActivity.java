package de.classicgameshe.classicgameshe;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import de.classicgameshe.classicgameshe.fm.HomeFragment;
import de.classicgameshe.classicgameshe.fm.LoginFragment;
import de.classicgameshe.classicgameshe.fm.SettingsFragment;
import de.classicgameshe.classicgameshe.fm.TictactoeFragment;
import de.classicgameshe.classicgameshe.fm.StatisticFragment;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    private static final String MY_PREF = "MyPrefs";
    private static final String USER_ID_KEY = "userID";
    private static final String USER_NAME_KEY = "userName";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchFragment(LoginFragment.newInstance());

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
//        mNavigationDrawerFragment.setUp(
//                R.id.navigation_drawer,
//                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment objFragment = null;

        switch (position) {
            case 0:
                objFragment = new HomeFragment();
                break;
            case 1:
                objFragment = new StatisticFragment().newInstance();
                break;
            case 2:
                objFragment = new TictactoeFragment();
                break;
        }
        switchFragment(objFragment);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_logout:
                //TODO Ausloggen
                saveUserDate("", "");
                switchFragment(LoginFragment.newInstance());
                mNavigationDrawerFragment.closeNavigationDrawer();
                break;
            case R.id.action_settings:
                switchFragment(SettingsFragment.newInstance());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchFragment(Fragment newFragment) {
        //hide Keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.container, newFragment).
                commit();

    }

    public void saveUserDate(String userID, String userName) {
        SharedPreferences sp =
                getSharedPreferences(MY_PREF,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_ID_KEY, userID);
        editor.putString(USER_NAME_KEY, userName);
        editor.commit();
    }

    public String loadUserID() {
        SharedPreferences sp =
                getSharedPreferences(MY_PREF,
                        Context.MODE_PRIVATE);
        return sp.getString(USER_ID_KEY, "");
    }

    public String loadUserName() {
        SharedPreferences sp =
                getSharedPreferences(MY_PREF,
                        Context.MODE_PRIVATE);
        return sp.getString(USER_NAME_KEY, "");
    }
}
