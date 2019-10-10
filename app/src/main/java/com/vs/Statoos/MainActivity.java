package com.vs.Statoos;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Layout;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vs.Statoos.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileNotFoundException;

 public class MainActivity extends AppCompatActivity {

    EditText str_et;
    Button mybtn;
    Bitmap bmp, image;
    TextView numberOfCharacters;
    TextView whiteColor, blueColor, greenColor, redColor, yellowColor, blackColor, backgroundImage, circularWhiteColor, circularBlueColor, circularGreenColor, circularRedColor, circularYellowColor, circularBlackColor;
    String filename, value;
    int currentLine;
     private AdView mAdViewMain;
     private boolean isReached = false;
     int characterMinus;
     int textColor = -16777216;
     int btmColor = -1;
     int bitmapBackground = 0;
     View v;
     LinearLayout textColorLayout;
     ImageView imageViewText, regularText, boldText, italicText;
     private boolean isOpened = false;
     Typeface type;
     String fontStyle = "Helvetica_Neue.ttf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this,"ca-app-pub-8425819219373897~8696143019");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);

        mAdViewMain = (AdView) findViewById(R.id.adViewMain);
        v = new View(getApplicationContext());
        str_et = (EditText) findViewById(R.id.str_id);
       // str_et.setSingleLine(false);
      //  str_et.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
      //  str_et.setInputType(InputType.TYPE_CLASS_TEXT);
        /*InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(30);
        str_et.setFilters(filters);*/
        mybtn = (Button) findViewById(R.id.btn_id);
        numberOfCharacters = (TextView) findViewById(R.id.numberOfCharacters);
        whiteColor = (TextView) findViewById(R.id.white_color);
        blueColor = (TextView) findViewById(R.id.blue_color);
        greenColor = (TextView) findViewById(R.id.green_color);
        redColor = (TextView) findViewById(R.id.red_color);
        yellowColor = (TextView) findViewById(R.id.yellow_color);
        blackColor = (TextView) findViewById(R.id.black_color);
        backgroundImage = (TextView) findViewById(R.id.background_image);
        imageViewText = (ImageView) findViewById(R.id.imageViewText);
        textColorLayout = (LinearLayout) findViewById(R.id.textColorLayout);
        regularText = (ImageView) findViewById(R.id.regularText);
        boldText = (ImageView) findViewById(R.id.boldText);
        italicText = (ImageView) findViewById(R.id.italicText);
        circularWhiteColor = (TextView) findViewById(R.id.white_color_circle);
        circularBlueColor = (TextView) findViewById(R.id.blue_color_circle);
        circularGreenColor = (TextView) findViewById(R.id.green_color_circle);
        circularRedColor = (TextView) findViewById(R.id.red_color_circle);
        circularYellowColor = (TextView) findViewById(R.id.yellow_color_circle);
        circularBlackColor = (TextView) findViewById(R.id.black_color_circle);

        type = Typeface.createFromAsset(getAssets(),"Helvetica_Neue.ttf");

        //addTestDevice("748303DC66299A698741D5BAE5E9CBA2")

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewMain.loadAd(adRequest);

        mAdViewMain.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //  Toast.makeText(getApplicationContext(), "Ad is loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                //Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                //Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                //Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                //do your work

                File test = new File(Environment.getExternalStorageDirectory() + "/Statoos");
                if (!test.exists()) {
                    try {
                        if (test.mkdir()) {
                            Log.d("xxx", "directory created");
                        } else {
                            Log.d("xxx", "directory creation failed");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("xxx", "directory already present");
                }
            } else {
                requestPermission();
            }
        }

        str_et.addTextChangedListener(mTextEditorWatcher);
        mybtn.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String data = str_et.getText().toString();
                //currentCharacterLength = str_et.getText().toString().trim().length();

                // Convert Text to Image
                Converter convert = new Converter(getApplicationContext());

                // text , size , stroke ,color,typeface

                //converting text to bitmap image here
                 bmp = convert.textAsBitmap(data, 32, 5, textColor, btmColor,
                        type, bitmapBackground);

                //passing border size 0 because we dont need the image borders
                 image = convert.addBorder(bmp, 0, Color.BLACK);

                // file name appending with system date

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
                    filename = "ScreenShot_" + f.format(c.getTime());

                /*String filePath = Environment.getExternalStorageDirectory() + "/status_king" + "/" + filename
                        + ".jpeg";*/
                // saving image to sdcard
                SaveImage saveimg = new SaveImage();
                // pass bit and filename
                // saving image to the phone here
                saveimg.storeImage(image, filename);

                Toast.makeText(getApplicationContext(),"Saved As :n" + filename, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, PostImageToInstagram.class);
                intent.putExtra("BitmapImage", data);
                intent.putExtra("FontStyle", fontStyle);
                intent.putExtra("textColor1", textColor);
                intent.putExtra("btmColor1", btmColor);
                intent.putExtra("BackgroundImage", bitmapBackground);
                startActivity(intent);

            }
        });

        whiteColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                v.setBackgroundResource(R.drawable.edittext_background);
                str_et.setBackground(v.getBackground());
                btmColor = Color.WHITE;
                bitmapBackground = 0;
            }
        });

        blueColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.setBackgroundResource(R.drawable.edittext_background_blue);
                str_et.setBackground(v.getBackground());
                btmColor = Color.BLUE;
                bitmapBackground = 0;
            }
        });

        greenColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.setBackgroundResource(R.drawable.edittext_background_green);
                str_et.setBackground(v.getBackground());
                btmColor = Color.GREEN;
                bitmapBackground = 0;
            }
        });

        redColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.setBackgroundResource(R.drawable.edittext_background_red);
                str_et.setBackground(v.getBackground());
                btmColor = Color.RED;
                bitmapBackground = 0;
            }
        });

        yellowColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.setBackgroundResource(R.drawable.edittext_background_yellow);
                str_et.setBackground(v.getBackground());
                btmColor = Color.YELLOW;
                bitmapBackground = 0;
            }
        });

        blackColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.setBackgroundResource(R.drawable.edittext_background_black);
                str_et.setBackground(v.getBackground());
                btmColor = Color.BLACK;
                bitmapBackground = 0;
            }
        });

        backgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.setBackgroundResource(R.drawable.backgroundimage);
                str_et.setBackground(v.getBackground());
                bitmapBackground = R.drawable.backgroundimage;
            }
        });

        imageViewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOpened = !isOpened;
                if(isOpened){
                    textColorLayout.setVisibility(LinearLayout.VISIBLE);
                } else {
                    textColorLayout.setVisibility(LinearLayout.GONE);
                }

            }
        });

        regularText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = Typeface.createFromAsset(getAssets(),"Helvetica_Neue.ttf");
                str_et.setTypeface(type);
                fontStyle = "Helvetica_Neue.ttf";

            }
        });

        boldText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = Typeface.createFromAsset(getAssets(),"AvenirNext-Bold.ttf");
                str_et.setTypeface(type);
                fontStyle = "AvenirNext-Bold.ttf";

            }
        });

        italicText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = Typeface.createFromAsset(getAssets(),"hemi head bd it.ttf");
                str_et.setTypeface(type);
                fontStyle = "hemi head bd it.ttf";
            }
        });

        circularWhiteColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textColor = Color.WHITE;
                str_et.setTextColor(textColor);
                str_et.setHintTextColor(textColor);

            }
        });

        circularBlueColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 textColor = Color.BLUE;
                 str_et.setTextColor(textColor);
                 str_et.setHintTextColor(textColor);
            }
        });

        circularGreenColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  textColor = Color.GREEN;
                  str_et.setTextColor(textColor);
                  str_et.setHintTextColor(textColor);
            }
        });

        circularRedColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  textColor = Color.RED;
                  str_et.setTextColor(textColor);
                  str_et.setHintTextColor(textColor);
            }
        });

        circularYellowColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   textColor = Color.YELLOW;
                   str_et.setTextColor(textColor);
                   str_et.setHintTextColor(textColor);
            }
        });

        circularBlackColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   textColor = Color.BLACK;
                   str_et.setTextColor(textColor);
                   str_et.setHintTextColor(textColor);
            }
        });

    }

    protected boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    protected void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //do your work
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }


    private final TextWatcher mTextEditorWatcher=new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
        /*    String string = s.toString();
            currentLine = getCurrentCursorLine(str_et);
            if(string.charAt(string.length() - 1) == '\n') {
                switch (currentLine) {
                    case 1:
                        if(Integer.parseInt(numberOfCharacters.getText().toString()) > 0) {
                            int length1 = Integer.parseInt(numberOfCharacters.getText().toString());
                            int LineOne = length1 - 30;
                            numberOfCharacters.setText(String.valueOf(LineOne));
                        }
                        break;
                    case 2:
                        if(Integer.parseInt(numberOfCharacters.getText().toString()) > 0) {
                            int length2 = Integer.parseInt(numberOfCharacters.getText().toString());
                            int LineTwo = length2 - 30;
                            numberOfCharacters.setText(String.valueOf(LineTwo));
                        }
                        break;
                    case 3:
                        if(Integer.parseInt(numberOfCharacters.getText().toString()) > 0) {
                            int length3 = Integer.parseInt(numberOfCharacters.getText().toString());
                            int LineThree = length3 - 30;
                            numberOfCharacters.setText(String.valueOf(LineThree));
                        }
                        break;
                    case 4:
                        if(Integer.parseInt(numberOfCharacters.getText().toString()) > 0) {
                            int length4 = Integer.parseInt(numberOfCharacters.getText().toString());
                            int LineFour = length4 - 30;
                            numberOfCharacters.setText(String.valueOf(LineFour));
                        }
                        break;
                    case 5:
                        if(Integer.parseInt(numberOfCharacters.getText().toString()) > 0) {
                            int length5 = Integer.parseInt(numberOfCharacters.getText().toString());
                            int LineFive = length5 - 30;
                            numberOfCharacters.setText(String.valueOf(LineFive));
                        }
                        break;
                    case 6:
                        if(Integer.parseInt(numberOfCharacters.getText().toString()) > 0) {
                            int length6 = Integer.parseInt(numberOfCharacters.getText().toString());
                            int LineSix = length6 - 30;
                            numberOfCharacters.setText(String.valueOf(LineSix));
                        }
                        break;
                    case 7:
                        if(Integer.parseInt(numberOfCharacters.getText().toString()) > 0) {
                            int length7 = Integer.parseInt(numberOfCharacters.getText().toString());
                            int LineSeven = length7 - 30;
                            numberOfCharacters.setText(String.valueOf(LineSeven));
                        }
                        break;
                    case 8:

                            if (null != str_et.getLayout() && str_et.getLayout().getLineCount() > 8) {
                                // str_et.getText().delete(str_et.getText().length() - 1, str_et.getText().length());
                                str_et.getText().delete(str_et.getText().length() - 1, str_et.getText().length());
                            } else {
                                int length8 = Integer.parseInt(numberOfCharacters.getText().toString());
                                int LineEight = length8 - 30;
                                numberOfCharacters.setText(String.valueOf(LineEight));
                            }

                        break;

                }
            } *//*else if (null != str_et.getLayout() && str_et.getLayout().getLineCount() > 8) {
                str_et.getText().delete(str_et.getText().length() - 1, str_et.getText().length());
                // Toast.makeText(getApplicationContext(),"You don't exceed more than 10 lines", Toast.LENGTH_SHORT).show();
            }*//*else {
                //int length = str_et.getText().toString().trim().length();
                if (Integer.parseInt(numberOfCharacters.getText().toString()) >= 0) {
                    numberOfCharacters.setText(String.valueOf(Integer.parseInt(numberOfCharacters.getText().toString()) - count));
                }else{
                    numberOfCharacters.setText(String.valueOf(0));
                }
            }*/
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

           /* for(int i = s.length()-1; i >= 0; i--) {
                if (s.charAt(i) == '\n') {
                    s.delete(i, i + 1);
                    return;
                }
            }*/

           /*currentLine = str_et.getLayout().getLineCount();
            switch (currentLine) {
                case 1:
                    InputFilter[] filters1 = new InputFilter[1];
                    filters1[0] = new InputFilter.LengthFilter(250);
                    str_et.setFilters(filters1);
                    break;
                case 2:
                    InputFilter[] filters2 = new InputFilter[1];
                    filters2[0] = new InputFilter.LengthFilter(250);
                    str_et.setFilters(filters2);
                    break;
                case 3:
                    InputFilter[] filters3 = new InputFilter[1];
                    filters3[0] = new InputFilter.LengthFilter(250);
                    str_et.setFilters(filters3);
                    break;
                case 4:
                    InputFilter[] filters4 = new InputFilter[1];
                    filters4[0] = new InputFilter.LengthFilter(250);
                    str_et.setFilters(filters4);
                    break;
                case 5:
                    InputFilter[] filters5 = new InputFilter[1];
                    filters5[0] = new InputFilter.LengthFilter(250);
                    str_et.setFilters(filters5);
                    break;
                case 6:
                    InputFilter[] filters6 = new InputFilter[1];
                    filters6[0] = new InputFilter.LengthFilter(250);
                    str_et.setFilters(filters6);
                    break;
                case 7:
                    InputFilter[] filters7 = new InputFilter[1];
                    filters7[0] = new InputFilter.LengthFilter(250);
                    str_et.setFilters(filters7);
                    break;
                case 8:
                    setEditTextMaxLength(44);
                    break;
            }*/
           /* if(str_et.getText().length() == 35 && !isReached) {
                str_et.append("\n");
                isReached = true;
            }
            // if edittext has less than 10chars & boolean has changed, reset
            if(str_et.getText().length() < 35 && isReached)
                isReached = false;*/

           /* if(s.toString().contains("\n")) {
                switch (currentLine) {
                    case 1:
                        numberOfCharacters.setText(String.valueOf(250 - (s.length() + 40)));
                        value = numberOfCharacters.getText().toString();
                        setEditTextMaxLength(Integer.parseInt(value));
                        break;
                    case 2:
                        numberOfCharacters.setText(String.valueOf(250 - (s.length() + 80)));
                        value = numberOfCharacters.getText().toString();
                        setEditTextMaxLength(Integer.parseInt(value));
                        break;
                    case 3:
                        numberOfCharacters.setText(String.valueOf(250 - (s.length() + 120)));
                        value = numberOfCharacters.getText().toString();
                        setEditTextMaxLength(Integer.parseInt(value));
                        break;
                    case 4:
                        numberOfCharacters.setText(String.valueOf(250 - (s.length() + 140)));
                        value = numberOfCharacters.getText().toString();
                        setEditTextMaxLength(Integer.parseInt(value));
                        break;
                    case 5:
                        numberOfCharacters.setText(String.valueOf(250 - (s.length() + 160)));
                        value = numberOfCharacters.getText().toString();
                        setEditTextMaxLength(Integer.parseInt(value));
                        break;
                    case 6:
                        numberOfCharacters.setText(String.valueOf(250 - (s.length() + 180)));
                        value = numberOfCharacters.getText().toString();
                        setEditTextMaxLength(Integer.parseInt(value));
                        break;
                    case 7:
                        numberOfCharacters.setText(String.valueOf(250 - (s.length() + 200)));
                        value = numberOfCharacters.getText().toString();
                        setEditTextMaxLength(Integer.parseInt(value));
                        break;
                    case 8:
                        numberOfCharacters.setText(String.valueOf(250 - (s.length() + 220)));
                        value = numberOfCharacters.getText().toString();
                        setEditTextMaxLength(Integer.parseInt(value));
                        break;

                }
            }else{
                int length = str_et.getText().toString().trim().length();
                numberOfCharacters.setText(String.valueOf(250 - s.length()));
            }
*/
            /* if(str_et.getLayout().getLineCount() == 8){
                 InputFilter[] filters5 = new InputFilter[1];
                 filters5[0] = new InputFilter.LengthFilter(30);
                 str_et.setFilters(filters5);

            }*/
            /* currentLine = getCurrentCursorLine(str_et);
            //currentLine = str_et.getLayout().getLineCount();
            if(s.toString().contains("\n")) {
                characterMinus = currentLine * 30;

            }else{
                characterMinus = 0;
            }

            setEditTextMaxLength(250 - characterMinus);
            numberOfCharacters.setText(String.valueOf(250 - (s.length() + characterMinus)));*/
            numberOfCharacters.setText(String.valueOf(250 - s.length()));

            if (null != str_et.getLayout() && str_et.getLayout().getLineCount() > 8) {
                str_et.getText().delete(str_et.getText().length() - 1, str_et.getText().length());
               // Toast.makeText(getApplicationContext(), "You Don't exceed more than 8 lines", Toast.LENGTH_SHORT).show();
            }
            else{
                //int length = str_et.getText().toString().trim().length();
               // numberOfCharacters.setText(String.valueOf(250 - s.length()));
            }
                /*if(Integer.parseInt(numberOfCharacters.getText().toString()) > 1) {
                   // str_et.setEnabled(true);
                    str_et.getText().delete(str_et.getText().length() - 1, str_et.getText().length());
                }else{
                   // str_et.setEnabled(false);
                    str_et.getText().delete(str_et.getText().length(), str_et.getText().length());
                }*/
                // Toast.makeText(getApplicationContext(),"You don't exceed more than 10 lines", Toast.LENGTH_SHORT).show();


           // numberOfCharacters.setText(String.valueOf(250 - s.length()));


           /* str_et.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {

                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                    {
                        Log.i("event", "captured");

                        switch (currentLine){
                            case 1:
                                int LineOne = numberOfCharacters.getText().length() - 25;
                                numberOfCharacters.setText(String.valueOf(LineOne));
                                break;
                            case 2:
                                int LineTwo = numberOfCharacters.getText().length() - 25;
                                numberOfCharacters.setText(String.valueOf(LineTwo));
                                break;
                            case 3:
                                int LineThree = numberOfCharacters.getText().length() - 25;
                                numberOfCharacters.setText(String.valueOf(LineThree));
                                break;
                            case 4:
                                int LineFour = numberOfCharacters.getText().length() - 25;
                                numberOfCharacters.setText(String.valueOf(LineFour));
                                break;
                            case 5:
                                int LineFive = numberOfCharacters.getText().length() - 25;
                                numberOfCharacters.setText(String.valueOf(LineFive));
                                break;
                            case 6:
                                int LineSix = numberOfCharacters.getText().length() - 25;
                                numberOfCharacters.setText(String.valueOf(LineSix));
                                break;
                            case 7:
                                int LineSeven = numberOfCharacters.getText().length() - 25;
                                numberOfCharacters.setText(String.valueOf(LineSeven));
                                break;
                            case 8:
                                int LineEight = numberOfCharacters.getText().length() - 25;
                                numberOfCharacters.setText(String.valueOf(LineEight));
                                break;

                        }

                        return true;
                    }

                    return false;
                }
            });*/

            //numberOfCharacters.setText(String.valueOf(250 - s.length()));
        }
    };

     public void setEditTextMaxLength(int length) {
         InputFilter[] editFilters = str_et.getFilters();
         InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
         System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
         newFilters[editFilters.length] = new InputFilter.LengthFilter(length); //the desired length
         str_et.setFilters(newFilters);

         /*InputFilter[] filterArray = new InputFilter[1];
         filterArray[0] = new InputFilter.LengthFilter(length);
         str_et.setFilters(filterArray);*/
     }
     

     @Override
     public void onPause() {
         if (mAdViewMain != null) {
             mAdViewMain.pause();
         }
         super.onPause();
     }

     @Override
     public void onResume() {
         super.onResume();
         if (mAdViewMain != null) {
             mAdViewMain.resume();
         }
     }

     @Override
     public void onDestroy() {
         if (mAdViewMain != null) {
             mAdViewMain.destroy();
         }
         super.onDestroy();
     }

     public int getCurrentCursorLine(EditText editText)
     {
         int selectionStart = Selection.getSelectionStart(editText.getText());
         Layout layout = editText.getLayout();

         if (selectionStart != -1) {
             return layout.getLineForOffset(selectionStart);
         }

         return -1;
     }

 }
