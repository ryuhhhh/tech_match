package com.techmatch.base.common.utility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.techmatch.base.exception.TechMatchValidationException;

public class TechMatchImageUtil {
	/**
	 * ファイルチェックOKだったら拡張子を返す
	 * @param multipartFile
	 * @return
	 * @throws TechMatchValidationException
	 */
	public static String fileCheck(MultipartFile multipartFile) throws TechMatchValidationException {
		Map<String,String> map = new HashMap<>();
		// 存在チェック
		if(multipartFile.isEmpty()) {
			map.put("ファイル","ファイルを入力してください");
			throw new TechMatchValidationException(map);
		}
		// content-typeのチェック
		List<String> allowedExtention = Arrays.asList("jpeg","jpg","png");
		String  extention = multipartFile.getContentType().substring(multipartFile.getContentType().lastIndexOf("/")+1);
		if(!allowedExtention.contains(extention)) {
			map.put("拡張子","jpeg jpg pngにしてください");
		}
		// ファイルサイズのチェック
		System.out.println(multipartFile.getSize());
		if(multipartFile.getSize()>5000000) {
			map.put("ファイルサイズ","5MB以下にしてください");
		}
		if(map.size()>0) {
			throw new TechMatchValidationException(map);
		}
		return extention;
	}

	/**
	 * ファイルの削除
	 * @param fileFullPath
	 * @return 存在可否
	 */
	public static boolean deleteFile(String fileFullPath) {
		File exisitedFile = new File(fileFullPath);
		if(exisitedFile.exists()) {
			exisitedFile.delete();
			return true;
		}
		return false;
	}

	/**
	 * ファイルの作成
	 * @param multipartFile
	 * @param filePath
	 * @param fileName
	 * @throws IOException
	 */
	public static void createFile(MultipartFile multipartFile,String filePath,String fileName) throws IOException{
		try {
			byte[] bytes = multipartFile.getBytes();
			BufferedOutputStream uploadFileStream =
	                new BufferedOutputStream(new FileOutputStream(new File(filePath+"/"+fileName)));
			uploadFileStream.write(bytes);
			uploadFileStream.close();
		}catch(IOException e) {
			throw e;
		}
	}
}
