package ru.nsu.ccfit.tabakov.artchallenge;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Константин on 18.04.2015.
 */
public interface ITranslationApi {
    @GET("/locales/{locales}/translation.json")
    public void getTranslation(@Path("locales") String locales, Callback<Translation> callback);
//    public Translation getTranslation(@Path("locales") String locales);
}
