package com.wuzx.model;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName MaterialFileVo.java
 * @Description 上传素材到腾讯【要求文件大小在5B~20M之间】
 * @createTime 2021年07月28日 16:44:00
 */

public class MaterialFileVo {
    /**
     * 错误码，0为成功
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * 媒体文件类型: 分别有图片（image）、语音（voice）、视频（video），普通文件(file)
     */
    private String type;

    /**
     * 媒体文件上传后获取的唯一标识，3天内有效
     */
    private String mediaId;

    /**
     * 媒体文件上传时间戳
     */
    private String createdAt;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MaterialFileVo{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                ", type='" + type + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
