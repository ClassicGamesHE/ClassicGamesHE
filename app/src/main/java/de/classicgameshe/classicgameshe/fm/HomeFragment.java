package de.classicgameshe.classicgameshe.fm;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.classicgameshe.classicgameshe.MainActivity;
import de.classicgameshe.classicgameshe.R;
import de.classicgameshe.classicgameshe.adapter.LoginDataBaseAdapter;


public class HomeFragment extends Fragment {
    private Button ticTacToeBtn;
    private Button settingsBtn;

    public final static String USERNAME = "username";
    private Button statisticBtn;

    public static HomeFragment newInstance(String username) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME,username);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticTacToeBtn = (Button) view.findViewById(R.id.home_tic_tac_toe_btn);
        statisticBtn = (Button) view.findViewById(R.id.home_statistic_btn);
        settingsBtn = (Button) view.findViewById(R.id.home_settings_btn);

        ticTacToeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).switchFragment(new TictactoeFragment());
            }
        });
        statisticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).switchFragment(StatisticFragment.newInstance());
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).switchFragment(SettingsFragment.newInstance());
            }
        });
    }



}
