package com.example.barisflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        findViewById(R.id.myExitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.mySaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((EditText) findViewById(R.id.editTextField)).getText().toString();
                ((EditText) findViewById(R.id.editTextAnswer)).getText().toString();

                TextView question = findViewById(R.id.editTextField);
                TextView answer = findViewById(R.id.editTextAnswer);

                String questionString = question.getText().toString();
                String answerString = answer.getText().toString();

                Intent data = new Intent(); // create a new Intent, this is where we will put our data
                data.putExtra("string1",questionString);// puts one string into the Intent, with the key as 'string1'
                data.putExtra("string2", answerString); // puts another string into the Intent, with the key as 'string2
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes this activity and pass data to the original activity that launched this activity
            }
        });
    }
}