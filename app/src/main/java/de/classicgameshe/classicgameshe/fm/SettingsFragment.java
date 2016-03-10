package de.classicgameshe.classicgameshe.fm;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLException;

import de.classicgameshe.classicgameshe.MainActivity;
import de.classicgameshe.classicgameshe.R;
import de.classicgameshe.classicgameshe.adapter.LoginDataBaseAdapter;
import de.classicgameshe.classicgameshe.support.DialogHelper;


public class SettingsFragment extends Fragment {

    private TextView nameTV;
    private Button changePwdBtn;
    private LinearLayout changePwdLayout;
    private Button saveNewPwdBtn;
    private EditText oldPwdET;
    private EditText newPwdET;
    private EditText repeatNewPwdEt;
    private LoginDataBaseAdapter loginDataBaseAdapter;
    private String userName;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        userName = ((MainActivity) getActivity()).loadUserName();
        nameTV.setText(String.format(getString(R.string.settings_fragment_text), userName));
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
                if (checkOldPwd() && checkNewPwd() && checkRepaetPwd()) {
                    //Passwort wird geändert
                    if (loginDataBaseAdapter.updateEntry(userName, newPwdET.getText().toString())) {
                        ((MainActivity) getActivity()).switchFragment(HomeFragment.newInstance(userName));
                    } else {
                        AlertDialog dialog = DialogHelper.createInfoDialog(getActivity(),
                                getString(R.string.dialog_message_can_not_change_password));
                        dialog.show();
                    }
                }
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if (loginDataBaseAdapter != null) {
            loginDataBaseAdapter.close();
        }

    }

    private boolean checkOldPwd() {
        //Datatbase öffnen
        loginDataBaseAdapter = new LoginDataBaseAdapter(getActivity());
        try {
            loginDataBaseAdapter = loginDataBaseAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String oldPassword = loginDataBaseAdapter.userPwd(userName);
        if (String.valueOf(oldPwdET.getText().toString().hashCode()).equals(oldPassword)) {
            return true;
        } else {
            AlertDialog dialog = DialogHelper.createInfoDialogWithMessage(getActivity(), getString(R.string.dialog_title_fail),
                    getString(R.string.settings_fragment_old_pwd_wrong));
            dialog.show();
            return false;
        }
    }

    private boolean checkNewPwd() {
        String newPasswordString = newPwdET.getText().toString();
        if (newPasswordString.length() < 4) {
            String errorString = getString(R.string.login_fragment_min_password_lenght_text);
            newPwdET.setError(errorString);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkRepaetPwd() {
        if (newPwdET.getText().toString().equals(repeatNewPwdEt.getText().toString())) {
            return true;
        } else {
            AlertDialog dialog = DialogHelper.createInfoDialogWithMessage(getActivity(), getString(R.string.dialog_title_fail),
                    getString(R.string.dialog_message_wrong_password));
            dialog.show();
            return false;
        }
    }
}
