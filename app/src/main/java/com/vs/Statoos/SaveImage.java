package com.vs.Statoos;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Android-Dev2 on 2/20/2018.
 */

public class SaveImage {

    public boolean storeImage(Bitmap imageData, String filename) {
        /*String iconsStoragePath = Environment.getExternalStorageDirectory()
                + "/myImages";*/

      /*  try {
            Runtime.getRuntime().exec(new String[]{"myImages", "-f", iconsStoragePath, "MyAppTAG:V", "*:S"});
        } catch (IOException e) {
            e.printStackTrace();
        } */

      /*  File sdIconStorageDir = new File(iconsStoragePath);
        sdIconStorageDir.mkdirs();*/

        try {
          //  String filePath = Environment.getExternalStorageDirectory() + "/status_king" + "/" + filename
                  //  + ".jpeg";
            File filePath = Environment.getExternalStorageDirectory();

            File cacheDir = new File(filePath.getAbsolutePath() + "/Statoos/");
            cacheDir.mkdirs();
            String filePath1 = cacheDir + "/"  + filename + ".jpeg";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath1);

            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

            // compress image according to your format
            imageData.compress(Bitmap.CompressFormat.JPEG, 70, bos);

            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return false;
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return false;
        }

        return true;
    }
}
