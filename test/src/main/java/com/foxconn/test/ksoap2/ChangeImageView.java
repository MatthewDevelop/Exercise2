package com.foxconn.test.ksoap2;

import com.foxconn.test.R;

public class ChangeImageView {
	// 由于下载下来的图片分 大中小 三种 而调用远程webserivce获取的值是小的图片名 所以的 根据获取的图片名称来获取向对应的大图片ID
	public static int imageId(String ids) {

		int id = R.drawable.a_0;
		int ided =Integer.parseInt(ids.substring(0, ids.indexOf(".")));
		switch (ided) {
			case 1:
				id = R.drawable.a_1;
				break;
			case 2:
				id = R.drawable.a_2;
				break;
			case 3:
				id = R.drawable.a_3;
				break;
			case 4:
				id = R.drawable.a_4;
				break;
			case 5:
				id = R.drawable.a_5;
				break;
			case 6:
				id = R.drawable.a_6;
				break;
			case 7:
				id = R.drawable.a_7;
				break;
			case 8:
				id = R.drawable.a_8;
				break;
			case 9:
				id = R.drawable.a_9;
				break;
			case 10:
				id = R.drawable.a_10;
				break;
			case 11:
				id = R.drawable.a_11;
				break;
			case 12:
				id = R.drawable.a_12;
				break;
			case 13:
				id = R.drawable.a_13;
				break;
			case 14:
				id = R.drawable.a_1;
				break;
			case 15:
				id = R.drawable.a_15;
				break;
			case 16:
				id = R.drawable.a_16;
				break;
			case 17:
				id = R.drawable.a_17;
				break;
			case 18:
				id = R.drawable.a_18;
				break;
			case 19:
				id = R.drawable.a_19;
				break;
			case 20:
				id = R.drawable.a_20;
				break;
			case 21:
				id = R.drawable.a_21;
				break;
			case 22:
				id = R.drawable.a_22;
				break;
			case 23:
				id = R.drawable.a_23;
				break;
			case 24:
				id = R.drawable.a_24;
				break;
			case 25:
				id = R.drawable.a_25;
				break;
			case 26:
				id = R.drawable.a_26;
				break;
			case 27:
				id = R.drawable.a_27;
				break;
			case 28:
				id = R.drawable.a_28;
				break;
			case 29:
				id = R.drawable.a_29;
				break;
			case 30:
				id = R.drawable.a_30;
				break;
		}
		return id;
	}
}
