package com.chenfei.community.provider;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chenfei.community.exception.CustomizeErrorCode;
import com.chenfei.community.exception.CustomizeException;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;

@Service
public class UcloudProvider {
	
	@Value("${ucloud.ufile.public-key}")
	private String publicKey ;
	
	@Value("${ucloud.ufile.private-key}")
	private String privateKey ;
	
	
	private final String bucketName = "manong-cf" ;
	
	
	public String upload(InputStream fileStream , String mineType, String fileName) {
		String generateFileName;
		String[] filePaths = fileName.split("\\.");
		if(filePaths.length > 1) {
			generateFileName = UUID.randomUUID().toString() + "." +filePaths[filePaths.length - 1];
		}else {
			throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
		}
		
		try {
			ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
			ObjectConfig config = new ObjectConfig("cn-bj", "ufileos.com");
		    PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
		         .putObject(fileStream, mineType)
		         .nameAs(generateFileName)
		         .toBucket(bucketName)
		         /**
		          * 是否上传校验MD5, Default = true
		          */
		     //  .withVerifyMd5(false)
		         /**
		          * 指定progress callback的间隔, Default = 每秒回调
		          */
		     //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
		         /**
		          * 配置进度监听
		          */
		         .setOnProgressListener((bytesWritten,contentLength )-> {
		         })
		         .execute();
		    if(response != null && response.getRetCode()==0) {
		    	String url = UfileClient.object(objectAuthorization, config)
		    			.getDownloadUrlFromPrivateBucket(generateFileName, bucketName, 24 * 60 *60 *60 *10)
		    			.createUrl();		
		    	return url ;
		    	}else {
		    		throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
		    	}
		} catch (UfileClientException e) {
		    e.printStackTrace();
		    throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
		} catch (UfileServerException e) {
		    e.printStackTrace();
		    throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
		}
	}
}
