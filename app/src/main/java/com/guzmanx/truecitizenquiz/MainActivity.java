package com.guzmanx.truecitizenquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

// After adding onClick(), now MainActivity class implements View.OnClickListener interface because
// we want to use the interface's onClick() behavior in our MainActivity class
// In Java, it doesn't allow multiple inheritance, so we use interface to bypass this limitation
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // reference button and text widgets
    private Button falseButton;
    private Button trueButton;
    private ImageButton nextButton;
    private ImageButton previousButton;
    private TextView questionTextView;

    private int currentQuestionIndex = 0;

    // Array contains instantiated objects of model Question class, initialized with string
    // resources and the correct answer associated
    private Question[] questionBank = new Question[] {
            new Question(R.string.question_amendments, false), // correct: 27
            new Question(R.string.question_declaration, true),
            new Question(R.string.question_constitution, true),
            new Question(R.string.question_independence_rights, true),
            new Question(R.string.question_religion, true),
            new Question(R.string.question_government, false),
            new Question(R.string.question_government_feds, false),
            new Question(R.string.question_government_senators, true) // add more later!
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect to button and text ids
        falseButton = findViewById(R.id.false_button);
        trueButton = findViewById(R.id.true_button);
        nextButton = findViewById(R.id.next_button);
        previousButton = findViewById(R.id.prev_button);
        questionTextView = findViewById(R.id.answer_text_view);

        // Two ways to attach on click listener to buttons
        // Approach 1 (Not Used): simplistic come to activity_main.xml buttons and add on click
        // Approach 2 (Used): more robust way in code call buttons setOnClickListener()
        // register our buttons to View.OnClickListener, so they can be listened in onClick()
        falseButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
    }

    // Override onClick() from View.OnClickListener, so we listen to clicks on false and true button
    @Override
    public void onClick(View v) {
        // Check view to see which button id was clicked (falseButton or trueButton)
        switch(v.getId()) {
            // Create toast message for button clicked
            case R.id.false_button:
                // User chose false button for answer to question, check is it correct
                checkAnswer(false);
                break;
            case R.id.true_button:
                // User chose true button for answer to question, check is it correct
                checkAnswer(true);
                break;
            case R.id.next_button:
                // go to next question, make sure once counter gets to end of length, go back to 0
                currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.length;
                updateQuestion();
                break;
            case R.id.prev_button:
                // go to prior question, make sure once dec count gets to first question, then stop
                if(currentQuestionIndex > 0) {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionBank.length;
                    updateQuestion();
                }
        }
    }

    private void updateQuestion() {
        Log.d("Current", "onClick: " + currentQuestionIndex);
        // Make sure we are within bounds of our question bank
        questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());
    }

    private void checkAnswer(boolean userChooseCorrect) {
        // Get actual answer from question bank at current question index
        boolean answerIsTrue = questionBank[currentQuestionIndex].isAnswerTrue();
        int toastMessageId = 0;

        // check whatever the answer the user chose with the correct answer from question bank
        if(userChooseCorrect == answerIsTrue) {
            // toast message id will point to correct answer
            toastMessageId = R.string.correct_answer;
        } else {
            toastMessageId = R.string.wrong_answer;
        }

        // Create a toast message displaying correct or wrong based on users chosen answer
        Toast.makeText(MainActivity.this, toastMessageId,
                Toast.LENGTH_SHORT)
                .show();

    }
}
