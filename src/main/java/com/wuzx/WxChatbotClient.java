package com.wuzx;

import com.alibaba.fastjson.JSONObject;
import com.wuzx.contants.RobotConstants;
import com.wuzx.message.Message;
import com.wuzx.model.SendResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *
 * api <a href='https://work.weixin.qq.com/api/doc/90000/90136/91770'>接口地址</>
 * @author wuzhixuan
 * @version 1.0.0
 * @Description 企业微信机器人发送消息
 * @createTime 2021年07月26日 16:01:00
 */
public class WxChatbotClient {
    private final static Logger logger = LoggerFactory.getLogger(WxChatbotClient.class);


    static HttpClient httpclient = HttpClients.createDefault();

    public static SendResult send(String webHookKey, Message message)  {
        logger.info("WxChatbotClient send webhook {} message {} ", webHookKey, message);
        if (StringUtils.isBlank(webHookKey)) {
            logger.info("WxChatbotClient send webHookKey is empry");
            return new SendResult();
        }
        try {

            HttpPost httppost = new HttpPost(String.format(RobotConstants.ROBOT_PATH, webHookKey));
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            StringEntity se = new StringEntity(message.toJsonString(), "utf-8");
            httppost.setEntity(se);

            SendResult sendResult = new SendResult();
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity());
                JSONObject obj = JSONObject.parseObject(result);

                Integer errcode = obj.getInteger("errcode");
                sendResult.setErrorCode(errcode);
                sendResult.setErrorMsg(obj.getString("errmsg"));
                sendResult.setIsSuccess(errcode.equals(0));
            }
            return sendResult;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("WxChatbotClient send IOException {}", e.getMessage());
        }
        return null;
    }
}


