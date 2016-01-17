package com.coder.who;

/**
 * Created by QiWangming on 2016/1/16.
 * Blog: www.jycoder.com
 * GitHub: msAndroid
 */
public class Item {

    /**
     * 主要包括背景图片，header图片和描述
     */

    private int bgImage;
    private int hrImage;
    private String desc;

    /**
     * Constructs a new instance of {@code Object}.
     */
    public Item() {
        super();
    }

    public Item(int bg,int hr,String dc) {
        this.bgImage = bg;
        this.hrImage = hr;
        this.desc = dc;
    }

    public int getBgImage() {
        return bgImage;
    }

    public int getHrImage() {
        return hrImage;
    }

    public String getDesc() {
        return desc;
    }

    public void setBgImage(int bgImage) {
        this.bgImage = bgImage;
    }

    public void setHrImage(int hrImage) {
        this.hrImage = hrImage;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
