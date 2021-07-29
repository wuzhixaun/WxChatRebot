package com.wuzx.message;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName FileMessage.java
 * @Description 文件消息
 * @createTime 2021年07月28日 17:26:00
 */
public class FileMessage implements Message {

    private String type = "file";
    private String mediaId;


    public FileMessage(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toJsonString() {
        Map<String, Object> items = new HashMap();
        items.put("msgtype", "file");

        Map<String, String> file = new HashMap();
        if (StringUtils.isBlank(mediaId)) {
            throw new IllegalArgumentException("mediaId should not be blank");
        }
        file.put("media_id", mediaId);
        items.put("file", file);

        return JSON.toJSONString(items);
    }
}
