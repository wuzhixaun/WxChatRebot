package com.wuzx.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.wuzx.WxChatbotClient;
import com.wuzx.contants.RobotConstants;
import com.wuzx.message.FileMessage;
import com.wuzx.model.MaterialFileVo;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName GetTencentFileIdUtils.java
 * @Description 获取上传素材响应信息
 * @createTime 2021年07月28日 16:50:00
 */

public class GetTencentMaterialFileUtils {

    /**
     * 从腾讯获取上传素材
     *
     * @param bytes
     * @param webHookKey
     * @return
     */
    public static String getMediaId(byte[] bytes, String fileName, String webHookKey) {
        HttpResponse response = HttpRequest.post(String.format(RobotConstants.UPLOAD_MEDIA_URL, webHookKey))
                .header("Content-Type", "multipart/form-data")
                .header("Content-Disposition", String.format("form-data; name=\"media\";filename=\"%s\"; filelength=%s", fileName, bytes.length))
                .header("Content-Type", "application/octet-stream")
                .form("key", bytes, fileName)
                .execute();
        final MaterialFileVo materialFileVo = JSONUtil.toBean(response.body(), MaterialFileVo.class);
        if (null == materialFileVo || materialFileVo.getErrcode() != 0) {
            return StringUtils.EMPTY;
        }
        return materialFileVo.getMediaId();
    }

    /**
     * 从腾讯获取上传素材
     *
     * @param file
     * @param webHookKey
     * @return
     */
    public static String getMediaId(File file, String webHookKey) {
        return getMediaId(getBytes(file), file.getName(), webHookKey);
    }


    public static void main(String[] args) {
        String webhook = "60689f1f-1dd1-49d6-bd6d-e03a777f3121";

        final String mediaId = getMediaId(getBytes("E:\\Users\\admin\\Desktop\\天若OCR开源版V5.0.0.rar"), "天若OCR开源版V5.0.0.rar", webhook);
        if (StringUtils.isEmpty(mediaId)) {
            return;
        }

        FileMessage fileMessage = new FileMessage(mediaId);
        WxChatbotClient.send(webhook, fileMessage);
    }


    /**
     * 获得指定文件的byte数组
     *
     * @param file
     * @return
     */
    public static byte[] getBytes(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 通过路径获得指定文件的byte数组
     *
     * @param filePath
     * @return
     */
    public static byte[] getBytes(String filePath) {
        return getBytes(new File(filePath));
    }

}
