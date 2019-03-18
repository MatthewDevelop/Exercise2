package com.foxconn.matthew.networkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Response;

public class AnotherActivity extends AppCompatActivity {

    private static final String TAG = "AnotherActivity";
    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        responseText = (TextView) findViewById(R.id.response_text);
    }

    public void onClick(View v) {
        Log.e(TAG, "onClick: ");
        //sendRequestWithOkHttp();

        sendRequestWithHttpUrlConnection();
    }

    private void sendRequestWithHttpUrlConnection() {
        String address="http://10.203.146.158:8080/Test/get_data.xml";
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                showResponse(response);
                parseXMLWithSax(response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void sendRequestWithOkHttp() {
        String address="http://10.203.146.158:8080/Test/get_data.xml";
        HttpUtil.sendOkHttpRequest(address,new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                showResponse(responseData);
                parseXMLWithPull(responseData);
            }
        });
    }

    private void parseJSONWithGSON(String responseDate) {
        Gson gson = new Gson();
        List<App> applist = gson.fromJson(responseDate, new TypeToken<List<App>>() {
        }.getType());
        for (App app : applist) {
            Log.e(TAG, "id is " + app.getId());
            Log.e(TAG, "name is " + app.getName());
            Log.e(TAG, "version is " + app.getVersion());
        }
    }

    private void parseJSONWithJsonObject(String responseDate) {
        try {
            JSONArray jsonArray = new JSONArray(responseDate);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.e(TAG, "id is " + id);
                Log.e(TAG, "name is " + name);
                Log.e(TAG, "version is " + version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithSax(String responseDate) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler();
            //将ContentHandler的实例设置到XMLReader
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(responseDate)));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithPull(String responseDate) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(responseDate));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)) {
                            Log.e(TAG, "id is " + id);
                            Log.e(TAG, "name is " + name);
                            Log.e(TAG, "version is " + version);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showResponse(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: ");
                responseText.setText(s);
            }
        });
    }
}
