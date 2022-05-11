package com.wuzx.contants;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName RobotConstants.java
 * @Description 企业微信机器人常量
 * @createTime 2022年05月11日 10:53:00
 */
public class RobotConstants {

    /**
     * 机器人webhook地址
     */
    public final static String ROBOT_PATH = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=%s";

    /**
     * uploadMedia URL
     */
    public final static String UPLOAD_MEDIA_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/upload_media?key=%s&type=file";

}
