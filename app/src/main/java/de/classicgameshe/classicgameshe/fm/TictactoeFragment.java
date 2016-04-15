package de.classicgameshe.classicgameshe.fm;

/**
 * Created by mastereder on 15.10.15.
 */

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.classicgameshe.classicgameshe.MainActivity;
import de.classicgameshe.classicgameshe.R;
import de.classicgameshe.classicgameshe.adapter.DeviceListActivity;
import de.classicgameshe.classicgameshe.adapter.TicTacToeBluetoothAdapter;
import de.classicgameshe.classicgameshe.adapter.TicTacToeDataBaseAdapter;

public class TictactoeFragment extends Fragment implements View.OnClickListener {
    //Bluetooth
    private static final String TAG = "TicTacToe";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Layout Views
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSendButton;

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    /**
     * Array adapter for the conversation thread
     */
    private ArrayAdapter<String> mConversationArrayAdapter;

    /**
     * String buffer for outgoing messages
     */
    private StringBuffer mOutStringBuffer;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    /**
     * Member object for the chat services
     */
    private TicTacToeBluetoothAdapter mChatService = null;

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


    private RelativeLayout modeLayout;
    private Button singlePlayerBtn;
    private Button multiPlayerBtn;

    private RelativeLayout bluetoothLayout;
    private ImageButton bluetoothIBtn;
    private TextView connectedDeviceTv;
    private TextView connectedColorTv;

    private LinearLayout mainLayout;

    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.tictactoe_layout, container, false);
        // get Instance  of Database Adapter

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Activity activity = getActivity();
            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            activity.finish();
        }
        else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            //setupLink();
        }

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


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modeLayout = (RelativeLayout) view.findViewById(R.id.tic_tac_toe_mode_layout);
        singlePlayerBtn = (Button) view.findViewById(R.id.singlepalyerBtn);
        multiPlayerBtn = (Button) view.findViewById(R.id.multiplayerBtn);

        bluetoothLayout = (RelativeLayout) view.findViewById(R.id.bluetooth_layout);
        bluetoothIBtn = (ImageButton) view.findViewById(R.id.bluetooth_activate_iv);
        connectedDeviceTv = (TextView) view.findViewById(R.id.bluetooth_connection_status_title_tv);
        connectedColorTv = (TextView) view.findViewById(R.id.bluetooth_connection_status_color_tv);

        mainLayout = (LinearLayout) view.findViewById(R.id.tic_tac_toe_main_layout);

        View.OnClickListener modeOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == singlePlayerBtn){
                    bluetoothLayout.setVisibility(View.GONE);

                }else {
                    bluetoothLayout.setVisibility(View.VISIBLE);

                }
                modeLayout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);
            }
        };

        singlePlayerBtn.setOnClickListener(modeOnClickListener);
        multiPlayerBtn.setOnClickListener(modeOnClickListener);


        bluetoothIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            }

        });
        bluetoothIBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ensureDiscoverable();
                return true;
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            setupChat();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == TicTacToeBluetoothAdapter.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }


    /**
     * Set up the UI and background operations for chat.
     */
    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        //mConversationArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.message);

        mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the compose field with a listener for the return key
        mOutEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the send button with a listener that for click events
        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                View view = getView();
                if (null != view) {
                    //TextView textView = (TextView) view.findViewById(R.id.edit_text_out);
                    //String message = textView.getText().toString();
                    sendMessage("df");
                }
            }
        });

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new TicTacToeBluetoothAdapter(getActivity(), mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    /**
     * Makes this device discoverable.
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != TicTacToeBluetoothAdapter.STATE_CONNECTED) {
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            mOutEditText.setText(mOutStringBuffer);
        }
    }

    /**
     * The action listener for the EditText widget, to listen for the return key
     */
    private TextView.OnEditorActionListener mWriteListener
            = new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            // If the action is a key-up event on the return key, send the message
            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                String message = view.getText().toString();
                sendMessage(message);
            }
            return true;
        }
    };

    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     */
    private void setStatus(int resId) {
        Activity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }

    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     */
    private void setStatus(CharSequence subTitle) {
        Activity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Activity activity = getActivity();
            switch (msg.what) {
                case TicTacToeBluetoothAdapter.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case TicTacToeBluetoothAdapter.STATE_CONNECTED:
                            setStatus( mConnectedDeviceName);
                            mConversationArrayAdapter.clear();
                            break;
                        case TicTacToeBluetoothAdapter.STATE_CONNECTING:
                            setStatus("Connecting");
                            break;
                        case TicTacToeBluetoothAdapter.STATE_LISTEN:
                        case TicTacToeBluetoothAdapter.STATE_NONE:
                            setStatus("Not connected");
                            break;
                    }
                    break;
                case TicTacToeBluetoothAdapter.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case TicTacToeBluetoothAdapter.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case TicTacToeBluetoothAdapter.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(TicTacToeBluetoothAdapter.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case TicTacToeBluetoothAdapter.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(TicTacToeBluetoothAdapter.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");

                    getActivity().finish();
                }
        }
    }

    /**
     * Establish connection with other divice
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
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

        boolean getWinner = false;

        // horizontal
        if (a1.getText() == a2.getText() && a2.getText() == a3.getText()
                && !a1.isClickable())
            getWinner = true;
        else if (b1.getText() == b2.getText() && b2.getText() == b3.getText()
                && !b1.isClickable())
            getWinner = true;
        else if (c1.getText() == c2.getText() && c2.getText() == c3.getText()
                && !c1.isClickable())
            getWinner = true;

            // vertical
        else if (a1.getText() == b1.getText() && b1.getText() == c1.getText()
                && !a1.isClickable())
            getWinner = true;
        else if (a2.getText() == b2.getText() && b2.getText() == c2.getText()
                && !b2.isClickable())
            getWinner = true;
        else if (a3.getText() == b3.getText() && b3.getText() == c3.getText()
                && !c3.isClickable())
            getWinner = true;

            // diagonal
        else if (a1.getText() == b2.getText() && b2.getText() == c3.getText()
                && !a1.isClickable())
            getWinner = true;
        else if (a3.getText() == b2.getText() && b2.getText() == c1.getText()
                && !b2.isClickable())
            getWinner = true;

        if (getWinner) {
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
        Log.v("userID", "this:" + userID);

        if (ticTacToeDataBaseAdapter.checkIfStatisticExists(userID)){
           if (winner =="x") {
//              ArrayList<String> arrayList = ticTacToeDataBaseAdapter.getData(userID);
               int xWins = ticTacToeDataBaseAdapter.getXWins(userID);
               Log.v("xWins", "this:" + xWins);
               Log.v("getData", "this:" + ticTacToeDataBaseAdapter.getData(userID));


               xWins = ++xWins;
               ticTacToeDataBaseAdapter.updateXWins(userID, xWins);
//               Log.v("update DATENBANTABLE:", "this:" + getall());
           }
            else {

               int oWins = ticTacToeDataBaseAdapter.getOWins(userID);
               Log.v("xWins", "this:" + oWins);

               oWins = ++oWins;
               ticTacToeDataBaseAdapter.updateOWins(userID, oWins);
//               Log.v("insert DATENBANTABLE:", "this:" + getall());

           }

        }
        else {
            if (winner == "x") {
                ticTacToeDataBaseAdapter.insertEntry(userID, 1, 0, 0);
            }else{
                ticTacToeDataBaseAdapter.insertEntry(userID, 0, 1, 0);
            }
            Log.v("insert DATENBANTABLE:", "this:" + getall());


        }
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
