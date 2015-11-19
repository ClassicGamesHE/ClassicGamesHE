package de.classicgameshe.classicgameshe.fm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;
import java.util.ArrayList;

import de.classicgameshe.classicgameshe.MainActivity;
import de.classicgameshe.classicgameshe.R;
import de.classicgameshe.classicgameshe.adapter.LoginDataBaseAdapter;
import de.classicgameshe.classicgameshe.support.DialogHelper;


public class LoginFragment extends Fragment {

    private EditText userET;
    private EditText passwordET;
    private EditText repeatPasswordET;
    private Button loginBtn;
    private Button registerBtn;
    public LoginDataBaseAdapter loginDataBaseAdapter;
    private ArrayList<ArrayList<String>> arrayLists;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);





        // get Instance  of Database Adapter
        loginDataBaseAdapter=new LoginDataBaseAdapter(getActivity());
        try {
            loginDataBaseAdapter=loginDataBaseAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        String[] test = {LoginDataBaseAdapter.USER_ID,LoginDataBaseAdapter.USER_NAME_COLUMN,LoginDataBaseAdapter.PASSWORD_COLUMN};
        arrayLists = new ArrayList<>();
        arrayLists = loginDataBaseAdapter.selectRecordsFromDBList(LoginDataBaseAdapter.TABLE_NAME, test, "", null, "", "", "");
        Log.v("DATENBANTABLE:", "this:" + arrayLists);


        userET = (EditText) rootView.findViewById(R.id.login_user_name_et);
        passwordET = (EditText) rootView.findViewById(R.id.login_password_et);
        repeatPasswordET = (EditText) rootView.findViewById(R.id.login_repeat_password_et);

        loginBtn = (Button) rootView.findViewById(R.id.login_login_btn);
        registerBtn = (Button) rootView.findViewById(R.id.login_register_btn);

        if (arrayLists.size() == 0){
            repeatPasswordET.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.INVISIBLE);
            registerBtn.setText(getString(R.string.login_fragment_register_btn_text));
        }

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerBtn.getText().equals(getString(R.string.login_fragment_register_new_btn_text))) {
                    repeatPasswordET.setVisibility(View.VISIBLE);
                    loginBtn.setVisibility(View.INVISIBLE);
                    registerBtn.setText(getString(R.string.login_fragment_register_btn_text));

                } else {
                    if (checkRepaetPwd() && checkRepeatInput(passwordET) && checkRepeatInput(repeatPasswordET)) {
                        //TODO: In der Datenbank neuen Account hinzufügen
                        String username = userET.getText().toString();
                        String password = passwordET.getText().toString();

                        if (loginDataBaseAdapter.checkIfUserExists(username)) {
                            loginDataBaseAdapter.insertEntry(username, password);
                            //Als Benutzer einloggen
                           loginUser(username,password);
                        } else {
                            Dialog dialog = DialogHelper.createInfoDialogWithMessage(getActivity(), getString(R.string.dialog_title_fail),
                                    getString(R.string.dialog_message_user_exists));
                            dialog.show();
                        }
                    }

                }

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLoginData()) {
                    String username = userET.getText().toString();
                    String password = passwordET.getText().toString();
                    Log.v("PASSWORD","HASH:"+ password.hashCode());
                    //Als Benutzer einloggen
                    loginUser(username,password);
                }
            }
        });


    }


    private void loginUser (String username,String password){
        try {
            if (loginDataBaseAdapter.loginUser(username, password)) {

                //DATEN des USERS bekommen
                String[] test = {LoginDataBaseAdapter.USER_ID,LoginDataBaseAdapter.USER_NAME_COLUMN,LoginDataBaseAdapter.PASSWORD_COLUMN};
                String whereClaus = "username=? AND password=?";
                Log.v("GETUSERNAME", "THIS:" + loginDataBaseAdapter.selectRecordsFromDBList(LoginDataBaseAdapter.TABLE_NAME, test, whereClaus, new String[]{username, password}, "", "", ""));
                String userIDString = loginDataBaseAdapter.selectRecordsFromDBList(LoginDataBaseAdapter.TABLE_NAME, test, whereClaus, new String[]{username, password}, "", "", "").get(0).get(0);
                String userNameString = loginDataBaseAdapter.selectRecordsFromDBList(LoginDataBaseAdapter.TABLE_NAME, test, whereClaus, new String[]{username, password}, "", "", "").get(0).get(1);
                Log.v("GETUSERNAME__ID", "THIS:" + userIDString);
                //

                //UserDaten speichern
                ((MainActivity)getActivity()).saveUserDate(userIDString,userNameString);
                ((MainActivity) getActivity()).switchFragment(HomeFragment.newInstance(username));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        loginDataBaseAdapter.close();

    }

    private boolean checkLoginData(){
        if (!TextUtils.isEmpty(userET.getText()) && !TextUtils.isEmpty(passwordET.getText())){
            return true;
        }else{
            if (TextUtils.isEmpty(userET.getText())) {
                userET.setError(getString(R.string.login_fragment_error_no_user_name));
            }
            if (TextUtils.isEmpty(passwordET.getText())){
                passwordET.setError(getString(R.string.login_fragment_error_no_passowrd));
            }
            return false;

        }
    }

    private boolean checkRepaetPwd (){
        if (passwordET.getText().toString().equals(repeatPasswordET.getText().toString())){
            return true;
        }else{
            AlertDialog dialog = DialogHelper.createInfoDialogWithMessage(getActivity(), getString(R.string.dialog_title_fail),
                    getString(R.string.dialog_message_wrong_password));
            dialog.show();
            return false;
        }
    }


    private boolean checkRepeatInput (EditText editText) {

        if (editText.getText().length() < 4) {
            String errorString = getString(R.string.login_fragment_min_password_lenght_text);
            editText.setError(errorString);
            return false;
        }else
        {
            return true;
        }
    }
}

