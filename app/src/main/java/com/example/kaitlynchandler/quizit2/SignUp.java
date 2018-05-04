package com.example.kaitlynchandler.quizit2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private EditText editFName;
    private EditText editLName;
    private EditText editEmail;
    private EditText editPassword;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editFName = (EditText) findViewById(R.id.FName);
        editLName = (EditText) findViewById(R.id.LName);
        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        signUpButton = (Button) findViewById(R.id.signUp);



        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String userFName = editFName.getText().toString();
                String userLName = editLName.getText().toString();
                String userEmail = editEmail.getText().toString();
                String userPasswrd = editPassword.getText().toString();

                if(isEmpty(userFName) || isEmpty(userLName) || isEmpty(userEmail) || isEmpty(userPasswrd))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    builder.setMessage("Please verify that all fields have been filled in.");
                    builder.setNegativeButton("Retry", null);
                    builder.create();
                    builder.show();
                }
                else
                {
                    String userData = RequestManager.signUp(userFName, userLName, userEmail, userPasswrd, "Teacher");
                    Toast.makeText(SignUp.this, userData,
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUp.this, Take_a_Quiz.class);
                    SignUp.this.startActivity(intent);
                }
            }
        });


    }
    public Boolean isEmpty(String str)
    {
        if(str.length() == 0 || str == null || str.equals(""))
        {
            return true;
        }
        else
            return false;
    }
}