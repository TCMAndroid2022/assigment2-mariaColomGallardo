package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.R;

public class PlayerDialogFragment extends DialogFragment {
    private EditText name;
    private IPlayerDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.player_dialog, null));

        builder.setPositiveButton(R.string.strOK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name = (EditText) getDialog().findViewById(R.id.etPlayerName);
                listener.sendInput(name.getText().toString());
                getDialog().dismiss();
            }
        });

        builder.setNegativeButton(R.string.strCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getDialog().dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (IPlayerDialogListener) context;
        }
        catch(Exception e) {
            throw new ClassCastException(getActivity().toString());
        }
    }

    public interface IPlayerDialogListener {
        void sendInput(String input);
    }
}
