package com.example.bibliotech.api;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.bibliotech.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class APIClient {

    private static final String BASE_URL = "http://10.240.72.69/comp2000/library/";
    private static Retrofit retrofit;

    public static LibraryAPI getApi() {
        if (retrofit ==  null) {
            // Adding some logging to help debugging :)
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();


                retrofit= new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                        .client(client)

                    .addConverterFactory( GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(LibraryAPI.class);
    }
}
