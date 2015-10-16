package de.classicgameshe.classicgameshe;

/**
 * Created by mastereder on 15.10.15.
 */
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.sql.SQLException;

import de.classicgameshe.classicgameshe.adapter.TicTacToeDataBaseAdapter;


public class tictactoe_Fragment extends Fragment implements View.OnClickListener {
    //test statistik
    int x;
    int o;
    public TicTacToeDataBaseAdapter ticTacToeDataBaseAdapter;

    boolean turn = true; // true = X & false = O
    int turn_count = 0;
    Button[] bArray = null;
    Button a1, a2, a3, b1, b2, b3, c1, c2, c3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get Instance  of Database Adapter
        ticTacToeDataBaseAdapter=new TicTacToeDataBaseAdapter(getActivity());
        try {
            ticTacToeDataBaseAdapter=ticTacToeDataBaseAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.tictactoe_layout, container, false);

        a1 = (Button) rootview.findViewById(R.id.A1);
        b1 = (Button) rootview.findViewById(R.id.B1);
        c1 = (Button) rootview.findViewById(R.id.C1);
        a2 = (Button) rootview.findViewById(R.id.A2);
        b2 = (Button) rootview.findViewById(R.id.B2);
        c2 = (Button) rootview.findViewById(R.id.C2);
        a3 = (Button) rootview.findViewById(R.id.A3);
        b3 = (Button) rootview.findViewById(R.id.B3);
        c3 = (Button) rootview.findViewById(R.id.C3);
        bArray = new Button[] { a1, a2, a3, b1, b2, b3, c1, c2, c3 };

        for (Button b : bArray)
            b.setOnClickListener(this);

        Button bnew = (Button) rootview.findViewById(R.id.button1);
        bnew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                turn = true;
                turn_count = 0;
                enableOrDisable(true);
            }
        });

        return rootview;


    }

    @Override
    public void onClick(View v) {
        buttonClicked(v);
    }

    private void buttonClicked(View v) {
        Button b = (Button) v;
        if (turn) {
            // X's turn
            b.setText("X");

        } else {
            // O's turn
            b.setText("O");
        }
        turn_count++;
        b.setClickable(false);
        b.setBackgroundColor(Color.LTGRAY);
        turn = !turn;

        checkForWinner();
    }

    private void checkForWinner() {

        boolean there_is_a_winner = false;

        // horizontal:
        if (a1.getText() == a2.getText() && a2.getText() == a3.getText()
                && !a1.isClickable())
            there_is_a_winner = true;
        else if (b1.getText() == b2.getText() && b2.getText() == b3.getText()
                && !b1.isClickable())
            there_is_a_winner = true;
        else if (c1.getText() == c2.getText() && c2.getText() == c3.getText()
                && !c1.isClickable())
            there_is_a_winner = true;

            // vertical:
        else if (a1.getText() == b1.getText() && b1.getText() == c1.getText()
                && !a1.isClickable())
            there_is_a_winner = true;
        else if (a2.getText() == b2.getText() && b2.getText() == c2.getText()
                && !b2.isClickable())
            there_is_a_winner = true;
        else if (a3.getText() == b3.getText() && b3.getText() == c3.getText()
                && !c3.isClickable())
            there_is_a_winner = true;

            // diagonal:
        else if (a1.getText() == b2.getText() && b2.getText() == c3.getText()
                && !a1.isClickable())
            there_is_a_winner = true;
        else if (a3.getText() == b2.getText() && b2.getText() == c1.getText()
                && !b2.isClickable())
            there_is_a_winner = true;

        if (there_is_a_winner) {
            if (!turn) {
                statisticCount("x");
                message("X wins");
            }else
                statisticCount("o");
                message("O wins");
            enableOrDisable(false);
        } else if (turn_count == 9)
            message("Draw!");

    }
    private void statisticCount (String winner) {
        Log.w("Winner", winner);
        if (winner == "x")
            x++;
        else
            o++;
        ticTacToeDataBaseAdapter.updateEntry(winner);
        Log.v("Spieler X", Integer.toString(x));
        Log.v("Spieler O", Integer.toString(o));
    }

    private void message(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT)
                .show();
    }


    private void enableOrDisable(boolean enable) {
        for (Button b : bArray) {
            b.setText("");
            b.setClickable(enable);
            if (enable) {
                b.setBackgroundColor(Color.parseColor("#33b5e5"));
            } else {
                b.setBackgroundColor(Color.LTGRAY);
            }
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        ticTacToeDataBaseAdapter.close();

    }
}
