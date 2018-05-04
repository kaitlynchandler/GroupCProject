package com.example.kaitlynchandler.quizit2;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Quiz_Selection extends AppCompatActivity {

    private Button startQuiz;
    private EditText quizCode;
    private EditText fName;
    private EditText lName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__selection);

        startQuiz = (Button) findViewById(R.id.LogIn);
        quizCode = (EditText) findViewById(R.id.quizCode);
        fName = (EditText) findViewById(R.id.FName);
        lName = (EditText) findViewById(R.id.LName);




        startQuiz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String codeForQuiz = quizCode.getText().toString();
                String firstName = fName.getText().toString();
                String lastName = lName.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(Quiz_Selection.this);
                builder.setMessage("You Are about to take quiz " + codeForQuiz + ".");
                builder.setNegativeButton("Start", null);
                builder.show();
}
});
   }
}