package ru.nsu.ccfit.tabakov.artchallenge;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Константин on 18.04.2015.
 */
public class GameActivity  extends Activity implements View.OnClickListener {
    private Button buttonPainter_1;
    private Button buttonPainter_2;
    private Button buttonPainter_3;
    private Button buttonPainter_4;
//    ArrayList<Button> setButton = new ArrayList<Button>(Arrays.asList(buttonPainter_1, buttonPainter_2, buttonPainter_3, buttonPainter_4));
    ArrayList<Button> setButton = new ArrayList<Button>();


    private ImageView imageViewPicture;

    private IPainterApi restApi;

    private ArrayList<Integer> setPainters = new ArrayList<Integer>(Arrays.asList(2,3,9,16,17,21,30,36,49,53,57,60,61,69,77,84,94,96));


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

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://artchallenge.me")
                .build();
        restApi = restAdapter.create(IPainterApi.class);

        setButton.add((Button) findViewById(R.id.buttonPainter_2));
        setButton.add((Button) findViewById(R.id.buttonPainter_3));
        setButton.add((Button) findViewById(R.id.buttonPainter_4));

        imageViewPicture = (ImageView) findViewById(R.id.imageViewmPicture);
    }



    private void setListener(View.OnClickListener listener) {
        for(Button button : setButton) {
            button.setOnClickListener(listener);
        }
    }

    private void checkTruePainter(int numAnswer) {
        int numTruePaintet = 1;
        if (numAnswer == numTruePaintet) {
            Log.i("qwer", "Выбран правильный ответ");
            //добавить зеленую звездочку
        } else {
            Log.i("qwer", "Выбран не правильный ответ");
            //добавить красную звездочку
        }
        int tmp = randomInt(1, setPainters.size());
        int idPainter = setPainters.get(tmp);
        int numTrueButton = randomInt(1, 4);

        Log.i("qwer", "Художник " + String.valueOf(idPainter));
        setNewPicture(idPainter);

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
                Log.i("qwer", "answer " + painter.getName() + painter.getId() + painter.getPictureQuantity());
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
                error.printStackTrace();
            }
        });

    }
}
