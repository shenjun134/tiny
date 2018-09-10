package com.tiny.common.entity;

import com.tiny.common.configuration.GridLayoutConfigMng;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GridLayoutConfig {

    public interface Const {
        int blankProbability = 80;

        int splitProbability = 70;

        int blankRowHeight = 50;

        int rectMinSize = 200;

        int defPadding = 50;

        int shock = 0;
    }

    private int totalWidth;

    private int totalHeight;

    private int paddingTop;

    private int paddingRight;

    private int paddingBottom;

    private int paddingLeft;

    private int borderWidth;

    private int borderColorR = 0;

    private int borderColorG = 0;

    private int borderColorB = 0;


    private int blankRowHeight;

    private int rectMinSize;

    /**
     * total 100
     */
    private int blankProbability;

    private int splitProbability;

    private int shockSize;

    private int numberOfCategory;

    private int eachCategoryTotal;

    private List<SplitConfig> splitConfigList = new ArrayList<>();

    public GridLayoutConfig() {
        Properties properties = GridLayoutConfigMng.getInstance().getProperties();
        this.totalWidth = NumberUtils.toInt(properties.get("total.width").toString(), 0);
        this.totalHeight = NumberUtils.toInt(properties.get("total.height").toString(), 0);
        this.borderWidth = NumberUtils.toInt(properties.get("border.width").toString(), 0);
        this.borderColorR = NumberUtils.toInt(properties.get("border.color.red").toString(), 0);
        this.borderColorG = NumberUtils.toInt(properties.get("border.color.green").toString(), 0);
        this.borderColorB = NumberUtils.toInt(properties.get("border.color.blue").toString(), 0);
        this.blankRowHeight = NumberUtils.toInt(properties.get("blank.row.height").toString(), Const.blankRowHeight);
        this.rectMinSize = NumberUtils.toInt(properties.get("rectangle.min.size").toString(), Const.rectMinSize);
        this.blankProbability = NumberUtils.toInt(properties.get("probability.blank").toString(), Const.blankProbability);
        this.splitProbability = NumberUtils.toInt(properties.get("probability.split").toString(), Const.splitProbability);

        this.paddingTop = NumberUtils.toInt(properties.get("padding.top").toString(), Const.defPadding);
        this.paddingRight = NumberUtils.toInt(properties.get("padding.right").toString(), Const.defPadding);
        this.paddingBottom = NumberUtils.toInt(properties.get("padding.bottom").toString(), Const.defPadding);
        this.paddingLeft = NumberUtils.toInt(properties.get("padding.left").toString(), Const.defPadding);
        this.shockSize = NumberUtils.toInt(properties.get("shock.size").toString(), Const.shock);


        this.numberOfCategory = NumberUtils.toInt(properties.get("category.number").toString(), 1);
        this.eachCategoryTotal = NumberUtils.toInt(properties.get("each.category.total").toString(), 1);
    }

    public static void main(String[] args) {
        System.out.println(new GridLayoutConfig());
    }

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


    public int getBlankRowHeight() {
        return blankRowHeight;
    }

    public void setBlankRowHeight(int blankRowHeight) {
        this.blankRowHeight = blankRowHeight;
    }

    public int getRectMinSize() {
        return rectMinSize;
    }

    public void setRectMinSize(int rectMinSize) {
        this.rectMinSize = rectMinSize;
    }

    public int getBlankProbability() {
        return blankProbability;
    }

    public void setBlankProbability(int blankProbability) {
        this.blankProbability = blankProbability;
    }

    public int getSplitProbability() {
        return splitProbability;
    }

    public void setSplitProbability(int splitProbability) {
        this.splitProbability = splitProbability;
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

    public int getLayoutW() {
        return this.totalWidth - this.paddingLeft - this.paddingRight;
    }

    public int getLayoutH() {
        return this.totalHeight - this.paddingTop - this.paddingBottom;
    }

    public List<SplitConfig> getSplitConfigList() {
        return splitConfigList;
    }

    public void setSplitConfigList(List<SplitConfig> splitConfigList) {
        this.splitConfigList = splitConfigList;
    }

    public int getShockSize() {
        return shockSize;
    }

    public void setShockSize(int shockSize) {
        this.shockSize = shockSize;
    }

    public int getNumberOfCategory() {
        return numberOfCategory;
    }

    public void setNumberOfCategory(int numberOfCategory) {
        this.numberOfCategory = numberOfCategory;
    }

    public int getEachCategoryTotal() {
        return eachCategoryTotal;
    }

    public void setEachCategoryTotal(int eachCategoryTotal) {
        this.eachCategoryTotal = eachCategoryTotal;
    }


    @Override
    public String toString() {
        return "GridLayoutConfig{" +
                "totalWidth=" + totalWidth +
                ", totalHeight=" + totalHeight +
                ", paddingTop=" + paddingTop +
                ", paddingRight=" + paddingRight +
                ", paddingBottom=" + paddingBottom +
                ", paddingLeft=" + paddingLeft +
                ", borderWidth=" + borderWidth +
                ", borderColorR=" + borderColorR +
                ", borderColorG=" + borderColorG +
                ", borderColorB=" + borderColorB +
                ", blankRowHeight=" + blankRowHeight +
                ", rectMinSize=" + rectMinSize +
                ", blankProbability=" + blankProbability +
                ", splitProbability=" + splitProbability +
                ", shockSize=" + shockSize +
                ", numberOfCategory=" + numberOfCategory +
                ", eachCategoryTotal=" + eachCategoryTotal +
                ", splitConfigList=" + splitConfigList +
                '}';
    }
}
