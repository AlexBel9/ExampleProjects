package space.belyaev.territorycapture;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Level1 extends AppCompatActivity {

    List<TextView> viewList = new ArrayList<>();
    int[][] arrayBtns = new int[8][8];
    Drawable drawableBlue;
    Drawable drawableRed;
    Drawable drawableNorm;
    boolean checkColor = false;
    private int pointsOne = 0;
    private int pointTwo = 0;
    private TextView pointsPlayerTwo;
    private TextView pointsPlayerOne;
    private TextView winner;
    private TextView restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activuty_level1);

        drawableBlue = getDrawable(R.drawable.blue_btn_press);
        drawableRed = getDrawable(R.drawable.red_btn_press);
        drawableNorm = getDrawable(R.drawable.shape_btn);

        pointsPlayerOne = findViewById(R.id.pointPlayer1);
        pointsPlayerTwo = findViewById(R.id.pointPlayer2);
        winner = findViewById(R.id.winner);
        restart = findViewById(R.id.restartLevel1);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level1.this, Level1.class);
                startActivity(i);
                finish();
            }
        });


        int count = 1;
        for (int i = 1; i < 7; i++) {
            for (int t = 1; t < 7; t++) {
                String name = "btn";
                int b = this.getResources().getIdentifier(name + count, "id", getPackageName());
                arrayBtns[i][t] = b;
                TextView textView = findViewById(b);
                textView.setBackground(drawableNorm);
                viewList.add(textView);
                count++;
            }
        }

        pointsPlayerOne.setText(String.valueOf(pointsOne));
        pointsPlayerTwo.setText(String.valueOf(pointTwo));

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        winner.setText("ходит игрок 1");
        winner.setBackground(drawableRed);

        for (TextView view : viewList) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (view.getBackground().equals(drawableNorm) && checkColor == false) {
                        view.setBackground(drawableRed);
                        for (int i = 1; i < 7; i++) {
                            for (int t = 1; t < 7; t++) {
                                if (arrayBtns[i][t] == view.getId()) {
                                    for (TextView view1 : viewList) {
                                        try {
                                            if (view1.getId() == arrayBtns[i - 1][t - 1] && view1.getBackground().equals(drawableBlue)) {
                                                view1.setBackground(drawableRed);
                                            }
                                            if (view1.getId() == arrayBtns[i - 1][t] && view1.getBackground().equals(drawableBlue)) {
                                                view1.setBackground(drawableRed);
                                            }

                                            if (view1.getId() == arrayBtns[i - 1][t + 1] && view1.getBackground().equals(drawableBlue)) {
                                                view1.setBackground(drawableRed);
                                            }

                                            if (view1.getId() == arrayBtns[i][t - 1] && view1.getBackground().equals(drawableBlue)) {
                                                view1.setBackground(drawableRed);
                                            }

                                            if (view1.getId() == arrayBtns[i][t + 1] && view1.getBackground().equals(drawableBlue)) {
                                                view1.setBackground(drawableRed);
                                            }

                                            if (view1.getId() == arrayBtns[i + 1][t - 1] && view1.getBackground().equals(drawableBlue)) {
                                                view1.setBackground(drawableRed);
                                            }
                                            if (view1.getId() == arrayBtns[i + 1][t] && view1.getBackground().equals(drawableBlue)) {
                                                view1.setBackground(drawableRed);
                                            }
                                            if (view1.getId() == arrayBtns[i + 1][t + 1] && view1.getBackground().equals(drawableBlue)) {
                                                view1.setBackground(drawableRed);
                                            }
                                        } catch (Exception e) {
                                            continue;
                                        }

                                    }
                                }
                            }
                        }
                        checkPoints();
                        checkColor = true;
                    }
                    if (view.getBackground().equals(drawableNorm) && checkColor == true) {
                        view.setBackground(drawableBlue);
                        for (int i = 1; i < 7; i++) {
                            for (int t = 1; t < 7; t++) {
                                if (arrayBtns[i][t] == view.getId()) {
                                    for (TextView view1 : viewList) {
                                        try {
                                            if (view1.getId() == arrayBtns[i - 1][t - 1] && view1.getBackground().equals(drawableRed)) {
                                                view1.setBackground(drawableBlue);
                                            }
                                            if (view1.getId() == arrayBtns[i - 1][t] && view1.getBackground().equals(drawableRed)) {
                                                view1.setBackground(drawableBlue);
                                            }

                                            if (view1.getId() == arrayBtns[i - 1][t + 1] && view1.getBackground().equals(drawableRed)) {
                                                view1.setBackground(drawableBlue);
                                            }

                                            if (view1.getId() == arrayBtns[i][t - 1] && view1.getBackground().equals(drawableRed)) {
                                                view1.setBackground(drawableBlue);
                                            }

                                            if (view1.getId() == arrayBtns[i][t + 1] && view1.getBackground().equals(drawableRed)) {
                                                view1.setBackground(drawableBlue);
                                            }

                                            if (view1.getId() == arrayBtns[i + 1][t - 1] && view1.getBackground().equals(drawableRed)) {
                                                view1.setBackground(drawableBlue);
                                            }
                                            if (view1.getId() == arrayBtns[i + 1][t] && view1.getBackground().equals(drawableRed)) {
                                                view1.setBackground(drawableBlue);
                                            }
                                            if (view1.getId() == arrayBtns[i + 1][t + 1] && view1.getBackground().equals(drawableRed)) {
                                                view1.setBackground(drawableBlue);
                                            }
                                        } catch (Exception e) {
                                            continue;
                                        }

                                    }
                                }
                            }
                        }
                        checkPoints();
                        checkColor = false;
                    }
                }
            });
        }
    }


    @SuppressLint("ResourceAsColor")
    private void checkPoints() {
        if (checkColor) {
            winner.setText("ходит игрок 1");
            winner.setBackground(drawableRed);
        } else {
            winner.setText("ходит игрок 2");
            winner.setBackground(drawableBlue);
        }
        byte norm = 0;
        for (TextView viewCount : viewList) {
            if (viewCount.getBackground().equals(drawableRed)) {
                pointsOne++;
            }
            if (viewCount.getBackground().equals(drawableBlue)) {
                pointTwo++;
            }
            if (viewCount.getBackground().equals(drawableNorm)) {
                norm++;
            }
        }
        if (norm == 0) {
            if (pointTwo > pointsOne) {
                winner.setText("выиграл игрок 2!");
                winner.setBackground(drawableBlue);
            }
            if (pointsOne > pointTwo) {
                winner.setText("выиграл игрок 1");
                winner.setBackground(drawableRed);
            }
            if (pointsOne == pointTwo) {
                winner.setText("ничья!");
                winner.setTextColor(R.color.teal_200);
            }
        }
        pointsPlayerOne.setText(String.valueOf(pointsOne));
        pointsPlayerTwo.setText(String.valueOf(pointTwo));
        pointsOne = 0;
        pointTwo = 0;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}