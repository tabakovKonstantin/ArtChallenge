package ru.nsu.ccfit.tabakov.artchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by Константин on 19.04.2015.
 */
public class WinActivity extends Activity implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_repet: {
                startActivity(new Intent(this, ChoiceStyleActivity.class));
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win);
        Translation translation = (Translation)getIntent().getSerializableExtra("Translation");
        Log.i("START", "start" + translation.getSets().get("basicSet-desc"));

    }
}
