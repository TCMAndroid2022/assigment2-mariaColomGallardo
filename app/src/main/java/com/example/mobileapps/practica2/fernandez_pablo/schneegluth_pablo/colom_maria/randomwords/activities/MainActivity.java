package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.database.AppDatabase;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Player;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.fragment.PlayerDialogFragment;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.view.PlayerViewModel;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.R;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Score;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.view.ScoreViewModel;

public class MainActivity extends AppCompatActivity implements PlayerDialogFragment.IPlayerDialogListener {

    private TextView playerName;
    private Player player;
    private PlayerViewModel playerViewModel;
    private ScoreViewModel scoreViewModel;
    private Button btnNewPlayer;
    private Button btnPlay;

    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerName = findViewById(R.id.tvPlayer);
        btnNewPlayer = findViewById(R.id.buttonNewPlayer);
        btnPlay = findViewById(R.id.buttonPlay);

        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        scoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);

        btnNewPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchPlayerDialogFragment();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerName == null || playerName.getText()=="") {
                    launchPlayerDialogFragment();
                }
                else {
                    Intent intent = new Intent(view.getContext(), GameActivity.class);
                    launcher.launch(intent);
                }
            }
        });

        launcher = registerForActivityResult(new
            ActivityResultContracts.StartActivityForResult(), new
            ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        float score = result.getData().getFloatExtra("score", 0);
                        String nickname = playerName.getText().toString();
                        float preScore = playerViewModel.getScore(nickname);
                        Log.v("prescore", String.valueOf(preScore));
                        playerViewModel.updateScore(nickname, score+preScore);
                        int games = playerViewModel.getGamesPlayed(nickname);
                        Log.v("games", String.valueOf(games));
                        playerViewModel.updatePlayed(playerName.getText().toString(),games+1);
                        scoreViewModel.insertScore(new Score(nickname,score));
                    } else {
                        Log.v("Alert","An error occurred");
                    }
                }
            });
    }

    private void launchPlayerDialogFragment() {
        PlayerDialogFragment dialog = new PlayerDialogFragment();
        dialog.show(getSupportFragmentManager(), "playerName");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ranking) {
            Intent intent = new Intent(getApplicationContext(), RankingActivity.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendInput(String input) {
        player = playerViewModel.getUser(input).getValue();
        if (player == null) {
            player = new Player(input);
            playerViewModel.insertPlayer(player);
        }
        playerName.setText(player.getNickname());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppDatabase.destroyInstance();
    }
}