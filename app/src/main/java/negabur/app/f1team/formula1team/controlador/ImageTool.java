package negabur.app.f1team.formula1team.controlador;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ruben on 24/2/15.
 */
public class ImageTool {

    public ImageTool(){

    }

    /**
     * Method for convert image to a byte Array
     *
     * @param path

     * @return byte array
     */
    public Bitmap convertImageToByte(String path, Context cont){
        Bitmap image = null;
        AssetManager assetManager = cont.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(path);
            image = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public Bitmap convertImageToByte(String path){
        Bitmap image = null;
        File file = new File(path);
        InputStream istr = null;
        try {
            istr = new FileInputStream(file);
            image = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
