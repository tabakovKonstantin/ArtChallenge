package ru.nsu.ccfit.tabakov.artchallenge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Константин on 18.04.2015.
 */
public class GameActivity  extends Activity implements View.OnClickListener {
    private static final String BASE_URL = "http://artchallenge.me";
    private static final int MAX_ANSWER = 10;

    private int countTrueAnswer = 0;
    private int numButtonTrueAnswer;
    private int idPainter;
    private String picURl;

    private ProgressBar progressBar;
    private ImageView imageViewPicture;
    private ArrayList<Button> setButtons = new ArrayList<Button>();

    private IPainterApi restApi;
    private Translation translation;
    private ArrayList<Integer> setPainters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        initComponents();
        setListener(this);
        startGame();
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
                checkTruePainter(3);
                break;
            }
            case R.id.buttonPainter_4: {
                checkTruePainter(4);
                break;
            }
            case R.id.imageViewmPicture: {
                break;
            }
        }

    }

    private void initComponents() {

        setTranslation((Translation) getIntent().getSerializableExtra("Translation"));
        setPainters = (ArrayList<Integer>) getIntent().getSerializableExtra("setPainters");

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();
        restApi = restAdapter.create(IPainterApi.class);

        setButtons.add((Button) findViewById(R.id.buttonPainter_1));
        setButtons.add((Button) findViewById(R.id.buttonPainter_2));
        setButtons.add((Button) findViewById(R.id.buttonPainter_3));
        setButtons.add((Button) findViewById(R.id.buttonPainter_4));

        imageViewPicture = (ImageView) findViewById(R.id.imageViewmPicture);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(MAX_ANSWER);
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    private void setListener(View.OnClickListener listener) {
        for(Button button : setButtons) {
            button.setOnClickListener(listener);
        }
        imageViewPicture.setOnClickListener(listener);
    }

    private void setClickable(boolean enableButton) {
        for(Button button : setButtons) {
            button.setClickable(enableButton);
        }
    }

    private void setButtonColorText(int numButtonTrueAnswer, int numButtonFalseAnswer) {
        if(numButtonTrueAnswer == numButtonFalseAnswer) {
            setButtons.get(numButtonTrueAnswer - 1).setTextColor(Color.GREEN);
        } else {
            setButtons.get(numButtonTrueAnswer - 1).setTextColor(Color.GREEN);
            setButtons.get(numButtonFalseAnswer - 1).setTextColor(Color.RED);
        }
    }

    private void resetButtonColorText() {
        for(Button button : setButtons) {
            button.setTextColor(Color.BLACK);
        }
    }

    private String createUrlForPicture(int idPainter, int pictureQuantity) {
        int numPicture = RandomMath.randomInt(1, pictureQuantity);
        return BASE_URL
                .concat("/painters/")
                .concat(String.valueOf(idPainter))
                .concat("/thumbnails/")
                .concat(String.valueOf(numPicture))
                .concat(".jpg");
    }

    private void startGame() {
        setClickable(false);
        setButtonText(translation);
        setNewPicture(idPainter);
    }

    private void checkTruePainter(int numButtonAnswer) {
        if (numButtonAnswer == numButtonTrueAnswer) {
            countTrueAnswer ++;
            Toast.makeText(this, translation.getRandGoodPhrases(), Toast.LENGTH_SHORT).show();
            Log.i("ArtChallengeGame", "It's true answer");
        } else {
            countTrueAnswer = 0;
            Toast.makeText(this, translation.getRandBadPhrases(), Toast.LENGTH_SHORT).show();
            Log.i("ArtChallengeGame", "It's false answer");
        }

        progressBar.setProgress(countTrueAnswer);
        if(countTrueAnswer != MAX_ANSWER) {
            setButtonColorText(numButtonTrueAnswer, numButtonAnswer);
            setButtonText(translation);
            setNewPicture(idPainter);
        } else {
            countTrueAnswer = 0;
            startActivity(new Intent(this, WinActivity.class ));
            Log.i("ArtChallengeGame", "You are WIN!!!");
        }

    }

    private void setButtonText(Translation translation) {

        ArrayList<Integer> uniqSet = RandomMath.randomSet(1, setPainters.size(), setButtons.size());
        numButtonTrueAnswer = RandomMath.randomInt(1, 4);
        idPainter = uniqSet.get(numButtonTrueAnswer - 1);

        for (int i = 0; i < uniqSet.size(); i++) {
            String textButton = translation.getPainters().get(uniqSet.get(i));
            setButtons.get(i).setText(textButton);
        }
        Log.i("ArtChallengeAnswer", "True answer: " + translation.getPainters().get(idPainter)
                + "; button number with the correct answer: " + numButtonTrueAnswer);
    }

    private void setNewPicture(final int idPainter) {
        restApi.getPainter(idPainter, new Callback<Painter>() {
            @Override
            public void success(Painter painter, Response response) {
                int pictureQuantity = painter.getPictureQuantity();
                Log.i("ArtChallengeInfo", "Download info about painter");
                Log.i("ArtChallengeInfo", "ID painder: " + String.valueOf(idPainter)
                        + "; Name painter: " + translation.getPainters().get(idPainter)
                        + "; Quantity of paintings from the painters: " + String.valueOf(pictureQuantity));
                picURl = createUrlForPicture(idPainter, pictureQuantity);
                Picasso.with(GameActivity.this)
                        .load(picURl)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .networkPolicy( NetworkPolicy.NO_STORE)
                        .into(imageViewPicture, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                resetButtonColorText();
                                setClickable(true);
                                Log.i("ArtChallengeInfo", "Download picture url: " + picURl);
                            }

                            @Override
                            public void onError() {
                                Log.i("ArtChallengeError", "Error download picture");
                            }
                        });

            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("ArtChallengeError", error.getMessage());
            }
        });
    }
}
