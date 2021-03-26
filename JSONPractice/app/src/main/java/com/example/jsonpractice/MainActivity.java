package com.example.jsonpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    TextView textViewCategory, textViewTitle, textViewDate,textViewDescription,textViewPage;
    ImageView imageViewNewsImage;

    final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonGO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Missing Field Detected");
            }
        });
        String a = "General";
        getArticle();
    }
    void getArticle(){
      //  "https://newsapi.org/v2/top-headlines?country=us&apiKey=1e7b20ceeb7a47c3b3c882c6a2dedfb9"
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/persons/xml")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure: " + request.toString());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //ResponseBody responseBody = response.body();
                if (response.isSuccessful()) {
                    //XML pull parser
                    try {
                        ArrayList<Person> persons = new ArrayList<>();
                        Person person = new Person();
                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        XmlPullParser parser = factory.newPullParser();
                        parser.setInput(response.body().byteStream(), "UTF-8");
                        int eventType = parser.getEventType();
                        while(eventType != XmlPullParser.END_DOCUMENT){
                            if(eventType == XmlPullParser.START_TAG){
                                if(parser.getName().equals("person")){
                                    person = new Person();
                                    person.setId(parser.getAttributeValue(null, "id"));
                                } else if(parser.getName().equals("name")){
                                    person.setName(parser.nextText());
                                } else if(parser.getName().equals("age")){
                                    person.setAge(parser.nextText());
                                } else if(parser.getName().equals("address")){
                                    person.setAdress(new Adress());
                                } else if(person.getAdress() != null){
                                    if(parser.getName().equals("line1")){
                                        person.getAdress().setLine1(parser.nextText());
                                    } else if(parser.getName().equals("city")){
                                        person.getAdress().setCity(parser.nextText());
                                    } else if(parser.getName().equals("state")){
                                        person.getAdress().setState(parser.nextText());
                                    } else if(parser.getName().equals("zip")){
                                        person.getAdress().setZip(parser.nextText());
                                    }

                                }
                                Log.d(TAG, "onResponse: " + parser.getName());
                            } else if (eventType == XmlPullParser.END_TAG){
                                if(parser.getName().equals("person")){
                                    persons.add(person);
                                }
                                Log.d(TAG, "onResponse: " + parser.getName());
                            }


//                            else if (eventType == XmlPullParser.TEXT){
//                                Log.d(TAG, "onResponse: " + parser.getText());
//                            }
                            eventType = parser.next();
                        }
                        Log.d(TAG, "onResponse: " + persons);

                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }

                    //XML SAX Parser
//                    try {
//                        PersonsXMLParser personsXMLParser = new PersonsXMLParser();
//                        ArrayList<Person> persons = personsXMLParser.parse(response.body().byteStream());
//                        Log.d(TAG, "onResponse: " + persons);
//                    } catch (SAXException e) {
//                        e.printStackTrace();
//                    }

                }
            }
        });

    }

}