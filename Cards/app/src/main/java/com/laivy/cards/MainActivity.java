package com.laivy.cards;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] CARD_IDS = new int[]{
            R.id.card_00, R.id.card_01, R.id.card_02, R.id.card_03,
            R.id.card_10, R.id.card_11, R.id.card_12, R.id.card_13,
            R.id.card_20, R.id.card_21, R.id.card_22, R.id.card_23,
            R.id.card_30, R.id.card_31, R.id.card_32, R.id.card_33
    };
    private int[] resIds = new int[]{
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd
    };
    private ImageButton prevClickedCardBtn;
    private TextView scoreTextView;
    private int flips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreTextView = findViewById(R.id.scoreTextView);

        startGame();
    }

    private void startGame() {
        for (int i = 0; i < CARD_IDS.length; ++i) {
            int resId = resIds[i];
            ImageButton btn = findViewById(CARD_IDS[i]);
            btn.setTag(resId);
        }
        setScore(0);
    }

    public void onBtnClickedRestart(View view) {
        Log.d(TAG, "onBtnClicked!");
        askRetry();
    }

    private void askRetry() {
        // 빌더 패턴을 사용할때는 아래와 같이 사용한다.
        new AlertDialog.Builder(this)
                .setTitle("Restart")
                .setMessage("Do you want to restart game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startGame();
                    }
                })
                .setNegativeButton("No", null)
                .create()
                .show();
    }

    public void onCardBtnClicked(View view) {
        if (!(view instanceof ImageButton)) {
            Log.e(TAG, "View is not ImageButton!");
            return;
        }

        ImageButton imageButton = (ImageButton) view;

        // 방금 선택한 버튼과 같은 버튼이라면 무시
        if (imageButton == prevClickedCardBtn) {
            Log.v(TAG, "Ignore!");
            return;
        }

        // 선택한 카드의 인덱스를 가져옴
        int cardBtnIndex = FindCardBtnIndex(imageButton.getId());
        Log.d(TAG, "onCardBtnClicked! " + cardBtnIndex);

        // 이전 버튼의 모양을 가져옴
        int prevResId = 0;
        if (prevClickedCardBtn != null) {
            prevResId = (Integer) prevClickedCardBtn.getTag();
        }
        int resId = (Integer) imageButton.getTag();

        // 서로 다른 카드를 선택했다면
        if (prevResId != resId) {
            imageButton.setImageResource(resId);

            // 이전의 카드를 다시 뒷면으로 설정
            if (prevClickedCardBtn != null) {
                prevClickedCardBtn.setImageResource(R.mipmap.card_blue_back);
            }

            // 방금 선택한 버튼을 저장
            prevClickedCardBtn = imageButton;
            setScore(flips + 1);
        } else {
            imageButton.setVisibility(View.INVISIBLE);
            prevClickedCardBtn.setVisibility(View.INVISIBLE);
            prevClickedCardBtn = null;
        }
    }

    private void setScore(int flips) {
        this.flips = flips;
        scoreTextView.setText("Flips: " + this.flips);
    }

    private int FindCardBtnIndex(int id) {
        for (int i = 0; i < CARD_IDS.length; ++i) {
            if (id == CARD_IDS[i])
                return i;
        }
        Log.e(TAG, "Can't find Card Button Index!");
        return -1;
    }
}