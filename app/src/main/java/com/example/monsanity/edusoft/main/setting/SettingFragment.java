package com.example.monsanity.edusoft.main.setting;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    LinearLayout lnChangePassword;
    LinearLayout lnSignOut;
    private FirebaseUser user;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        lnChangePassword = view.findViewById(R.id.ln_setting_change_password);
        lnSignOut = view.findViewById(R.id.ln_setting_sign_out);
        lnChangePassword.setOnClickListener(this);
        lnSignOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ln_setting_sign_out:
                showLogOutDialog();
                break;
            case R.id.ln_setting_change_password:
                showChangePasswordDialog();
        }
    }

    private void showLogOutDialog() {
        final Dialog dialog = new Dialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_logout_confirm,null);
        dialog.setContentView(view);
        TextView tvLogOutCancel = view.findViewById(R.id.tv_logout_cancel);
        TextView tvLogOutConfirm = view.findViewById(R.id.tv_logout_confirm);
        tvLogOutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvLogOutConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    private void showChangePasswordDialog() {
        final Dialog dialog = new Dialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_password,null);
        dialog.setContentView(view);
        final EditText edtCurrentPassword = view.findViewById(R.id.edt_current_password);
        final EditText edtNewPassword = view.findViewById(R.id.edt_new_password);
        final EditText edtConfirmNewPassword = view.findViewById(R.id.edt_confirm_new_password);
        TextView tvPasswordCancel = view.findViewById(R.id.tv_password_cancel);
        TextView tvPasswordSave = view.findViewById(R.id.tv_password_save);

        tvPasswordCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tvPasswordSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = edtCurrentPassword.getText().toString();
                String newPass = edtNewPassword.getText().toString();
                if(isPasswordValid(oldPass,
                                    newPass,
                                    edtConfirmNewPassword.getText().toString())){
                    changePassword(dialog, oldPass, newPass);
                }else{
                    Toast.makeText(getContext(), "Please retype your new password correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    private boolean isPasswordValid(String currentPass, String newPass, String confirmPass){
        return !newPass.equals("")
                && confirmPass.equals(newPass);
    }

    private void changePassword(final Dialog dialog, String oldPassword, final String newPassword) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String email = user.getEmail();
            AuthCredential credential = EmailAuthProvider.getCredential(email,oldPassword);
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(getContext(), "Something went wrong. \nPlease try again later", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getContext(), "Password updated successfully.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(getContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
        }
    }

}
