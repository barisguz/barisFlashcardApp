package com.example.barisflashcardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

        TextView questionTextView = findViewById(R.id.flashcard_question);
        TextView answerTextView = findViewById(R.id.flashcard_answer);

        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionTextView.setVisibility(View.INVISIBLE);
                answerTextView.setVisibility(View.VISIBLE);
            }
        });

        answerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionTextView.setVisibility(View.VISIBLE);
                answerTextView.setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.myButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent,100);
            }
        });

        findViewById(R.id.myNextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards.size() == 0)
                    return;

                currentCardDisplayedIndex++;

                if(currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(questionTextView,"You've reached the end of the cards, going back " +
                            "to start.", Snackbar.LENGTH_SHORT).show();
                    currentCardDisplayedIndex = 0;
                }

                allFlashcards = flashcardDatabase.getAllCards();
                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                ((TextView) findViewById(R.id.flashcard_question)).setText(flashcard.getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(flashcard.getAnswer());
            }
        });
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
    }

    List<Flashcard> allFlashcards;

    FlashcardDatabase flashcardDatabase;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String newQuestion = data.getExtras().getString("question"); // 'string1' needs to match the key we used when we put the string in the Intent
            String newAnswer = data.getExtras().getString("answer");

            ((TextView)findViewById(R.id.flashcard_question)).setText(newQuestion);
            ((TextView)findViewById(R.id.flashcard_answer)).setText(newAnswer);

            flashcardDatabase.insertCard(new Flashcard(newQuestion, newAnswer));
            allFlashcards = flashcardDatabase.getAllCards();
            //Log.i(TAG,newQuestion + " " + newAnswer);
            Snackbar.make(findViewById(R.id.flashcard_question),"Flashcard Successfully Added. ", Snackbar.LENGTH_SHORT).show();
        }
    }
}