package de.classicgameshe.classicgameshe;

/**
 * Created by mastereder on 15.10.15.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class statistic_Fragment extends Fragment {

    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.statistic_layout, container, false);
        return rootview;
    }
}



git status                  -> anzeige alle veränderten files
git add <pfad der datei>    -> für jedes file das commit werdn soll
git commit -m "Kommentar"
git push                    -> hochladen
git pull                    -> herunterladen