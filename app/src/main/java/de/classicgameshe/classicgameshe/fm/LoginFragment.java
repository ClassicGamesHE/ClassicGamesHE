package de.classicgameshe.classicgameshe;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
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

import de.classicgameshe.classicgameshe.MainActivity;
import de.classicgameshe.classicgameshe.R;
import de.classicgameshe.classicgameshe.adapter.LoginDataBaseAdapter;
import de.classicgameshe.classicgameshe.fm.HomeFragment;
import de.classicgameshe.classicgameshe.support.DialogHelper;


public class LoginFragment extends Fragment {

    private EditText userET;
    private EditText passwordET;
    private EditText repeatPasswordET;
    private Button loginBtn;
    private Button registerBtn;
    private LoginDataBaseAdapter loginDataBaseAdapter;

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
        String[] test = {"USERNAME","PASSWORD"};

        Log.v("DATENBANTABLE:", "this:" + loginDataBaseAdapter.selectRecordsFromDBList("LOGIN", test, "", null, "", "", ""));
        userET = (EditText) rootView.findViewById(R.id.login_user_name_et);
        passwordET = (EditText) rootView.findViewById(R.id.login_password_et);
        repeatPasswordET = (EditText) rootView.findViewById(R.id.login_repeat_password_et);

        loginBtn = (Button) rootView.findViewById(R.id.login_login_btn);
        registerBtn = (Button) rootView.findViewById(R.id.login_register_btn);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerBtn.getText().equals("Neu Registrieren")) {
                    repeatPasswordET.setVisibility(View.VISIBLE);
                    loginBtn.setVisibility(View.INVISIBLE);
                    registerBtn.setText("REGISTRIEREN");

                } else {
                    if (checkRepaetPwd() && checkRepeatInput(passwordET) && checkRepeatInput(repeatPasswordET)) {
                        //TODO: In der Datenbank neuen Account hinzufügen
                        loginDataBaseAdapter.insertEntry(userET.getText().toString(), passwordET.getText().toString());
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
                    //Als Benutzer einloggen
                    try {
                        if (loginDataBaseAdapter.loginUser(username, password)) {
                            ((MainActivity) getActivity()).switchFragment(HomeFragment.newInstance());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


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
                userET.setError("Bitte geben Sie den UserName ein");
            }
            if (TextUtils.isEmpty(passwordET.getText())){
                passwordET.setError("Bitte geben Sie das Passwort ein");
            }
            return false;

        }
    }

    private boolean checkRepaetPwd (){
        if (passwordET.getText().toString().equals(repeatPasswordET.getText().toString())){
            return true;
        }else{
            AlertDialog dialog = DialogHelper.createInfoDialog(getActivity(), "Fehler");
            dialog.show();
            return false;
        }
    }


    private boolean checkRepeatInput (EditText editText) {

//
        if (editText.getText().length() < 4) {
            String errorString = "mind 4 Zeichen !";
            editText.setError(errorString);
            return false;
        }else
        {
            return true;
        }
    }
}
