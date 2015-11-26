package de.classicgameshe.classicgameshe;

import android.app.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.inputmethod.InputMethodManager;

import de.classicgameshe.classicgameshe.fm.HomeFragment;
import de.classicgameshe.classicgameshe.fm.LoginFragment;
import de.classicgameshe.classicgameshe.fm.TicTacToeMpFragment;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    private static final String MY_PREF = "MyPrefs";
    private static final String USER_ID_KEY = "userID";
    private static final String USER_NAME_KEY = "userName";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private BluetoothAdapter BTAdapter;
    public static int REQUEST_BLUETOOTH = 1;

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

        BTAdapter = BluetoothAdapter.getDefaultAdapter();
        // Phone does not support Bluetooth so let the user know and exit.
        if (BTAdapter == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        if (!BTAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }


        // Set up the drawer.
       // mNavigationDrawerFragment.setUp(
         //       R.id.navigation_drawer,
           //     (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void blockDrawer(){
        mNavigationDrawerFragment.blockUp();
    }

    public void setUpNavigationDrawer(){
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment objFragment = null;

        switch (position) {
            case 0:
                objFragment = new HomeFragment();
                break;
            case 1:
                objFragment = new statistic_Fragment().newInstance();
                break;
            case 2:
                 objFragment = new tictactoe_Fragment();
                 break;
            case 3:
                objFragment = new TicTacToeMpFragment();
                break;
        }
        switchFragment(objFragment);
    }
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle =  getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
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
        if (id == R.id.action_settings) {
            saveUserDate("","");
            blockDrawer();
            switchFragment(LoginFragment.newInstance());
            mNavigationDrawerFragment.closeNavigationDrawer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchFragment (Fragment newFragment){
        //hide Keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null){
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

    public String loadUserID(){
        SharedPreferences sp =
                getSharedPreferences(MY_PREF,
                        Context.MODE_PRIVATE);
        return sp.getString(USER_ID_KEY, "");
    }

    public String loadUserName(){
        SharedPreferences sp =
                getSharedPreferences(MY_PREF,
                        Context.MODE_PRIVATE);
        return sp.getString(USER_NAME_KEY, "");
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }


    }

}
