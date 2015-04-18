package ru.nsu.ccfit.tabakov.artchallenge;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static ru.nsu.ccfit.tabakov.artchallenge.R.color.orange;

/**
 * Created by Константин on 18.04.2015.
 */
public class GameActivity  extends Activity implements View.OnClickListener {
    private static final String BASE_URL = "http://artchallenge.me";
    private ArrayList<Button> setButton = new ArrayList<Button>();
    private int numTrueButton;
    private int idPainter;
    private ImageView imageViewPicture;
    private IPainterApi restApi;
    private Translation translation;
    private ArrayList<Integer> setPainters = new ArrayList<Integer>(Arrays.asList(2,3,9,16,17,21,30,36,49,53,57,60,61,69,77,84,94,96));



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        initComponents();
        setListener(this);
        setClickable(false);


        String locale = Locale.getDefault().getLanguage();
        Log.i("qwer", "locale " + locale);
        restApi.getTranslation(locale, new Callback<Translation>() {
            @Override
            public void success(Translation translation, Response response) {
                setButtonText(translation);
                setNewPicture(idPainter);
                setClickable(true);
                setTranslation(translation);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });



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
                Log.i("rewq", "dhdffgfhg");
                break;
            }
        }

    }

    private void initComponents() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();
        restApi = restAdapter.create(IPainterApi.class);

        setButton.add((Button) findViewById(R.id.buttonPainter_1));
        setButton.add((Button) findViewById(R.id.buttonPainter_2));
        setButton.add((Button) findViewById(R.id.buttonPainter_3));
        setButton.add((Button) findViewById(R.id.buttonPainter_4));

        imageViewPicture = (ImageView) findViewById(R.id.imageViewmPicture);

    }

    private void setListener(View.OnClickListener listener) {
        for(Button button : setButton) {
            button.setOnClickListener(listener);
        }
        imageViewPicture.setOnClickListener(listener);
    }

    private void setClickable(boolean enableButton) {
        for(Button button : setButton) {
            button.setClickable(enableButton);
        }
    }

    private void checkTruePainter(int numAnswer) {
        if (numAnswer == numTrueButton) {
            Log.i("qwer", "Выбран правильный ответ");
            Toast.makeText(this, getGoodText(), Toast.LENGTH_SHORT).show();

            //добавить зеленую звездочку
        } else {
            Log.i("qwer", "Выбран не правильный ответ");
            Toast.makeText(this, getBadText(), Toast.LENGTH_SHORT).show();
            //добавить красную звездочку
//            ratingBar.setNumStars(1);
        }
        setBackgroundColor(numTrueButton, numAnswer);

        setButtonText(translation);
        setNewPicture(idPainter);
        Log.i("qwer", "Художник " + String.valueOf(idPainter));
    }

    private void setBackgroundColor(int trueAnswerNum, int falseAnswerNum) {
        if(trueAnswerNum == falseAnswerNum) {
            setButton.get(trueAnswerNum - 1).setTextColor(Color.BLUE);
        } else {
            setButton.get(trueAnswerNum - 1).setTextColor(Color.BLUE);
            setButton.get(falseAnswerNum-1).setTextColor(Color.RED);
        }
    }

    private void resetBackgroundColor() {
        for(Button button : setButton) {
            button.setTextColor(Color.BLACK);
        }
        Log.i("rewq","vizov");
    }

    private String getGoodText() {
        Map<Integer, String> goodTextMap = translation.getGoodPhrases();
        int numText = randomInt(1, goodTextMap.size());
        return goodTextMap.get(numText);
    }

    private String getBadText() {
        Map<Integer, String> badTextMap = translation.getBadPhrases();
        int numText = randomInt(1, badTextMap.size());
        return badTextMap.get(numText);
    }


    private void setButtonText(Translation translation) {

        ArrayList<Integer> uniqSet = randomSet(1, setPainters.size(), setButton.size());
        numTrueButton = randomInt(1, 4);
        idPainter = uniqSet.get(numTrueButton-1);

        for (int i = 0; i < uniqSet.size(); i++) {
            String textButton = translation.getPainters().get(uniqSet.get(i));
            setButton.get(i).setText(textButton);
        }
        Log.i("rewq", "правильная кнопка " + String.valueOf(numTrueButton) + "  " + translation.getPainters().get(idPainter));
    }

    private int randomInt(int start, int end) {
        Random rand = new Random();
        while (true) {
            int value = rand.nextInt((end - start) + 1) + start;
            if(value != 0) {
                return value;
            }
        }

    }

    private ArrayList<Integer> randomSet(int start, int end, int size) {
        Set<Integer> uniqSet = new HashSet<Integer>();
        while (uniqSet.size() < size) {
            uniqSet.add(randomInt(start,end));
        }
        return new ArrayList<Integer>(uniqSet);
    }

    private String createUrlForPicture(int idPainter, int pictureQuantity) {
        int numPicture = randomInt(1, pictureQuantity);
        Log.i("qwer", "картина художника " + String.valueOf(numPicture));
        return BASE_URL
                .concat("/painters/")
                .concat(String.valueOf(idPainter))
                .concat("/thumbnails/")
                .concat(String.valueOf(numPicture))
                .concat(".jpg");
    }

    private void setNewPicture(final int idPainter) {
        Log.i("qwer", "sdf"+String.valueOf(idPainter));
        restApi.getPainter(idPainter, new Callback<Painter>() {
            @Override
            public void success(Painter painter, Response response) {

                Log.i("qwer", "answer " + response.getBody().mimeType());
                int pictureQuantity = painter.getPictureQuantity();
                Log.i("qwer", "картин у художника " + String.valueOf(pictureQuantity));
                String url = createUrlForPicture(idPainter, pictureQuantity);
                Log.i("qwer", url);
                Picasso.with(GameActivity.this)
                        .load(url)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .networkPolicy( NetworkPolicy.NO_STORE)
                        .into(imageViewPicture, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                                resetBackgroundColor();
                            }

                            @Override
                            public void onError() {

                            }
                        });

            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("qwer", error.getMessage());
//                Log.i("qwer",  String.valueOf(error.getResponse().getStatus()));
                error.printStackTrace();
            }
        });
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }
}
