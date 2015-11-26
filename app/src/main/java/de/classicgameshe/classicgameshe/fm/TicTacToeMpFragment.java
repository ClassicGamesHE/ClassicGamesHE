package de.classicgameshe.classicgameshe.fm;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.classicgameshe.classicgameshe.R;


public class TicTacToeMpFragment extends Fragment implements View.OnClickListener {
    Button b1,b2,b3,b4;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice>pairedDevices;
    ListView lv;


    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_tictactoe_mp, container, false);

        b1 = (Button) rootview.findViewById(R.id.button);
        b2=(Button)   rootview.findViewById(R.id.button2);
        b3=(Button)   rootview.findViewById(R.id.button3);
        b4=(Button)   rootview.findViewById(R.id.button4);

        BA = BluetoothAdapter.getDefaultAdapter();
        lv = (ListView)rootview.findViewById(R.id.listView);
        return rootview;
    }

    @Override
    public void onClick(View v) {

    }

    public static class MyClass
    {
        static Context c;
        public MyClass(Context context)
        {
            c= context;
        }
    }

    public void on(View v){
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(MyClass.c, "Turned on",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MyClass.c, "Already on", Toast.LENGTH_LONG).show();
        }
    }

    public void off(View v){
        BA.disable();
        Toast.makeText(MyClass.c,"Turned off" ,Toast.LENGTH_LONG).show();
    }

    public  void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }

    public void list(View v){
        pairedDevices = BA.getBondedDevices();
        ArrayList list = new ArrayList();

        for(BluetoothDevice bt : pairedDevices)
            list.add(bt.getName());
        Toast.makeText(MyClass.c,"Showing Paired Devices",Toast.LENGTH_SHORT).show();

        //final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        //lv.setAdapter(adapter);
    }




}
