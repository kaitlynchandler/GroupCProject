package com.example.kaitlynchandler.quizit2;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class student extends AppCompatActivity {

    private Button takeAQuiz;
    private Button viewMyGrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        takeAQuiz = (Button) findViewById(R.id.take_a_quiz);
        viewMyGrades = (Button) findViewById(R.id.viewGrades);

        TextView appTittle = (TextView) findViewById(R.id.title);
        Typeface pacifico = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");
        appTittle.setTypeface(pacifico);

        takeAQuiz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(student.this, MainActivity.class);
                student.this.startActivity(intent);
            }
        });

    }
}