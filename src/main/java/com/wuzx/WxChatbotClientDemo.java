package com.wuzx;

import com.wuzx.message.ImageMessage;
import com.wuzx.message.MarkdownMessage;
import com.wuzx.message.NewsArticle;
import com.wuzx.message.NewsMessage;
import com.wuzx.message.TextMessage;
import com.wuzx.model.ImageBase64Md5;
import com.wuzx.model.SendResult;
import com.wuzx.utils.Base64Utils;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName WxChatbotClientTest.java
 * @Description TODO
 * @createTime 2021年07月26日 16:12:00
 */
public class WxChatbotClientDemo {

    private static String webhook = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=*******";

    /**
     * 网络图片
     * 目前测试有问题，需要ssl链接外网，由于公司网络设置问题
     *
     * @throws Exception
     */
    @Test
    public void imageMessageOnlineTest() throws Exception {

        String string = "https://c-ssl.duitang.com/uploads/blog/202008/22/20200822114634_2b3e0.thumb.1000_0.png";
        ImageBase64Md5 image = Base64Utils.ImageToBase64ByOnline(string);
        ImageMessage imageMessage = new ImageMessage(image.getBase64(),image.getMd5());
        SendResult result = WxChatbotClient.send(webhook, imageMessage);
        System.out.println(result);
    }

    /**
     * 本地图片
     * @throws Exception
     */
    @Test
    public void imageMessageLocalTest() throws Exception {

        String string = "E:\\Users\\admin\\Desktop\\20200514184710.png";
        ImageBase64Md5 image = Base64Utils.ImageToBase64ByLocal(string);
        ImageMessage imageMessage = new ImageMessage(image.getBase64(),image.getMd5());
        SendResult result = WxChatbotClient.send(webhook, imageMessage);
        System.out.println(result);
    }

    @Test
    public void testSendMarkdownMessage() throws Exception {
        MarkdownMessage message = new MarkdownMessage();
        message.add("This is a markdown message");
        message.add(MarkdownMessage.getHeaderText(1, "header 1"));
        message.add(MarkdownMessage.getHeaderText(2, "header 2"));
        message.add(MarkdownMessage.getHeaderText(3, "header 3"));
        message.add(MarkdownMessage.getHeaderText(4, "header 4"));
        message.add(MarkdownMessage.getHeaderText(5, "header 5"));
        message.add(MarkdownMessage.getHeaderText(6, "header 6"));

        message.add(MarkdownMessage.getReferenceText("reference text"));
        message.add("\n\n");

        message.add("normal text");
        message.add("\n\n");

        message.add(MarkdownMessage.getBoldText("Bold Text"));
        message.add("\n\n");

        message.add(MarkdownMessage.getItalicText("Italic Text"));
        message.add("\n\n");

        ArrayList<String> orderList = new ArrayList<String>();
        orderList.add("order item1");
        orderList.add("order item2");
        message.add(MarkdownMessage.getOrderListText(orderList));
        message.add("\n\n");

        ArrayList<String> unorderList = new ArrayList<String>();
        unorderList.add("unorder item1");
        unorderList.add("unorder item2");
        message.add(MarkdownMessage.getUnorderListText(unorderList));
        message.add("\n\n");

        message.add(MarkdownMessage.getImageText("https://c-ssl.duitang.com/uploads/blog/202008/22/20200822114634_2b3e0.thumb.1000_0.png"));
        message.add(MarkdownMessage.getLinkText("百度", "www.baidu.com"));

        SendResult result = WxChatbotClient.send(webhook, message);
        System.out.println(result);
    }

    @Test
    public void testSendMarkdownMessage1() throws Exception {
        MarkdownMessage message = new MarkdownMessage();
        message.add(MarkdownMessage.getHeaderText(3, "有新用户注册啦"));
        message.add(MarkdownMessage.getReferenceText("手机号： "+"183XXXXXXXX"));
        message.add(MarkdownMessage.getInfoColor("用户名： "+"唐三"));
        SendResult result = WxChatbotClient.send(webhook, message);
        System.out.println(result);
    }


    @Test
    public void testSendMarkdownTextColor() throws Exception {
        MarkdownMessage message = new MarkdownMessage();
        message.add(MarkdownMessage.getHeaderText(3, "有新用户注册啦"));
        message.add(MarkdownMessage.getInfoColor("手机号： "+"183XXXXXXXX"));
        message.add(MarkdownMessage.getCommentColor("用户名： "+"唐三"));
        message.add(MarkdownMessage.getWarningColor("公司： "+"优合集团"));
        SendResult result = WxChatbotClient.send(webhook, message);
        System.out.println(result);
    }

    @Test
    public void testSendTextMessageWithAtAndAtAll() throws Exception {
        TextMessage message = new TextMessage("脸给你打歪");
        List<String> mentionedMobileList=new ArrayList<String>();
        mentionedMobileList.add("18702623606");//@群内成员  手机号
        message.setMentionedMobileList(mentionedMobileList);
        //message.setIsAtAll(true);//@所有人
        SendResult result = WxChatbotClient.send(webhook, message);
        System.out.println(result);
    }

    @Test
    public void testSendNewsMessage() throws Exception {

        NewsArticle article=new NewsArticle();
        article.setTitle("斗破");
        article.setDescription("噢噢噢噢");
        article.setUrl("https://work.weixin.qq.com/api/doc#90000/90135/91760");
        article.setPicurl("http://tvax2.sinaimg.cn/large/006raI8cgy1fkd600tgboj31ao0t6tgh.jpg");


        NewsArticle article2=new NewsArticle();
        article2.setTitle("焰灵姬");
        article2.setDescription("咯哦哦哦");
        article2.setUrl("http://www.chengxuz.com/dongman/392.html");
        article2.setPicurl("http://tvax1.sinaimg.cn/large/9afb97dagy1fwh3w0ipjdj21jk0v91kx.jpg");

        NewsArticle article3=new NewsArticle();
        article3.setTitle("鹤熙");
        article3.setDescription("诸神天降");
        article3.setUrl("http://www.chengxuz.com/dongman/543.html");
        article3.setPicurl("http://tvax1.sinaimg.cn/large/9c774d91gy1g4b8iatgtsj21kd0u0kgs.jpg");

        NewsMessage message=new NewsMessage();
        message.addNewsArticle(article);
        message.addNewsArticle(article2);
        message.addNewsArticle(article3);
        SendResult result = WxChatbotClient.send(webhook, message);
        System.out.println(result);
    }
}
