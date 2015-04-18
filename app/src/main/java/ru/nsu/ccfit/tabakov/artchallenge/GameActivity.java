package ru.nsu.ccfit.tabakov.artchallenge;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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

    private ImageView imageViewPicture;


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

        imageViewPicture = (ImageView) findViewById(R.id.imageViewmPicture);
    }

    private void setListener(View.OnClickListener listener) {
        buttonPainter_1.setOnClickListener(listener);
        buttonPainter_2.setOnClickListener(listener);
        buttonPainter_3.setOnClickListener(listener);
        buttonPainter_4.setOnClickListener(listener);

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
        int idPainter = randomInt(1,10);
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
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://artchallenge.me")
                .build();
        IPainterApi restApi = restAdapter.create(IPainterApi.class);
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
            }
        });

    }
}
