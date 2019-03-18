package com.foxconn.test.ksoap2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.foxconn.test.R;

import java.util.List;

public class MainKsoap2Activity extends Activity {

    private Spinner city, citys;
    private List<String> listcity, listcitys, weater;
    // 分别显示近三天的天气信息和城市介绍
    private TextView cityNames1, cityNames2, cityNames3, cityjie;
    private ImageView weateImage1, weateImage2, weateImage3;

    private static final String TAG = "skylark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        citys = (Spinner) findViewById(R.id.citys);
        city = (Spinner) findViewById(R.id.city);
        cityNames1 = (TextView) findViewById(R.id.cityNames1);
        cityNames2 = (TextView) findViewById(R.id.cityNames2);
        cityNames3 = (TextView) findViewById(R.id.cityNames3);
        cityjie = (TextView) findViewById(R.id.cityjie);
        weateImage1 = (ImageView) findViewById(R.id.weateImage1);
        weateImage2 = (ImageView) findViewById(R.id.weateImage2);
        weateImage3 = (ImageView) findViewById(R.id.weateImage3);

        new Thread() {
            @Override
            public void run() {
                super.run();
                listcitys = WebServiceTest.getCitys();
                Log.e(TAG, "所有省份" + listcitys.size() + listcitys.toString());
                listcity = WebServiceTest.getCity("湖北");
                Log.e(TAG, "该省下的所有城市" + listcity.size() + listcity.toString());
//                weater = WebServiceTest.getWeather("武汉");
//                Log.e(TAG, "获取三天之内的天气详情" + weater.size() + weater.toString());
            }
        }.start();

		/*listcitys = WebServiceTest.getCitys();
        Log.e(TAG,"所有省份"+ listcitys.size() + listcitys.toString());*/
//		listcity = WebServiceTest.getCity("湖北");
//		Log.e(TAG,"该省下的所有城市" + listcity.size()+ listcity.toString());
        /*weater = WebServiceTest.getWeather("武汉");
		Log.e(TAG,"获取三天之内的天气详情" + weater.size()+ weater.toString());*/
		/*ArrayAdapter<String> citysAdater = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, listcitys);
		citys.setAdapter(citysAdater);

		listcity = WebServiceTest.getCity(citys.getSelectedItem().toString());*/
		/*ArrayAdapter<String> cityAdater = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, listcity);
		city.setAdapter(cityAdater);
		citys.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				listcity = WebServiceTest.getCity(citys.getSelectedItem()
						.toString());
				ArrayAdapter<String> cityAdater1 = new ArrayAdapter<String>(
						MainKsoap2Activity.this,
						android.R.layout.simple_list_item_multiple_choice,
						listcity);
				city.setAdapter(cityAdater1);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		city.setOnItemSelectedListener(new OnItemSelectedListener() {
			// 返回数据： 一个一维数组 String(22)，共有23个元素。
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				weater = WebServiceTest.getWeather(city.getSelectedItem()
						.toString());

				for (int i = 0; i < weater.size(); i++) {
					System.out.println("i=" + i + ":" + weater.get(i));
				}

				cityNames1.setText(weater.get(6) + "\n" + weater.get(5) + "\n"
						+ weater.get(7));
				cityNames1.setBackgroundResource(ChangeImageView.imageId(weater
						.get(8)));
				weateImage1.setImageResource(ChangeImageView.imageId(weater
						.get(8)));
				cityNames2.setText(weater.get(13) + "\n" + weater.get(12) + "\n"
						+ weater.get(14));
				cityNames2.setBackgroundResource(ChangeImageView.imageId(weater
						.get(15)));
				weateImage2.setImageResource(ChangeImageView.imageId(weater
						.get(15)));
				cityNames3.setText(weater.get(18) + "\n" + weater.get(17) + "\n"
						+ weater.get(19));
				cityNames3.setBackgroundResource(ChangeImageView.imageId(weater
						.get(20)));
				weateImage3.setImageResource(ChangeImageView.imageId(weater
						.get(21)));
				cityjie.setText(weater.get(22));


			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});*/

    }

}
