package com.foxconn.matthew.cmdtest;

import android.util.Log;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SystemPartition {
	private static final String TAG = "SystemPartition";
	private static String TMP_PATH = "sdcard/mount.txt";
	private static String mountPiont = null;
	
	private SystemPartition() {
		Log.i(TAG, "new SystemMount()");
	}
	
	private static class SystemPartitionHolder {
		private static SystemPartition instance = new SystemPartition();
	}
	
	public SystemPartition getInstance() {
		return SystemPartitionHolder.instance;
	}
	
	public static void getSystemMountPiont() {
		DataInputStream dis = null;
		if (mountPiont == null) { 
			try {
				ShellUtil.exec("mount > " + TMP_PATH);
				dis = new DataInputStream(new FileInputStream(TMP_PATH));
				String line = null;
				int index = -1;
				while ( (line = dis.readLine()) != null ) {
					Log.i(TAG, line);
					index = line.indexOf(" /system ");
					if (index > 0) {
						mountPiont = line.substring(0, index);
						if (line.indexOf(" rw") > 0) {
							Log.i(TAG, "/system is writeable !");
						} else {
							Log.i(TAG, "/system is readonly !");
						}
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (dis != null) {
					try {
						dis.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					dis = null;
				}
				
				File f = new File(TMP_PATH);
				if (f.exists()) {
					f.delete();
				}
			}
		}
		
		if (mountPiont != null) {
			Log.i(TAG, "/system mount piont: " + mountPiont);
		} else {
			Log.i(TAG, "get /system mount piont failed !");
		}
	}
	
	public static void isWriteable() {
		mountPiont = null;
		getSystemMountPiont();
	}
	
	public static void remountSystem(boolean writeable) {
		String cmd = null;
		getSystemMountPiont();
		if (mountPiont != null && ShellUtil.haveRoot()) {
			if (writeable) {
				cmd = "mount -o remount,rw " + mountPiont + " /system";
			} else {
				cmd = "mount -o remount,ro " + mountPiont + " /system";
				//cmd = "mount -o remount,rw mmcblk0p10 /system";
			}
			ShellUtil.exec(cmd);
			
			isWriteable();
		}
	}
}
