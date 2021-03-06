package com.seven.boom.collection.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.boom.collection.R;
import com.seven.boom.collection.slotsGame.Common;
import com.seven.boom.collection.slotsGame.imageViewScrolling.IEventEnd;
import com.seven.boom.collection.slotsGame.imageViewScrolling.ImageViewScrolling;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.FullscreenPromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

public class SlotsActivity extends AppCompatActivity implements IEventEnd {

    Button btn_down;
    ImageViewScrolling image1, image2, image3;
    TextView txt_score;
    ImageView mImgJackpot;

    int count_done = 0;
    public int klo;

    MediaPlayer mp;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slots);
        initView();

        mp = MediaPlayer.create(this, R.raw.music);
        mp.setLooping(true);
        mp.start();


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        klo = intent.getIntExtra("cloaka", 0);

        showBalansPromt(txt_score);

        image1.setEventEnd(this);
        image2.setEventEnd(this);
        image3.setEventEnd(this);

        if (klo == 1) {

            // ?????? ???????????????????? ???????? ?????? ?????????????????? ????????????????????????

            btn_down.setOnClickListener(v -> {

                btn_down.setEnabled(false);
                btn_down.setClickable(false);

                Completable.timer(2000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                btn_down.setEnabled(true);
                                btn_down.setClickable(true);
                                Log.d("TAG", "???????????? ????????????????");
                            }

                            @Override
                            public void onError(@NotNull Throwable e) {
                                e.printStackTrace();
                            }
                        });

                if (Common.SCORE >= 50) {

                    // ?????? ?????? ?????????? ?????????????? ???????????????????? ????????????????
                    image1.setValueRandom(4,
                            new Random().nextInt((8 - 5) + 1) + 5);

                    image2.setValueRandom(4,
                            new Random().nextInt((8 - 5) + 1) + 5);

                    image3.setValueRandom(4,
                            new Random().nextInt((8 - 5) + 1) + 5);

                    Common.SCORE -= 50;

                    txt_score.setText("?????? ???????? : " + Common.SCORE);

                } else {
                    Toast.makeText(this, "You not enough money", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            btn_down.setOnClickListener(v -> {

                btn_down.setEnabled(false);
                btn_down.setClickable(false);

                Completable.timer(2000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                btn_down.setEnabled(true);
                                btn_down.setClickable(true);
                                Log.d("TAG", "???????????? ????????????????");
                            }

                            @Override
                            public void onError(@NotNull Throwable e) {
                                e.printStackTrace();
                            }
                        });

                if (Common.SCORE >= 50) {

                    // ?????? ?????? ?????????? ?????????????? ???????????????????? ????????????????
                    image1.setValueRandom(new Random().nextInt(6),
                            new Random().nextInt((8 - 5) + 1) + 5);

                    image2.setValueRandom(new Random().nextInt(6),
                            new Random().nextInt((8 - 5) + 1) + 5);

                    image3.setValueRandom(new Random().nextInt(6),
                            new Random().nextInt((8 - 5) + 1) + 5);

                    Common.SCORE -= 25;

                    txt_score.setText("?????? ???????? : " + Common.SCORE);

                } else {
                    Toast.makeText(this, "You not enough money", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mp.stop();
    }

    public void showBalansPromt(View view) {
        new MaterialTapTargetPrompt.Builder(this)
                .setTarget(view)
                .setPrimaryText("?????? ????????????")
                .setSecondaryText("?????????????????? ???????????? ?????????????? ?????????? ???????????????????? ?? ???????????????????????? ???????? ????????????")
                .setPromptBackground(new FullscreenPromptBackground())
                .setPromptFocal(new RectanglePromptFocal())
                .setPromptStateChangeListener((prompt, state) -> {
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                    {
                        showButtonPromt(btn_down);
                    } else if(state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {
                        showButtonPromt(btn_down);
                    }
                })
                .show();
    }

    public void showButtonPromt(View view) {
        new MaterialTapTargetPrompt.Builder(this)
                .setTarget(view)
                .setPrimaryText("???????????? ??????????????")
                .setSecondaryText("???????????? ?????????????? ???????????? ?????????????? ?????????? 25 ?????????? ???? ???????????? ??????????????")
                .setPromptBackground(new FullscreenPromptBackground())
                .setPromptFocal(new RectanglePromptFocal())
                .show();
    }

    private void initView() {
        btn_down = findViewById(R.id.btn_down);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        txt_score = findViewById(R.id.txt_score);

        mImgJackpot = findViewById(R.id.img_jackpot);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void eventEnd(int result, int count) {

        if (klo == 1) {

            mImgJackpot.setVisibility(View.VISIBLE);
            btn_down.setVisibility(View.GONE);

            Completable.timer(2000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {
                            startActivity(new Intent(SlotsActivity.this, AuthActivity.class));
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            e.printStackTrace();
                        }
                    });

        } else {

            if (count_done < 2) {
                count_done++;
            } else {

                count_done = 0;

                if (image1.getValue() == image2.getValue() && image2.getValue() == image3.getValue()) {

                    Toast.makeText(this, "You win big prize", Toast.LENGTH_SHORT).show();
                    Common.SCORE += 10000;

                    txt_score.setText("?????? ???????? : " + Common.SCORE);
                }
            }
        }
    }
}