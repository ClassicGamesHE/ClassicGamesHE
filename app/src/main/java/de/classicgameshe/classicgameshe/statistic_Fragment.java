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

import de.classicgameshe.classicgameshe.support.PercentView;

public class statistic_Fragment extends Fragment {

    View rootview;
    PercentView percentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.statistic_layout, container, false);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        percentView = (PercentView) view.findViewById(R.id.percentview);
        int x = 5;
        int o = 3;
        float onePercent = 100 /(float)(x+o);
        float setPercentage = onePercent*o;
        Log.v("INT","X = "+x);
        Log.v("INT","O = "+o);
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
