package com.example.barbershopsystem.service.impl;

import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.handle.SystemException;
import com.example.barbershopsystem.service.UploadService;
import com.example.barbershopsystem.utils.PathUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;




@Service
@Data
@ConfigurationProperties("oss")
public class UploadServiceImpl implements UploadService {
    /*图片上传*/
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
       /* if (!originalFilename.endsWith(".png")){
            throw new SystemException(AppHttpCodeEnum.ImG_ERROR);
        }*/
        //判断通过，上传到oos
        String filePath = PathUtil.generateFilePath(originalFilename);
        String url = uploadOos(img, filePath);
        return ResponseResult.okResult(url);
    }
    /*上传文件到oos*/
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String uploadOos(MultipartFile imgFile, String filePath) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "QvAPUTBbTwNb-CaK71BT2dhqj_GDTw82w57Iblq-";
        String secretKey = "f_LTed2-loTcEWLC8JQekTa1GVUqTo2ANP6JS8Io";
        String bucket = "xwlbs";
        //默认不指定key的情况下，以文件内容的hash值作为文件名

        String key = filePath;
        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                return "http://sdkgnltb4.hn-bkt.clouddn.com/"+key;


            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "上传失败";
    }
}