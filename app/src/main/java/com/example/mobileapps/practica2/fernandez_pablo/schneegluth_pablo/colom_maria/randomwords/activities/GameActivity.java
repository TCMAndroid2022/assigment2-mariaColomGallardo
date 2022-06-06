package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.view.PlayerViewModel;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.R;

import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    RequestQueue queue;
    TextView tv,tvFinalScore,tvFinalScoreNumber;
    String url = "https://random-word-api.herokuapp.com/word";
    String word;
    char [] wordArray;
    TextView [] tvArray;
    EditText et;
    float score;
    int lletresIntroduides;
    Button wholeWord,addLetter;
    private PlayerViewModel viewModel;

    @Override
    public void finish() {
        super.finish();
        Intent intent = new Intent();
        intent.putExtra("score", score);
        setResult(Activity.RESULT_OK);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        queue = Volley.newRequestQueue(getApplicationContext());
        linearLayout = findViewById(R.id.lyWord);
        et = findViewById(R.id.etLetter);
        tv = findViewById(R.id.txWord);
        wholeWord = findViewById(R.id.buttonWholeWord);
        addLetter = findViewById(R.id.btnAdd);
        tvFinalScore = findViewById(R.id.tvFinalScore);
        tvFinalScoreNumber = findViewById(R.id.tvFinalScoreNumber);

        score = 0.0F;
        tvFinalScore.setText("");
        tvFinalScoreNumber.setText("");
        lletresIntroduides = 0;
        word = "";
        getWord();

        wholeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et.getText().toString();
                checkGame(text.toUpperCase(Locale.ROOT));
            }
        });

        addLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et.getText().toString();
                if(text.length() != 1) {
                    triggerLetterLengthError();
                    et.setText("");
                } else {
                    char c = Character.toUpperCase(text.charAt(0));
                    if (word.contains(String.valueOf(c))) updateGame(c);
                    else {
                        triggerLetterDoesntExist();
                        et.setText("");
                    }
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        openDialogOnCancel();
        return true;
    }

    private void getWord() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        word = response.substring(2,response.length()-2).toUpperCase(Locale.ROOT);
                        createGame();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        triggerError();
                    }
                }
        );
        queue.add(stringRequest);
    }

    private void checkGame(String tryWord) {
        if (tryWord!= null && word.equals(tryWord)) {
            revealWord();
        } else {
            hideSoftKeyboard();
            openDialogOnWinLose("lose");
        }
    }

    private void revealWord() {
        for (int i = 0; i < tvArray.length; i++) {
            tvArray[i].setText(wordArray[i]+" ");
        }
        float len = word.length();
        score = Math.round((((len-(float)lletresIntroduides)/len)*10F)*100F)/100F;

        et.setText("");
        hideSoftKeyboard();
        openDialogOnWinLose("win");
    }

    private void createGame() {
        wordArray = word.toCharArray();
        tvArray = new TextView[word.length()];
        for (int i=0; i < word.length(); i++) {
            TextView textView = new TextView(this);
            textView.setText("_ ");
            tvArray[i] = textView;
            linearLayout.addView(textView);
        }
        tv.setText(word);
    }

    private void updateGame (char c) {
        for (int i = 0; i < tvArray.length; i++) {
            if(wordArray[i]==c) tvArray[i].setText(c+" ");
        }
        lletresIntroduides++;
        et.setText("");
    }


    public void openDialogOnCancel() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.strOnCancel);
        alert.setCancelable(false);

        alert.setPositiveButton(R.string.strYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        alert.setNegativeButton(R.string.strNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alert.create().show();
    }

    public void openDialogOnWinLose(String status) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        String message = "";
        if (status.equals("win"))
            message = getString(R.string.strFinalScore) + " " + String.valueOf(score);
        else
            message = getString(R.string.strLose);
        alert.setMessage(message);
        alert.setCancelable(false);

        alert.setNeutralButton(R.string.strGoBack, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.putExtra("score",score);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        alert.create().show();
    }

    public void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void triggerError() {
        hideSoftKeyboard();
        Toast.makeText(this,R.string.strErrorAPI,Toast.LENGTH_LONG).show();
    }

    private void triggerLetterLengthError() {
        hideSoftKeyboard();
        Toast.makeText(this,R.string.strErrorCharacter,Toast.LENGTH_LONG).show();
    }

    private void triggerLetterDoesntExist() {
        hideSoftKeyboard();
        Toast.makeText(this,R.string.strLetterNotCorrect,Toast.LENGTH_LONG).show();
    }
}
