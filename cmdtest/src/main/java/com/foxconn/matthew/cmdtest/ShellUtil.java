package com.foxconn.matthew.cmdtest;

import android.content.Intent;
import android.util.Log;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * 
 * @author Kenneth 执行shell(root)命令
 *
 */
public final class ShellUtil {

	private static final String TAG = "ShellUtil";
	private static boolean haveRoot = false;
	private static Process process=null;

	// 判断机器Android是否已经root，即是否获取root权限
	public static boolean haveRoot() {
		if (!haveRoot) {
			int ret = exec("echo test"); // 通过执行测试命令来检测
			if (ret != -1) {
				Log.i(TAG, "have root!");
				haveRoot = true;
			} else {
				Log.i(TAG, "not root!");
			}
		} else {
			Log.i(TAG, "mHaveRoot = true, have root!");
		}
		return haveRoot;
	}

	public static int exec(String cmd) {
		int result = -1;
		DataOutputStream dos = null;
		try {
			process = Runtime.getRuntime().exec("su");
			dos = new DataOutputStream(process.getOutputStream());
			Log.e(TAG, "exec: =====>>"+(dos==null) );
			dos.writeBytes(cmd + "\n");
			dos.flush();
			dos.writeBytes("exit\n");
			dos.flush();
			clearCache(process.getErrorStream());
			clearCache(process.getInputStream());
			process.waitFor();	
			result = process.exitValue();
			Log.e(TAG, "exec: result---->"+result );
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(process!=null){
				process.destroy();
				process=null;
			}
		}
		return result;
	}
	
	public static void execReboot(String toolboxpath) {
		Intent intent=new Intent("cn.fxn.fsvmpczr.apkinstall");
		intent.putExtra("path",toolboxpath);
		MyApp.getContext().sendBroadcast(intent);
	}
	
	public static void execSuper(String cmd) {
		SystemPartition.remountSystem(true);
		exec(cmd);
	}
	
	// 隐藏系统下方的虚拟按键，需每2.5秒结束com.android.systemui任务
	@Deprecated
	public static void hideSystemUI() {
		exec("busybox killall com.android.systemui");
	}

	// 移除系统下方的虚拟按键，需重启生效
	/*public static void removeSystemUI() {
		String buildProp = "/system/build.prop";
		String buildPropBak = "/system/build.prop#";

		// 备份文件
		execSuper("cp " + buildProp + " " + buildPropBak);

		chmod(666, buildProp);
		String key = Cfg.getPropertyByName(buildProp, "qemu.hw.mainkeys");
		if (null == key || key.equals("") || key.equals("null")) {
			System.out.println("移除系统虚拟按键，重启后生效");
			// 用Cfg修改prop可能打乱原有顺序和编码格式
			execSuper("echo qemu.hw.mainkeys=1 >> " + buildProp);
		}
		chmod(644, buildProp);
	}*/

	// 恢复系统下方的虚拟按键，需重启生效
	/*public static void resumeSystemUI() {
		String buildProp = "/system/build.prop";
		chmod(666, buildProp);
		String key = Cfg.getPropertyByName(buildProp, "qemu.hw.mainkeys");
		if (null != key && key.equals("1"))
		{
			System.out.println("恢复系统虚拟按键，重启后生效");
			FileUtil.replaceLine(buildProp, "qemu.hw.mainkeys=1", "");
		}
		chmod(644, buildProp);
	}*/

	// Shell命令重启系统，程序数据可能无法保存
	public static void restartSystem() {
		//reboot(MyApp.getContext());
		exec("reboot");
	}
	
	/** 针对研华IPC无法重启的情况!!!   By Kenneth 2016.09.27 **/
	/*public static void reboot(Context context)
	{
		String filePath = context.getFilesDir().getAbsolutePath();
		String toolboxPath = filePath + "/toolbox";
		if (!new File(toolboxPath).exists())
		{
			FileUtil.copyAssetFileToFiles(context, "toolbox", filePath);
			ShellUtil.chmod(777, toolboxPath);
		}
//		ShellUtil.exec(toolboxPath + " reboot");
		ShellUtil.execReboot(toolboxPath);
	}*/

	// 修改文件权限(666或644)
	public static void chmod(int permission, String filePath) {
		execSuper("chmod " + permission + " " + filePath);
	}
	
	public static void clearCache(final InputStream in){
		/*if(logger==null){
			logger=LogPcComm.getLogger(ShellUtil.class);
		}*/
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				BufferedReader reader=new BufferedReader(new InputStreamReader(in));
				try {
					String line="";
					while((line=reader.readLine())!=null){
						Log.e(TAG, "run: "+line );
					}
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}).start();
	}
}