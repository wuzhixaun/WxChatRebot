package com.wuzx;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.wuzx.message.FileMessage;
import com.wuzx.model.MaterialFileVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName GetTencentFileIdUtils.java
 * @Description 获取上传素材响应信息
 * @createTime 2021年07月28日 16:50:00
 */

public class GetTencentMaterialFileUtils {
    private static final Logger log = LoggerFactory.getLogger(GetTencentMaterialFileUtils.class);

    private static String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/upload_media?key=%s&type=file";
    /**
     * 从腾讯获取上传素材
     * @param filePath
     * @param webHookKey
     * @return
     */
    public static String getMediaId(String filePath, String webHookKey)  {

        if (StringUtils.isAnyEmpty(filePath,webHookKey)) {
            return StringUtils.EMPTY;
        }
        try {
            File file = new File(filePath);
            byte[] bytesArray = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(bytesArray); // read file into bytes[]
            fis.close();
            HttpResponse response = HttpRequest.post(String.format(url, webHookKey))
                    .header("multipart/form-data", "multipart/form-data")
                    .header("Content-Disposition", String.format("form-data; name=\"media\";filename=\"%s\"; filelength=%s", file.getName(), file.length()))
                    .header("Content-Type", "application/octet-stream")
                    .form("key", bytesArray, file.getName())
                    .execute();

            final MaterialFileVo materialFileVo = JSONUtil.toBean(response.body(), MaterialFileVo.class);
            if (null == materialFileVo || materialFileVo.getErrcode() != 0) {
                return StringUtils.EMPTY;
            }
            return materialFileVo.getMediaId();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("GetTencentMaterialFileUtils getMediaId IOException {}", e.getMessage());
        }
        return null;
    }


    public static void main(String[] args) {
        // 机器人对应key
        String webhook = "**********";

        final String mediaId = getMediaId("E:\\Users\\admin\\Desktop\\test.xlsx", webhook);
        if (StringUtils.isEmpty(mediaId)) {
            return;
        }

        FileMessage fileMessage = new FileMessage(mediaId);
        WxChatbotClient.send(webhook, fileMessage);
    }
}
