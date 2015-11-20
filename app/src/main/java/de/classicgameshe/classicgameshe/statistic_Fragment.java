package de.classicgameshe.classicgameshe;

/**
 * Created by mastereder on 15.10.15.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.classicgameshe.classicgameshe.adapter.TicTacToeDataBaseAdapter;
import de.classicgameshe.classicgameshe.support.PercentView;

public class statistic_Fragment extends Fragment {

    View rootview;
    PercentView percentView;

    public static statistic_Fragment newInstance() {
        statistic_Fragment fragment = new statistic_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.statistic_layout, container, false);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        percentView = (PercentView) view.findViewById(R.id.percentview);
        String userID = ((MainActivity) getActivity()).loadUserID();
        TicTacToeDataBaseAdapter ticTacToeDataBaseAdapter = new TicTacToeDataBaseAdapter(getActivity());

        int xWins = ticTacToeDataBaseAdapter.getXWins(userID);
        int oWins = ticTacToeDataBaseAdapter.getOWins(userID);

        float onePercent = 100 /(float)(xWins+oWins);
        float setPercentage = onePercent*oWins;
        Log.v("INT","X = "+xWins);
        Log.v("INT","O = "+oWins);
        Log.v("INT","onePercent = "+onePercent);
        Log.v("INT","setPercentage = "+setPercentage);
        // x = 5 o = 3
        // 100 / 7
        // 14,29
        percentView.setPercentage(setPercentage);
    }
}



//git status                  -> anzeige alle veränderten files
//git add <pfad der datei>    -> für jedes file das commit werdn soll
//git commit -m "Kommentar"
//git push                    -> hochladen
//git pull                    -> herunterladen
