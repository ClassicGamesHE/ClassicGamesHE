package de.classicgameshe.classicgameshe.support;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import de.classicgameshe.classicgameshe.MainActivity;
import de.classicgameshe.classicgameshe.R;
import de.classicgameshe.classicgameshe.fm.LoginFragment;

/**
 * Created by lukashenze on 24.09.15.
 */
public class DialogHelper {

    public static AlertDialog createInfoDialog(Context context, String title){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setTitle(title)
                            .setPositiveButton(context.getString(R.string.dialog_button_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

        AlertDialog dialog = alertDialogBuilder.create();
        return dialog;
    }

    public static AlertDialog createInfoDialogWithMessage(Context context, String title, String text){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setTitle(title)
                .setPositiveButton(context.getString(R.string.dialog_button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setMessage(text);

        AlertDialog dialog = alertDialogBuilder.create();
        return dialog;
    }

    public static AlertDialog createInfoDialogWithLoginBtn(final MainActivity mainActivity, String title, String text){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mainActivity);

        alertDialogBuilder.setTitle(title)
                .setPositiveButton(mainActivity.getString(R.string.dialog_button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton(mainActivity.getString(R.string.back_to_login), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainActivity.switchFragment(LoginFragment.newInstance());
                    }
                })
                .setMessage(text);

        AlertDialog dialog = alertDialogBuilder.create();
        return dialog;
    }
}
