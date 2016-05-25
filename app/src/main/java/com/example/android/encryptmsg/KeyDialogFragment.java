package com.example.android.encryptmsg;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import static android.app.AlertDialog.Builder;
import static android.app.AlertDialog.OnClickListener;

public class KeyDialogFragment extends DialogFragment {
    private EditText key;

    public interface KeyInputListener
    {
        void onSubmitkey(String key);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View layout=inflater.inflate(R.layout.fragment_passwd_dialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(layout);
        builder.setTitle("Input keys")
                .setPositiveButton(R.string.yes,new OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        key=(EditText)layout.findViewById(R.id.editPasswd);
                        KeyInputListener listener = (KeyInputListener) getActivity();
                        listener.onSubmitkey(key.getText().toString());


                    }
                }).setNegativeButton("Cancel", null);

                // Add action buttons
        return builder.create();
    }
}