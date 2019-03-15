package com.tiny.common.entity;


import com.tiny.common.base.ToString;

import java.util.List;

public class SignatureConfig extends ToString {

    protected int totalWidth;

    protected int totalHeight;

    protected int paddingTop;

    protected int paddingRight;

    protected int paddingBottom;

    protected int paddingLeft;

    protected int borderWidth;

    protected int borderColorR = 0;

    protected int borderColorG = 0;

    protected int borderColorB = 0;

    protected int signatureWidth;

    protected int signatureHeight;

    protected int signatureMax;

    protected int signatureMin;

    protected String signatureDir;


    protected int markBorderColorR = 0;

    protected int markBorderColorG = 0;

    protected int markBorderColorB = 0;

    protected int markRectPaddingTop = 0;
    protected int markRectPaddingBottom = 0;
    protected int markRectPaddingLeft = 0;
    protected int markRectPaddingRight = 0;

    protected int noiseBottomProbability;
    protected int noiseLeftProbability;
    protected int noiseRightProbability;
    protected int noiseTopProbability;

    protected String mockDataPath;

    protected boolean mockDataHeadShuffle;

    protected List<String> fixedStructureJsonList;

    protected boolean fixedStructureEnable;

    protected int retryCount = 3;

    public int getTotalWidth() {
        return totalWidth;
    }

    public void setTotalWidth(int totalWidth) {
        this.totalWidth = totalWidth;
    }

    public int getTotalHeight() {
        return totalHeight;
    }

    public void setTotalHeight(int totalHeight) {
        this.totalHeight = totalHeight;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public int getBorderColorR() {
        return borderColorR;
    }

    public void setBorderColorR(int borderColorR) {
        this.borderColorR = borderColorR;
    }

    public int getBorderColorG() {
        return borderColorG;
    }

    public void setBorderColorG(int borderColorG) {
        this.borderColorG = borderColorG;
    }

    public int getBorderColorB() {
        return borderColorB;
    }

    public void setBorderColorB(int borderColorB) {
        this.borderColorB = borderColorB;
    }


    public int getSignatureWidth() {
        return signatureWidth;
    }

    public void setSignatureWidth(int signatureWidth) {
        this.signatureWidth = signatureWidth;
    }

    public int getSignatureHeight() {
        return signatureHeight;
    }

    public void setSignatureHeight(int signatureHeight) {
        this.signatureHeight = signatureHeight;
    }

    public int getSignatureMax() {
        return signatureMax;
    }

    public void setSignatureMax(int signatureMax) {
        this.signatureMax = signatureMax;
    }

    public int getSignatureMin() {
        return signatureMin;
    }

    public void setSignatureMin(int signatureMin) {
        this.signatureMin = signatureMin;
    }

    public String getSignatureDir() {
        return signatureDir;
    }

    public void setSignatureDir(String signatureDir) {
        this.signatureDir = signatureDir;
    }

    public int getMarkBorderColorR() {
        return markBorderColorR;
    }

    public void setMarkBorderColorR(int markBorderColorR) {
        this.markBorderColorR = markBorderColorR;
    }

    public int getMarkBorderColorG() {
        return markBorderColorG;
    }

    public void setMarkBorderColorG(int markBorderColorG) {
        this.markBorderColorG = markBorderColorG;
    }

    public int getMarkBorderColorB() {
        return markBorderColorB;
    }

    public void setMarkBorderColorB(int markBorderColorB) {
        this.markBorderColorB = markBorderColorB;
    }

    public int getMarkRectPaddingTop() {
        return markRectPaddingTop;
    }

    public void setMarkRectPaddingTop(int markRectPaddingTop) {
        this.markRectPaddingTop = markRectPaddingTop;
    }

    public int getMarkRectPaddingBottom() {
        return markRectPaddingBottom;
    }

    public void setMarkRectPaddingBottom(int markRectPaddingBottom) {
        this.markRectPaddingBottom = markRectPaddingBottom;
    }

    public int getMarkRectPaddingLeft() {
        return markRectPaddingLeft;
    }

    public void setMarkRectPaddingLeft(int markRectPaddingLeft) {
        this.markRectPaddingLeft = markRectPaddingLeft;
    }

    public int getMarkRectPaddingRight() {
        return markRectPaddingRight;
    }

    public void setMarkRectPaddingRight(int markRectPaddingRight) {
        this.markRectPaddingRight = markRectPaddingRight;
    }


    public int getNoiseBottomProbability() {
        return noiseBottomProbability;
    }

    public void setNoiseBottomProbability(int noiseBottomProbability) {
        this.noiseBottomProbability = noiseBottomProbability;
    }

    public int getNoiseLeftProbability() {
        return noiseLeftProbability;
    }

    public void setNoiseLeftProbability(int noiseLeftProbability) {
        this.noiseLeftProbability = noiseLeftProbability;
    }

    public int getNoiseRightProbability() {
        return noiseRightProbability;
    }

    public void setNoiseRightProbability(int noiseRightProbability) {
        this.noiseRightProbability = noiseRightProbability;
    }

    public int getNoiseTopProbability() {
        return noiseTopProbability;
    }

    public void setNoiseTopProbability(int noiseTopProbability) {
        this.noiseTopProbability = noiseTopProbability;
    }

    public String getMockDataPath() {
        return mockDataPath;
    }

    public void setMockDataPath(String mockDataPath) {
        this.mockDataPath = mockDataPath;
    }

    public boolean isMockDataHeadShuffle() {
        return mockDataHeadShuffle;
    }

    public void setMockDataHeadShuffle(boolean mockDataHeadShuffle) {
        this.mockDataHeadShuffle = mockDataHeadShuffle;
    }

    public List<String> getFixedStructureJsonList() {
        return fixedStructureJsonList;
    }

    public void setFixedStructureJsonList(List<String> fixedStructureJsonList) {
        this.fixedStructureJsonList = fixedStructureJsonList;
    }

    public boolean isFixedStructureEnable() {
        return fixedStructureEnable;
    }

    public void setFixedStructureEnable(boolean fixedStructureEnable) {
        this.fixedStructureEnable = fixedStructureEnable;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void retryCountCut(){
        retryCount--;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}
