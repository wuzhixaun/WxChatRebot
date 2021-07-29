package com.wuzx.message;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @Description 附带链接
 * @createTime 2021年07月26日 16:01:00
 */
public class LinkMessage implements Message {

    private String title;
    private String text;
    private String picUrl;
    private String messageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }


    public String toJsonString() {
        Map<String, Object> items = new HashMap<String, Object>();
        items.put("msgtype", "link");

        Map<String, String> linkContent = new HashMap<String, String>();
        if (StringUtils.isBlank(title)) {
            throw new IllegalArgumentException("title should not be blank");
        }
        linkContent.put("title", title);

        if (StringUtils.isBlank(messageUrl)) {
            throw new IllegalArgumentException("messageUrl should not be blank");
        }
        linkContent.put("messageUrl", messageUrl);

        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException("text should not be blank");
        }
        linkContent.put("text", text);

        if (StringUtils.isNotBlank(picUrl)) {
            linkContent.put("picUrl", picUrl);
        }

        items.put("link", linkContent);

        return JSON.toJSONString(items);
    }
}
