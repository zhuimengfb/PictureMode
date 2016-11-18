package com.fbi.picturemode.utils;

import android.content.Context;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 9/1/16
 */
public class ShareUtil {

  private String title;
  private String imageUrl;
  private String text;
  private String url;
  private String comment;
  private String titleUrl;
  private String site;
  private String siteUrl;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getTitleUrl() {
    return titleUrl;
  }

  public void setTitleUrl(String titleUrl) {
    this.titleUrl = titleUrl;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public String getSiteUrl() {
    return siteUrl;
  }

  public void setSiteUrl(String siteUrl) {
    this.siteUrl = siteUrl;
  }

  public static ShareUtil wxShare(String title, String iconUrl, String content, String url) {
    return new Builder().title(title)
        .imageUrl(iconUrl)
        .text(content)
        .url(url)
        .create();
  }

  public void share(Context context) {
    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
    oks.setTitle(title);
    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    oks.setTitleUrl(titleUrl);
    // text是分享文本，所有平台都需要这个字段
    oks.setText(text);
    //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
    oks.setImageUrl(imageUrl);
    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
    // url仅在微信（包括好友和朋友圈）中使用
    oks.setUrl(url);
    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
    oks.setComment(comment);
    // site是分享此内容的网站名称，仅在QQ空间使用
    oks.setSite(site);
    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    oks.setSiteUrl(siteUrl);
    // 启动分享GUI
    oks.show(context);
  }

  public static class Builder {
    private ShareUtil shareUtil;

    public Builder() {
      shareUtil = new ShareUtil();
    }

    public Builder title(String title) {
      shareUtil.setTitle(title);
      return this;
    }

    public Builder imageUrl(String imageUrl) {
      shareUtil.setImageUrl(imageUrl);
      return this;
    }

    public Builder text(String text) {
      shareUtil.setText(text);
      return this;
    }

    public Builder url(String url) {
      shareUtil.setUrl(url);
      return this;
    }

    public Builder comment(String comment) {
      shareUtil.setComment(comment);
      return this;
    }

    public Builder titleUrl(String titleUrl) {
      shareUtil.setTitleUrl(titleUrl);
      return this;
    }

    public Builder site(String site) {
      shareUtil.setSite(site);
      return this;
    }

    public Builder siteUrl(String siteUrl) {
      shareUtil.setSiteUrl(siteUrl);
      return this;
    }

    public ShareUtil create() {
      return shareUtil;
    }
  }
}
