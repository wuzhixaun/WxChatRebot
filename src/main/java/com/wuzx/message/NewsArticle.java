package com.wuzx.message;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName NewsArticle.java
 * @Description 文章标题
 * @createTime 2021年07月26日 17:15:00
 */
public class NewsArticle {
    private String title;
    private String description;
    private String picurl;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
