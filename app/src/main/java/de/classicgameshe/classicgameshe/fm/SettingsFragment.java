package de.classicgameshe.classicgameshe.fm;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.classicgameshe.classicgameshe.MainActivity;
import de.classicgameshe.classicgameshe.R;
import de.classicgameshe.classicgameshe.support.DialogHelper;


public class SettingsFragment extends Fragment {

    private TextView nameTV;
    private Button changePwdBtn;
    private LinearLayout changePwdLayout;
    private Button saveNewPwdBtn;
    private EditText oldPwdET;
    private EditText newPwdET;
    private EditText repeatNewPwdEt;


    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameTV = (TextView) view.findViewById(R.id.settings_name_tv);
        changePwdBtn = (Button) view.findViewById(R.id.settings_change_password_btn);
        changePwdLayout = (LinearLayout) view.findViewById(R.id.settings_change_password_layout);
        saveNewPwdBtn = (Button) view.findViewById(R.id.settings_save_new_pwd_btn);

        oldPwdET = (EditText) view.findViewById(R.id.settings_old_pwd_ed);
        newPwdET = (EditText) view.findViewById(R.id.settings_new_pwd_ed);
        repeatNewPwdEt = (EditText) view.findViewById(R.id.settings_repeat_new_pwd_ed);


        changePwdLayout.setVisibility(View.GONE);
        nameTV.setText(((MainActivity) getActivity()).loadUserName());
        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changePwdBtn.getText().toString().equals(getString(R.string.settings_fragment_change_passwort_text))) {
                    changePwdLayout.setVisibility(View.VISIBLE);
                    changePwdBtn.setText(getString(R.string.settings_fragment_back_text));
                } else {
                    changePwdLayout.setVisibility(View.GONE);
                    changePwdBtn.setText(getString(R.string.settings_fragment_change_passwort_text));
                }
            }
        });

        saveNewPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOldPwd() && checkNewPwd() && checkRepaetPwd()){
                    //Passwort ändern
                }
            }
        });

    }

    private boolean checkOldPwd (){
        //TODO Echtes Psswort bekommen
        String oldPassword = "1234";
        if (oldPwdET.getText().toString().equals(oldPassword)){
            return true;
        }else {
            return false;
        }
    }

    private boolean checkNewPwd (){
        String newPasswordString = newPwdET.getText().toString();
        if (newPasswordString.length() < 4) {
            String errorString = getString(R.string.login_fragment_min_password_lenght_text);
            newPwdET.setError(errorString);
            return false;
        }else
        {
            return true;
        }
    }

    private boolean checkRepaetPwd (){
        if (newPwdET.getText().toString().equals(repeatNewPwdEt.getText().toString())){
            return true;
        }else{
            AlertDialog dialog = DialogHelper.createInfoDialogWithMessage(getActivity(), getString(R.string.dialog_title_fail),
                    getString(R.string.dialog_message_wrong_password));
            dialog.show();
            return false;
        }
    }
}
