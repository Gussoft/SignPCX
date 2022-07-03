package com.gussoft.signpcx.models;

import java.io.File;

public class SignatureParameters {

    private String x;
    private String y;
    private String height;
    private String width;
    private int page;
    private int textSize;
    private File file;

    public SignatureParameters() {
    }

    public SignatureParameters(String x, String y, String height, String width, int page, int textSize) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.page = page;
        this.textSize = textSize;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
