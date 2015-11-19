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

import java.util.ArrayList;

import de.classicgameshe.classicgameshe.adapter.TicTacToeDataBaseAdapter;

public class tictactoe_Fragment extends Fragment implements View.OnClickListener {
    //test statistik
    private TicTacToeDataBaseAdapter ticTacToeDataBaseAdapter;
    int x;
    int o;
    private final int delayMiliSeconds = 100;
    int delayCount = 1;

    boolean turn = true; // true = X & false = O
    int turn_count = 0;
    Button[] bArray = null;
    Button a1, a2, a3, b1, b2, b3, c1, c2, c3;


    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.tictactoe_layout, container, false);

        // get Instance  of Database Adapter
        ticTacToeDataBaseAdapter=new TicTacToeDataBaseAdapter(getActivity());


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

                setTicTacToeNewGameAnimation();


            }
        });

        return rootview;


    }

    private void setBtnBackgroundColor (Button[] buttons,int colorInt){
        for (Button b : buttons){
            b.setBackgroundColor(getResources().getColor(colorInt));
        }
    }

    private void setTicTacToeNewGameAnimation (){
        final Button[] firstTwoBtns = new Button[]{a2,b1};
        final Button[] threeBtns = new Button[]{c1,b2,a3};
        final Button[] endTwoBtns = new Button[]{c2,b3};

        Runnable runn1 = new Runnable() {
            @Override
            public void run() {
                a1.setBackgroundColor(getResources().getColor(R.color.white));

            }
        };
        Runnable runn2 = new Runnable() {
            @Override
            public void run() {
                a1.setBackgroundColor(getResources().getColor(R.color.red));
                setBtnBackgroundColor(firstTwoBtns, R.color.white);

            }
        };
        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                a1.setBackgroundColor(getResources().getColor(R.color.red));
                setBtnBackgroundColor(firstTwoBtns, R.color.blue);
                setBtnBackgroundColor(threeBtns, R.color.white);

            }
        };

        Runnable run2 = new Runnable() {
            @Override
            public void run() {
                a1.setBackgroundColor(getResources().getColor(R.color.white));
                setBtnBackgroundColor(firstTwoBtns, R.color.red);
                setBtnBackgroundColor(threeBtns, R.color.blue);
                setBtnBackgroundColor(endTwoBtns, R.color.white);
            }
        };

        Runnable run3 = new Runnable() {
            @Override
            public void run() {
                setBtnBackgroundColor(firstTwoBtns, R.color.white);
                setBtnBackgroundColor(threeBtns, R.color.red);
                setBtnBackgroundColor(endTwoBtns, R.color.blue);
                c3.setBackgroundColor(getResources().getColor(R.color.white));


            }
        };

        Runnable run4 = new Runnable() {
            @Override
            public void run() {
                setBtnBackgroundColor(threeBtns, R.color.white);
                setBtnBackgroundColor(endTwoBtns, R.color.red);
                c3.setBackgroundColor(getResources().getColor(R.color.blue));
            }
        };

        Runnable run5 = new Runnable() {
            @Override
            public void run() {
                setBtnBackgroundColor(endTwoBtns, R.color.white);
                c3.setBackgroundColor(getResources().getColor(R.color.red));
            }
        };

        Runnable run6 = new Runnable() {
            @Override
            public void run() {
                c3.setBackgroundColor(getResources().getColor(R.color.white));

            }
        };

        for (Button b : bArray) {
            b.setText("");
            b.setClickable(false);
        }

        a1.setBackgroundColor(getResources().getColor(R.color.blue));
        getView().postDelayed(runn2, delayMiliSeconds * delayCount);
        delayCount++;
        getView().postDelayed(run1, delayMiliSeconds*delayCount);
        delayCount++;
        getView().postDelayed(run2, delayMiliSeconds*delayCount);
        delayCount++;
        getView().postDelayed(run3, delayMiliSeconds*delayCount);
        delayCount++;
        getView().postDelayed(run4, delayMiliSeconds*delayCount);
        delayCount++;
        getView().postDelayed(run5, delayMiliSeconds*delayCount);
        delayCount++;
        getView().postDelayed(run6, delayMiliSeconds*delayCount);
        delayCount++;

        getView().postDelayed(run6, delayMiliSeconds*delayCount);
        delayCount++;
        getView().postDelayed(run5, delayMiliSeconds*delayCount);
        delayCount++;
        getView().postDelayed(run4, delayMiliSeconds*delayCount);
        delayCount++;
        getView().postDelayed(run3, delayMiliSeconds*delayCount);
        delayCount++;
        getView().postDelayed(run2, delayMiliSeconds*delayCount);
        delayCount++;
        getView().postDelayed(run1, delayMiliSeconds*delayCount);
        delayCount++;
        getView().postDelayed(runn2, delayMiliSeconds*delayCount);
        delayCount++;
        getView().postDelayed(runn1, delayMiliSeconds*delayCount);
        delayCount++;

        Runnable run = new Runnable() {
            @Override
            public void run() {
                for (Button b : bArray) {
                    b.setText("");
                    b.setClickable(true);
                }            }
        };
        getView().postDelayed(run,delayMiliSeconds*delayCount);
        delayCount = 1;
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
            b.setBackgroundColor(getResources().getColor(R.color.blue));


        } else {
            // O's turn
            b.setText("O");
            b.setBackgroundColor(getResources().getColor(R.color.red));

        }
        turn_count++;
        b.setClickable(false);
//        b.setBackgroundColor(Color.LTGRAY);
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
            } else{
                statisticCount("o");
            message("O wins");
                }
            for (Button b :bArray){
                b.setClickable(false);
            }
        } else if (turn_count == 9)
            message("Draw!");

    }
    private void statisticCount (String winner) {
        Log.w("Winner", winner);
        String userID = ((MainActivity) getActivity()).loadUserID();
        if (ticTacToeDataBaseAdapter.checkIfStatisticExists(userID)){
           if (winner =="x") {
               ticTacToeDataBaseAdapter.getData(userID);
               ticTacToeDataBaseAdapter.updateEntry(userID, 3, 3, 5);
               Log.v("update DATENBANTABLE:", "this:" + getall());
           }
            else {


           }

        }
        else {
            if (ticTacToeDataBaseAdapter.insertEntry(userID, 0, 0, 0)) {
                Log.v("insert DATENBANTABLE:", "this:" + getall());

        }


        }
        if (winner == "x")

            x++;
        else
            o++;
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
                b.setBackgroundColor(getResources().getColor(R.color.white));
            } else {
                b.setBackgroundColor(Color.LTGRAY);
            }
        }
    }
    @Override
    public void onStop() {
        super.onStop();

    }
    public ArrayList getall() {
        String[] test = {TicTacToeDataBaseAdapter.COLUMN_userID,TicTacToeDataBaseAdapter.COLUMN_x,TicTacToeDataBaseAdapter.COLUMN_o, TicTacToeDataBaseAdapter.COLUMN_multiplayer};
        ArrayList arrayLists = new ArrayList<>();
        return  arrayLists = ticTacToeDataBaseAdapter.getAllEntrys(TicTacToeDataBaseAdapter.TABLE_NAME, test, "", null, "", "", "");
    }
}
