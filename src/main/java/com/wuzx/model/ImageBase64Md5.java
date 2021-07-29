package com.wuzx.model;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @Description ImageBase64Md5 model
 * @createTime 2021年07月26日 16:01:00
 */
public class ImageBase64Md5 {

    private String base64;
    private String md5;

    public ImageBase64Md5(String base64, String md5) {
        this.base64 = base64;
        this.md5 = md5;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

}
