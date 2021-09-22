package com.finesaaa.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText mainEtBil1, mainEtBil2;
    TextView mainTvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    protected void setupView() {
        mainEtBil1 = findViewById(R.id.main_et_bil_1);
        mainEtBil2 = findViewById(R.id.main_et_bil_2);
        mainTvHasil = findViewById(R.id.main_tv_hasil);
    }

    public void calculate(View v) {
        float bil1, bil2, hasil = 0;
        Button button;

        bil1 = Float.parseFloat(mainEtBil1.getText().toString());
        bil2 = Float.parseFloat(mainEtBil2.getText().toString());

        switch (v.getId()) {
            case R.id.main_btn_tambah:
                hasil = bil1 + bil2;
                break;
            case R.id.main_btn_kurang:
                hasil = bil1 - bil2;
                break;
            case R.id.main_btn_kali:
                hasil = bil1 * bil2;
                break;
            case R.id.main_btn_bagi:
                hasil = bil1 / bil2;
                break;
        }
        button = findViewById(v.getId());
        mainTvHasil.setText(bil1 + " " + button.getText() + " " + bil2 + " = " + hasil);
    }
}