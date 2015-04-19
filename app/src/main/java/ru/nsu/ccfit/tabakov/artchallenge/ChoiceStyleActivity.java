package ru.nsu.ccfit.tabakov.artchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Константин on 19.04.2015.
 */
public class ChoiceStyleActivity extends Activity implements View.OnClickListener {
    private static final String BASE_URL = "http://artchallenge.me";
    private IPainterApi restApi;

    private Translation translation;

    private ArrayList<ImageButton> setImageButton = new ArrayList<ImageButton>();
    private ArrayList<TextView> setTextView = new ArrayList<TextView>();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_1: {
                Intent intent =  new Intent(this, WinActivity.class);
                intent.putExtra("Translation", translation);
                startActivity(intent);
                break;
            }
            case R.id.imageButton_2: {
                break;
            }
            case R.id.imageButton_3: {
                break;
            }
            case R.id.imageButton_4: {
                break;
            }
            case R.id.imageButton_5: {
                break;
            }
            case R.id.imageButton_6: {
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choce_style);
        initComponents();
        setListener(this);

        String locale = Locale.getDefault().getLanguage();
        restApi.getTranslation(locale, new Callback<Translation>() {
            @Override
            public void success(Translation translation, Response response) {
                setText(translation);
                setTranslation(translation);

            }
            @Override
            public void failure(RetrofitError error) {
                Log.e("ArtChallengeError", error.getMessage());
            }
        });
        Log.i("ArtChallengeInfo", "Game start. " + "Language game: " + locale);
    }

    private void initComponents() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();
        restApi = restAdapter.create(IPainterApi.class);

        setImageButton.add((ImageButton) findViewById(R.id.imageButton_1));
        setImageButton.add((ImageButton) findViewById(R.id.imageButton_2));
        setImageButton.add((ImageButton) findViewById(R.id.imageButton_3));
        setImageButton.add((ImageButton) findViewById(R.id.imageButton_4));
        setImageButton.add((ImageButton) findViewById(R.id.imageButton_5));
        setImageButton.add((ImageButton) findViewById(R.id.imageButton_6));

        setTextView.add((TextView) findViewById(R.id.textView_1));
        setTextView.add((TextView) findViewById(R.id.textView_2));
        setTextView.add((TextView) findViewById(R.id.textView_3));
        setTextView.add((TextView) findViewById(R.id.textView_4));
        setTextView.add((TextView) findViewById(R.id.textView_5));
        setTextView.add((TextView) findViewById(R.id.textView_6));

    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    private void setListener(View.OnClickListener listener) {
        for(ImageButton imageButton : setImageButton) {
            imageButton.setOnClickListener(listener);
        }
    }

    private void setText(Translation translation) {

        for(TextView textView : setTextView) {
            textView.setText("dsf");
        }
//        setTextView.get(1).setText("asdfs");
////        setTextView.get(5).setText("asdfs");
//        setTextView.get(0).setText("asdfs");
////
//        setTextView.get(2).setText("asdfs");
//
//        setTextView.get(3).setText("asdfs");
//
//        setTextView.get(4).setText("asdfs");
//
//
    }
}
