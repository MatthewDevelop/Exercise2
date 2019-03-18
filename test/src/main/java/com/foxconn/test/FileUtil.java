package com.foxconn.test;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class FileUtil {


	static int countFiles = 0;

	
	public static String getSDcardPath() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
						return Environment.getExternalStorageDirectory().getPath();
		}
		return null;
	}

	
	public static String getDataPath() {
		return Environment.getDataDirectory().getAbsolutePath();
	}

	
	public static String getTFcardPath() {
		String path = null;
		try {
			InputStream ins = Runtime.getRuntime().exec("mount")
					.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ins));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line.contains("sdcard")) {
					if (line.contains("vfat") || line.contains("fuse")) {
						String split[] = line.split(" ");
						path = split[1];
					}
				}
			}
			reader.close();
			ins.close();

		} catch (IOException e) {
			return null;
		}
		return path;
	}

	
	@SuppressWarnings("deprecation")
	public static String getPathSizeInfo(String path) {
		if (null != path) {
			StatFs sf = new StatFs(path);
			long blockSize = sf.getBlockSize();
			long blockCount = sf.getBlockCount();
			long availCount = sf.getAvailableBlocks();
			return "共" + blockSize * blockCount / 1024 / 1024 + "MB" + ", 剩"
					+ availCount * blockSize / 1024 / 1024 + "MB";
		}
		return "未挂载";
	}

	
	public static void mkdirs(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			if (dir.mkdirs() == false) {
				System.err.println("+++ " + dirPath + " 文件夹创建失败!");
			}
		}
	}

	
	public static File[] searchFile(File folder, final String keyword) {
		File[] subFolders = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isFile())
					countFiles++;
				if (pathname.isFile() && pathname.getName().contains(keyword)) {
					return true;
				}
				return false;
			}
		});

		return subFolders;
	}
	
	
	public static File[] searchFileWithRegexp(File folder, final String regexp) {
		File[] subFolders = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isFile())
					countFiles++;
				if (pathname.isFile() && pathname.getName()
						.toLowerCase(Locale.getDefault()).matches(regexp)) {
					return true;
				}
				return false;
			}
		});

		return subFolders;
	}

		public static void copyFile(String oldPath, String newPath) {
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { 				inStream = new FileInputStream(oldPath); 				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; 					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void copy(String path, String copyPath) throws IOException {
		File filePath = new File(path);
		DataInputStream read;
		DataOutputStream write;
		if (filePath.isDirectory()) {
			File[] list = filePath.listFiles();
			System.out.println("dl video file size:" + list.length);
			for (int i = 0; i < list.length; i++) {
				String newPath = "", newCopyPath = "";
				if (path.endsWith(File.separator)) {
					newPath = path + list[i].getName();
				} else {
					newPath = path + File.separator + list[i].getName();
				}
				if (newCopyPath.endsWith(File.separator)) {
					newCopyPath = copyPath + list[i].getName();
				} else {
					newCopyPath = copyPath + File.separator + list[i].getName();
				}
				System.out.println("newPath:" + newPath);
				System.out.println("newCopyPath:" + newCopyPath);
				File newFile = new File(copyPath);
				if (!newFile.exists()) {
					newFile.mkdir();
				}
				copy(newPath, newCopyPath);
			}
		} else if (filePath.isFile()) {
			System.out.println("path:" + path);
			System.out.println("copyPath:" + copyPath);
			read = new DataInputStream(new BufferedInputStream(
					new FileInputStream(path)));
			write = new DataOutputStream(new BufferedOutputStream(
					new FileOutputStream(copyPath)));
			byte[] buf = new byte[1024 * 512];
			while (read.read(buf) != -1) {
				write.write(buf);
			}
			read.close();
			write.close();
		} else {
			
		}
	}

	
	public static void delAllFileInFolder(String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				return;
			}
			if (!file.isDirectory()) {
				return;
			}
			String[] tempList = file.list();

			File temp = null;
			for (int i = 0; i < tempList.length; i++) {
				if (path.endsWith(File.separator)) {
					temp = new File(path + tempList[i]);
				} else {
					temp = new File(path + File.separator + tempList[i]);
				}
				System.out.println(temp);
				if (temp.isFile()) {
					temp.delete();
				}
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		}
	}

	
	public static File buildFile(String fileName, boolean isDirectory) {
		File target = new File(fileName);
		if (isDirectory) {
			target.mkdirs();
		} else {
			if (!target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
				target = new File(target.getAbsolutePath());
			}
		}
		return target;
	}

	
	public static void copyAssetFileToFiles(Context context, String fileName, String desDirPath) {
		try {
			InputStream is = context.getAssets().open(fileName);
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			is.close();

			File of = new File(desDirPath + "/" + FileUtil.getFileName(fileName));
			of.createNewFile();
			FileOutputStream os = new FileOutputStream(of);
			os.write(buffer);
			os.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	
	public static void copyAssetDirToFiles(Context context, String dirName, String desDirPath) {
		mkdirs(desDirPath);
		AssetManager assetManager = context.getAssets();
		try {
			String[] children = assetManager.list(dirName);
			if (children.length != 0) {
				for (String child : children) {
					child = dirName + '/' + child;
					String[] grandChildren = assetManager.list(child);
					if (0 == grandChildren.length) {
						copyAssetFileToFiles(context, child, desDirPath);
					} else {
						copyAssetDirToFiles(context, child, desDirPath);
					}
				}
				System.out.println("+++ /assets/" + dirName + "文件夹拷贝完成");
			} else {
				System.out.println("+++ /assets/" + dirName + "文件夹子文件个数为0");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void copyDataBaseDir(Context context, String dirName){
		System.out.println("dirdir:"+context.getFilesDir() + "/" + dirName);
		try {
			copy(context.getFilesDir() + "/" + dirName, "/data/data/cn.fxn.fsvmpczr_ui/databases/");
		} catch (IOException e) {
						e.printStackTrace();
		}
	}
	
	public static String getFileName(String filePath)
	{
		return filePath.substring(
				filePath.lastIndexOf(System.getProperty("file.separator")) + 1);
	}
	
	public static String readFile(String filePath)
	{
		File file = new File(filePath);
		FileInputStream fis = null;
		if (file.exists())
		{
			try {
				fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				StringBuilder sb = new StringBuilder();
				String line = null;
								while ((line = br.readLine()) != null)
				{
					sb.append(line + "\n");
				}
								br.close();
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			System.err.println("+++ " + filePath + "文件不存在");
		}
		return null;
	}
	
	public static void writeFile(String filePath, String content)
	{
		File file = new File(filePath);
		if (file.exists())
		{
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
				fos.write(content.getBytes());  
			    fos.close();  
			} catch (Exception e) {
				e.printStackTrace();
			}  
		}
		else
		{
			System.err.println("+++ " + filePath + "文件不存在");
		}
	}
	
	public static void replaceLine(String filePath, String oldContent, String newContent)
	{
		String content = readFile(filePath);
		if (null != content)
		{
			content = content.replace(oldContent, newContent);
						if (content.endsWith("\n\n"))
			{
								content = content.substring(0, content.length() - 1);
			}
			writeFile(filePath, content);
		}
	}
	
}
