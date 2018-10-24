package com.groupscheduler.www;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.security.Key;

/**
 * This application demos the use of the Firebase Login feature. It currently supports logging in
 * with Google, Facebook, Twitter, Email/Password, and Anonymous providers.
 * <p/>
 * The methods in this class have been divided into sections based on providers (with a few
 * general methods).
 * <p/>
 * {@link LoginActivity}
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    String email,password;

    EditText email_et, pw_et;
    Button registerButton,loginButton;
    FirebaseAuth firebaseAuth;
    Context login_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login_context = LoginActivity.this;
        /* Load the view and display it */
        setContentView(R.layout.login);

        email_et = findViewById(R.id.login_email);
        pw_et = findViewById(R.id.login_pw);
        registerButton = findViewById(R.id.create_btn);
        loginButton = findViewById(R.id.login_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(login_context, MainActivity.class));
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        email = email_et.getText().toString();
        password = pw_et.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(login_context, "Please fill in the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        switch(v.getId()){
            case R.id.login_btn:
                signIn();
                break;
            case R.id.create_btn:
                signUp();
                break;
            default:
                break;
        }
    }

    private void signUp() {
        if (password.length() < 6) {
            Toast.makeText(login_context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(login_context, MainActivity.class));
                            finish();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                pw_et.setError("error : weak password");
                                pw_et.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                email_et.setError("error : invalid email");
                                email_et.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                email_et.setError("error : user exists");
                                email_et.requestFocus();
                            } catch (Exception e) {
                                Toast.makeText(login_context, "Unknown Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void signIn() {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(login_context, MainActivity.class));
                            finish();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                pw_et.setError("error : weak password");
                                pw_et.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                email_et.setError("error : invalid email");
                                email_et.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                email_et.setError("error : user exists");
                                email_et.requestFocus();
                            } catch (Exception e) {
                                Toast.makeText(login_context, "Unknown Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER) {
            signIn();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
