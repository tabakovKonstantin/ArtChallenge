package ru.nsu.ccfit.tabakov.artchallenge;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.squareup.picasso.Picasso;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Константин on 18.04.2015.
 */
public class GameActivity  extends Activity implements View.OnClickListener {
    private ArrayList<Button> setButton = new ArrayList<Button>();
    private int numTrueButton;
    private int idPainter;
    private ImageView imageViewPicture;
    private RatingBar ratingBar;
    private IPainterApi restApi;
    private ITranslationApi restApi2;
    private Translation translation;
    private ArrayList<Integer> setPainters = new ArrayList<Integer>(Arrays.asList(2,3,9,16,17,21,30,36,49,53,57,60,61,69,77,84,94,96));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        initComponents();

        setNewPicture(idPainter);
        setListener(this);


        restApi2.getTranslation("ru", new Callback<Translation>() {
            @Override
            public void success(Translation translation, Response response) {
                Log.i("qwer", translation.painters.get(2));
                setButtonText(translation);
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
        }

    }

    private void initComponents() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://artchallenge.me")
                .build();
        restApi = restAdapter.create(IPainterApi.class);

        RestAdapter build = new RestAdapter.Builder()
                .setEndpoint("http://artchallenge.ru")
                .build();
        restApi2 = build.create(ITranslationApi.class);

        setButton.add((Button) findViewById(R.id.buttonPainter_1));
        setButton.add((Button) findViewById(R.id.buttonPainter_2));
        setButton.add((Button) findViewById(R.id.buttonPainter_3));
        setButton.add((Button) findViewById(R.id.buttonPainter_4));

        imageViewPicture = (ImageView) findViewById(R.id.imageViewmPicture);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    }

    private void setListener(View.OnClickListener listener) {
        for(Button button : setButton) {
            button.setOnClickListener(listener);
        }
    }

    private void checkTruePainter(int numAnswer) {
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        if (numAnswer == numTrueButton) {
            Log.i("qwer", "Выбран правильный ответ");
            ratingBar.setRating(5);

            //добавить зеленую звездочку
        } else {
            Log.i("qwer", "Выбран не правильный ответ");
            //добавить красную звездочку
//            ratingBar.setNumStars(1);
        }

        setButtonText(translation);
        setNewPicture(idPainter);
        Log.i("qwer", "Художник " + String.valueOf(idPainter));
    }

    private void setButtonText(Translation translation) {

        ArrayList<Integer> uniqSet = randomSet(1, setPainters.size(), setButton.size());
        numTrueButton = randomInt(0, 3);
        idPainter = uniqSet.get(numTrueButton);

        for (int i = 0; i < uniqSet.size(); i++) {
            String textButton = translation.painters.get(uniqSet.get(i));
            setButton.get(i).setText(textButton);
        }
        Log.i("qwer", "правильная кнопка " + String.valueOf(numTrueButton));
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
        return "http://artchallenge.me/painters/"
                .concat(String.valueOf(idPainter))
                .concat("/thumbnails/")
                .concat(String.valueOf(numPicture))
                .concat(".jpg");
    }

    private void setNewPicture(final int idPainter) {
        Log.i("qwer", String.valueOf(idPainter));
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
                        .into(imageViewPicture);

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
