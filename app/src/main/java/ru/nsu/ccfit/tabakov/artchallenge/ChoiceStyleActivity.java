package ru.nsu.ccfit.tabakov.artchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
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

    private ArrayList<Integer> basicSet = new ArrayList<Integer>(Arrays.asList(1,4,7,9,14,15,17,19,21,22,24,26,27,28,29,30,32,33,34,35,36,39,40,41,42,43,45,46,49,50,53,54,55,57,58,61,62,63,69,73,75,77,79,80,82,83,94,95,112,118));
    private ArrayList<Integer> renaissanceSet = new ArrayList<Integer>(Arrays.asList(24,35,39,41,42,45,50,55,87,89,90,91,92,95,98,100,101,104,106,108,110,111,112,114));
    private ArrayList<Integer> impressionismSet = new ArrayList<Integer>(Arrays.asList(2,3,9,16,17,21,30,36,49,53,57,60,61,69,77,84,94,96));
    private ArrayList<Integer> realismSet = new ArrayList<Integer>(Arrays.asList(5,8,18,25,37,47,48,58,85,113,116,117));
    private ArrayList<Integer> russianSet = new ArrayList<Integer>(Arrays.asList(3,4,5,6,8,10,11,12,13,16,19,20,23,25,26,27,31,37,38,44,47,48,76,81,84,85,86,103,105,107,109,113,115));
    private ArrayList<Integer> frenchSet = new ArrayList<Integer>(Arrays.asList(2,9,17,30,36,40,49,53,57,58,61,64,65,69,70,73,75,77,93,94,96,97));
    private ArrayList<Integer> allSet = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choce_style);
        initComponents();
        setListener(this);
        downloadTranslation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_1: {
                choice(realismSet, "realismSet");
                break;
            }
            case R.id.imageButton_2: {
                choice(impressionismSet, "impressionismSet");
                break;
            }
            case R.id.imageButton_3: {
                choice(renaissanceSet, "renaissanceSet");
                break;
            }
            case R.id.imageButton_4: {
                choice(basicSet, "basicSet");
                break;
            }
            case R.id.imageButton_5: {
                choice(russianSet, "russianSet");
                break;
            }
            case R.id.imageButton_6: {
                choice(frenchSet, "frenchSet");
                break;
            }
            case R.id.imageButton_7: {
                choice(allSet, "allSet");
                break;
            }
        }
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
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
        setImageButton.add((ImageButton) findViewById(R.id.imageButton_7));

        setTextView.add((TextView) findViewById(R.id.textView_1));
        setTextView.add((TextView) findViewById(R.id.textView_2));
        setTextView.add((TextView) findViewById(R.id.textView_3));
        setTextView.add((TextView) findViewById(R.id.textView_4));
        setTextView.add((TextView) findViewById(R.id.textView_5));
        setTextView.add((TextView) findViewById(R.id.textView_6));
        setTextView.add((TextView) findViewById(R.id.textView_7));

    }

    private void setListener(View.OnClickListener listener) {
        for(ImageButton imageButton : setImageButton) {
            imageButton.setOnClickListener(listener);
        }
    }

    private void setText(Translation translation) {
        int i = 0;
        String[] textArray = new String[21];
        translation.getSets().values().toArray(textArray);
        for(TextView textView : setTextView) {
            String text = textArray[i];
            textView.setText(text);
            i = i + 3;
        }
    }

    private void choice(ArrayList<Integer> setPainters, String nameSet) {
        Intent intent =  new Intent(this, GameActivity.class);
        intent.putExtra("setPainters", setPainters);
        intent.putExtra("Translation", translation);
        startActivity(intent);
        Log.i("ArtChallengeInfo", "Choice painters set: " + nameSet);
    }

    private void downloadTranslation() {
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

}
