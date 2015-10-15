package de.classicgameshe.classicgameshe.support;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by lukashenze on 24.09.15.
 */
public class DialogHelper {

    public static AlertDialog createInfoDialog(Context context, String title){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setTitle(title)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setMessage("Die beiden Passwörter müssen übereinstimmen");

        AlertDialog dialog = alertDialogBuilder.create();
        return dialog;
    }
}
