package com.vs.Statoos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.vs.Statoos.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PostImageToInstagram extends AppCompatActivity {
    private AdView mAdViewPost;
    ImageView imageView,postImageToFacebook;
    Button saveGallery;
    String ImagePath,data,filename,filePath,fontStyle="Helvetica_Neue.ttf";
    Bitmap bmp,image, finalImage;
    int width;
    int textColor, btmColor, backgroundImage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image_to_instagram);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);

        mAdViewPost = (AdView) findViewById(R.id.adViewPost);
        postImageToFacebook = (ImageView) findViewById(R.id.postImageToFacebook);
        saveGallery = (Button) findViewById(R.id.saveToGallary);
        imageView = (ImageView) findViewById(R.id.image1);
        Intent intent = getIntent();
     //   Bitmap image = (Bitmap) intent.getParcelableExtra("BitmapImage");
        data =  intent.getStringExtra("BitmapImage");
        fontStyle = intent.getStringExtra("FontStyle");
        textColor = intent.getIntExtra("textColor1", 0);
        btmColor = intent.getIntExtra("btmColor1", 0);
        backgroundImage = intent.getIntExtra("BackgroundImage", 0);

        Converter convert = new Converter(getApplicationContext());

        // text , size , stroke ,color,typeface
        bmp = convert.textAsBitmap(data, 32, 5, textColor, btmColor,
                Typeface.createFromAsset(getAssets(), fontStyle), backgroundImage);
        image = convert.addBorder(bmp, 0, Color.BLACK);
       // finalImage = createSquaredBitmap(image);
        //BitmapDrawable ob = new BitmapDrawable(getResources(), image);
      //  imageView.setBackgroundDrawable(ob);
        imageView.setImageBitmap(image);
        // file name appending with system date

        Calendar c = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        filename = "ScreenShot_" + f.format(c.getTime());

        // filename = "ScreenShot_" + "123";
        filePath = Environment.getExternalStorageDirectory() + "/Statoos" + "/" + filename
                + ".jpeg";
        // saving image to sdcard
        saveGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImage saveimg = new SaveImage();
                // pass bit and filename
                saveimg.storeImage(image, filename);
                Toast.makeText(PostImageToInstagram.this,"Image saved to gallery",Toast.LENGTH_SHORT).show();
            }
        });

        postImageToFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SaveImage saveimg = new SaveImage();
                // pass bit and filename
                //saveimg.storeImage(image, filename);
                sendImageToFacebook();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewPost.loadAd(adRequest);

        mAdViewPost.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //  Toast.makeText(getApplicationContext(), "Ad is loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
               Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public File reSizeImage(){
    File f = null;
    File imgFileOrig = new File(filePath);//change "getPic()" for whatever you need to open the image file.
    Bitmap b = BitmapFactory.decodeFile(imgFileOrig.getAbsolutePath());
    // original measurements
    int origWidth = b.getWidth();
    int origHeight = b.getHeight();

    final int destWidth = 300;//or the width you need

    if(origWidth > destWidth){
        // picture is wider than we want it, we calculate its target height
        int destHeight = origHeight/( origWidth / destWidth ) ;
        // we create an scaled bitmap so it reduces the image, not just trim it
        Bitmap b2 = Bitmap.createScaledBitmap(b, destWidth, destHeight, false);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // compress to the format you want, JPEG, PNG...
        // 70 is the 0-100 quality percentage
        b2.compress(Bitmap.CompressFormat.JPEG,70 , outStream);

        // we save the file, at least until we have made use of it

        f = new File(Environment.getExternalStorageDirectory() + "/Statoos" + "/" + filename
                + ".jpeg");
       // f = new File(filePath);
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(outStream.toByteArray());
            // remember close de FileOutput
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    return f;
}

    public void sendImageToInstaGram(){
      // String imagePath = Environment.getExternalStorageDirectory() + "/status_king" + "/ScreenShot_20-02-2018-08-33-13.png";
        File f= new File(filePath);
       // File f = reSizeImage();
        Uri uri = Uri.parse("file://"+f.getAbsolutePath());
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setPackage("com.instagram.android");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setType("image/*");
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(share, "Share image File"));

    }

    public void sendImageToFacebook() {

    }

    private static Bitmap createSquaredBitmap(Bitmap srcBmp) {
        int dim = Math.max(srcBmp.getWidth(), srcBmp.getHeight());
        Bitmap dstBmp = Bitmap.createBitmap(dim, dim, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(dstBmp);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(srcBmp, (dim - srcBmp.getWidth()) / 2, (dim - srcBmp.getHeight()) / 2, null);

        return dstBmp;
    }

    @Override
    public void onPause() {
        if (mAdViewPost != null) {
            mAdViewPost.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdViewPost != null) {
            mAdViewPost.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdViewPost != null) {
            mAdViewPost.destroy();
        }
        super.onDestroy();
    }

}
