package ru.nsu.ccfit.tabakov.artchallenge;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Константин on 18.04.2015.
 */
public class GameActivity  extends Activity implements View.OnClickListener {
    private Button buttonPainter_1;
    private Button buttonPainter_2;
    private Button buttonPainter_3;
    private Button buttonPainter_4;

    private ImageView imageViewPictyre;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        initComponents();
        setListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPainter_1: {
                checkTruePainter(1);
                break;
            }
            case R.id.buttonPainter_2: {
                checkTruePainter(2);
                break;
            }
            case R.id.buttonPainter_3: {
                checkTruePainter(2);
                break;
            }
            case R.id.buttonPainter_4: {
                checkTruePainter(2);
                break;
            }
        }

    }

    private void initComponents() {
        buttonPainter_1 = (Button) findViewById(R.id.buttonPainter_1);
        buttonPainter_2 = (Button) findViewById(R.id.buttonPainter_2);
        buttonPainter_3 = (Button) findViewById(R.id.buttonPainter_3);
        buttonPainter_4 = (Button) findViewById(R.id.buttonPainter_4);

        imageViewPictyre = (ImageView) findViewById(R.id.imageViewmPictyre);
    }

    private void setListener(View.OnClickListener listener) {
        buttonPainter_1.setOnClickListener(listener);
        buttonPainter_2.setOnClickListener(listener);
        buttonPainter_3.setOnClickListener(listener);
        buttonPainter_4.setOnClickListener(listener);

    }

    private void checkTruePainter(int numAnswer) {
        int numTruePaintet = 1;
        if(numAnswer == numTruePaintet) {

        }

        else {

        }

    }

}
