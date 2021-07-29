package com.wuzx.message;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @Description 图文（news）
 * @createTime 2021年07月26日 16:01:00
 */
public class NewsMessage implements Message {
    public static final int MAX_ARTICLE_CNT = 5;
    public static final int MIN_ARTICLE_CNT = 1;

    private List<NewsArticle> articles = new ArrayList<NewsArticle>();

    public void addNewsArticle(NewsArticle newsArticle) {
        if (articles.size() >= MAX_ARTICLE_CNT) {
            throw new IllegalArgumentException("number of articles can't more than " + MAX_ARTICLE_CNT);
        }
        articles.add(newsArticle);
    }

    @Override
    public String toJsonString() {
        Map<String, Object> items = new HashMap<String, Object>();
        items.put("msgtype", "news");

        Map<String, Object> news = new HashMap<String, Object>();
        if (articles.size() < MIN_ARTICLE_CNT) {
            throw new IllegalArgumentException("number of articles can't less than " + MIN_ARTICLE_CNT);
        }
        news.put("articles", articles);
        items.put("news", news);
        return JSON.toJSONString(items);
    }
}


