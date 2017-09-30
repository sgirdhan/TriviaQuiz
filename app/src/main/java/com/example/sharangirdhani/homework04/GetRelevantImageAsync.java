package com.example.sharangirdhani.homework04;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sharangirdhani on 9/30/17.
 */

public class GetRelevantImageAsync extends AsyncTask<String, Void, Bitmap> {
    public DisplayImage image;

    public GetRelevantImageAsync(DisplayImage image) {
        this.image = image;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL imageURL = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
            Bitmap image = BitmapFactory.decodeStream(connection.getInputStream());
            return image;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        image.setImage(bitmap);
    }

    static interface DisplayImage{
        void setImage(Bitmap image);
    }
}
