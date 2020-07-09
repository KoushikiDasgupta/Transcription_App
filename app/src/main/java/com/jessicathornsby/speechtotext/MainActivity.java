package com.jessicathornsby.speechtotext;

import android.content.ActivityNotFoundException;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.TextView;
import android.view.View;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import android.media.MediaPlayer;
import android.net.Uri;
import android.app.Activity;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;
import android.widget.Toast;
import java.io.File;
import java.util.Locale;
import java.lang.reflect.Field;
import android.util.Log;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;
import java.lang.Object;
import android.content.res.AssetFileDescriptor;

import static java.util.logging.Logger.global;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private TextView textOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textOutput = (TextView) findViewById(R.id.textOutput);

    }

    /*public void listRaw(){
        Field[] fields=R.raw.class.getFields();
        for(int count=0;count<fields.length;count++) {
            Log.i("Raw Asset: ",fields[count].getName());
            int resourceID=fields[count].getInt(fields[count]);
        }
    }*/


    public void onClick(View v) {

        //Field[] fields = R.raw.class.getFields();
        //for (int count = 0; count < fields.length; count++) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,1000000000);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_prompt));
            try {
                startActivityForResult(intent, REQUEST_CODE);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.speech_not_supported),
                        Toast.LENGTH_SHORT).show();
            }
            //code for playing the input file
            try {
                /*AssetManager assetManager = getAssets();
                String[] files=assetManager.list("audiofiles");
                final int audioindex=0;
                MediaPlayer mp;
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        FunctionPlayFile(files[audioindex])
                        audioindex+=1;
                    }
                });
                void FunctionPlayFile()
                }*/
                /*Field[] fields = R.raw.class.getFields();
                int[] resourceID = null;
                for (int count = 0; count < fields.length; count++) {
                    Log.i("Raw Asset: ", fields[count].getName());
                    resourceID[count] = fields[count].getInt(fields[count]);
                }*/
                MediaPlayer mp = MediaPlayer.create(this,R.raw.test_20);
                mp.start();
            } catch (Exception e) {
                Toast.makeText(this, "Problem occured..!!!", Toast.LENGTH_SHORT).show();
            }
        }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textOutput.setText(result.get(0));
                    //String path="D://applog.txt";
                    try {
                        File myfile=new File("D://output.txt");
                        Path out=Paths.get("D://output.txt");
                        Files.write(out,result, Charset.defaultCharset());
                        //Files.write(Paths.get(path), result.get(0).getBytes());
                    }catch(IOException e){e.printStackTrace();}
                }
                break;
            }
        }
    }


}


