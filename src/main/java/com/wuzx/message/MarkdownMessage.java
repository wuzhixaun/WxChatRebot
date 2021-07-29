package com.wuzx.message;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @Description markdown（markdown）
 * @createTime 2021年07月26日 16:01:00
 */
public class MarkdownMessage implements Message {

    private List<String> items = new ArrayList<String>();

    public void add(String text) {
        items.add(text);
    }

    /**
     * <font color="green">info字体</font>
     *
     * @param text
     * @return
     */
    public static String getInfoColor(String text) {
        return getColorText("info", text);
    }

    /**
     * <font color="grey">Comment字体</font>
     *
     * @param text
     * @return
     */
    public static String getCommentColor(String text) {
        return getColorText("comment", text);
    }

    /**
     * <font color="D9D919">warning字体【金色】</font>
     *
     * @param text
     * @return
     */
    public static String getWarningColor(String text) {
        return getColorText("warning", text);
    }


    private static String getColorText(String color,String text) {
        String font = "<font color=\"%s\">%s</font>";
        return String.format(font, color, text);
    }

    /**
     * 加粗
     * @param text
     * @return
     */
    public static String getBoldText(String text) {
        return "**" + text + "**";
    }

    public static String getItalicText(String text) {
        return "*" + text + "*";
    }

    /**
     * 链接
     * @param text
     * @param href
     * @return
     */
    public static String getLinkText(String text, String href) {
        return "[" + text + "](" + href + ")";
    }

    /**
     * 图片类型
     * @param imageUrl
     * @return
     */
    public static String getImageText(String imageUrl) {
        return "![image](" + imageUrl + ")";
    }

    /**
     * 标题
     * @param headerType
     * @param text
     * @return
     */
    public static String getHeaderText(int headerType, String text) {
        if (headerType < 1 || headerType > 6) {
            throw new IllegalArgumentException("headerType should be in [1, 6]");
        }

        StringBuffer numbers = new StringBuffer();
        for (int i = 0; i < headerType; i++) {
            numbers.append("#");
        }
        return numbers + " " + text;
    }

    /**
     * 引用
     * @param text
     * @return
     */
    public static String getReferenceText(String text) {
        return "> " + text;
    }

    /**
     * 代码消息
     * @param text
     * @return
     */
    public static String getCodeText(String text) {
        return "`" + text + "`";
    }

    /**
     * 有序列表
     * @param orderItem
     * @return
     */
    public static String getOrderListText(List<String> orderItem) {
        if (orderItem.isEmpty()) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= orderItem.size() - 1; i++) {
            sb.append(String.valueOf(i) + ". " + orderItem.get(i - 1) + "\n");
        }
        sb.append(String.valueOf(orderItem.size()) + ". " + orderItem.get(orderItem.size() - 1));
        return sb.toString();
    }

    /**
     * 无序列表
     * @param unorderItem
     * @return
     */
    public static String getUnorderListText(List<String> unorderItem) {
        if (unorderItem.isEmpty()) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < unorderItem.size() - 1; i++) {
            sb.append("- " + unorderItem.get(i) + "\n");
        }
        sb.append("- " + unorderItem.get(unorderItem.size() - 1));
        return sb.toString();
    }

    @Override
    public String toJsonString() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("msgtype", "markdown");

        Map<String, Object> markdown = new HashMap<String, Object>();

        StringBuffer markdownText = new StringBuffer();
        for (String item : items) {
            markdownText.append(item + "\n");
        }
        markdown.put("content", markdownText.toString());
        result.put("markdown", markdown);

        return JSON.toJSONString(result);
    }
}
