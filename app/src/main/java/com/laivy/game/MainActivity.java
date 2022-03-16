package com.laivy.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int[] CAT_RES_IDS = new int[]{
            R.mipmap.cat_1,
            R.mipmap.cat_2,
            R.mipmap.cat_3,
            R.mipmap.cat_4,
            R.mipmap.cat_5
    };
    private int pageNumber;
    private TextView pageTextView;
    private ImageView catImageView;
    private ImageButton prevBtn, nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pageTextView = findViewById(R.id.pageTextView);
        catImageView = findViewById(R.id.catImageView);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);

        // 저장된 페이지 로드
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        pageNumber = sharedPref.getInt("PAGE_NUMBER", 1);
        setPage(pageNumber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 현재 페이지를 저장
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("PAGE_NUMBER", pageNumber);
        editor.apply();
    }

    public void onPrevBtnClick(View view) {
        setPage(pageNumber - 1);
    }

    public void onNextBtnClick(View view) {
        setPage(pageNumber + 1);
    }

    private void setPage(int page) {
        if (page < 1 || page > 5)
            return;

        pageNumber = page;

        String text = pageNumber + " / 5";
        pageTextView.setText(text);
        catImageView.setImageResource(CAT_RES_IDS[pageNumber - 1]);

        // 1, 5페이지일 때는 버튼 비활성화
        prevBtn.setEnabled(pageNumber != 1);
        nextBtn.setEnabled(pageNumber != 5);
    }
}