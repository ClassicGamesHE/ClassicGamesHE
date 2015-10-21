package de.classicgameshe.classicgameshe.fm;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.sql.SQLException;
import java.util.ArrayList;

import de.classicgameshe.classicgameshe.MainActivity;
import de.classicgameshe.classicgameshe.R;
import de.classicgameshe.classicgameshe.adapter.LoginDataBaseAdapter;


public class HomeFragment extends Fragment {
    private TextView halloTV;
    private LoginDataBaseAdapter loginDataBaseAdapter;

    public final static String USERNAME = "username";

    public static HomeFragment newInstance(String username) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME,username);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        halloTV = (TextView) view.findViewById(R.id.home_hallo_tv);
        loginDataBaseAdapter = new LoginDataBaseAdapter(getActivity());
        try {
            loginDataBaseAdapter = loginDataBaseAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        LoginDataBaseAdapter loginDataBaseAdapter = new LoginDataBaseAdapter(getActivity());
        String[] test = {"USERNAME", "PASSWORD"};
//        halloTV.setText((LoginFragment.newInstance().loginDataBaseAdapter.selectRecordsFromDBList("LOGIN", test, "", null, "", "", "")).toString());
        ArrayList<ArrayList<String>> arrayLists = new ArrayList<>();
        arrayLists = loginDataBaseAdapter.selectRecordsFromDBList("LOGIN", test, "", null, "", "", "");
        Log.v("DATENBANTABLE:", "this:" + arrayLists);

        Log.v("HOME:", "this:" + ((MainActivity) getActivity()).laodeUserID());
    }



}
