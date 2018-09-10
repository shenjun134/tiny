package com.tiny.common.entity;


import com.tiny.common.enums.FieldType;

public class CellVO {

    private Object value;


    private int xAlign = 0;

    private int yAlign = 0;

    private int valueX = 0;

    private int valueY = 0;

    private FieldType valueType = FieldType.STRING;


    private boolean decimalEnable = false;
    private int decimalMaxCount = 0;
    private int decimalMinCount = 0;

    private boolean intEnable = false;
    private int intMaxCount = 0;

    private int intMinCount = 0;

    private int width = 1;

    private String valueDef;


    public CellVO(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public CellVO setValue(Object value) {
        this.value = value;
        return this;
    }

    public int getxAlign() {
        return xAlign;
    }

    public CellVO setxAlign(int xAlign) {
        this.xAlign = xAlign;
        return this;
    }

    public int getyAlign() {
        return yAlign;
    }

    public CellVO setyAlign(int yAlign) {
        this.yAlign = yAlign;
        return this;
    }

    public int getValueX() {
        return valueX;
    }

    public CellVO setValueX(int valueX) {
        this.valueX = valueX;
        return this;
    }

    public int getValueY() {
        return valueY;
    }

    public CellVO setValueY(int valueY) {
        this.valueY = valueY;
        return this;
    }

    public FieldType getValueType() {
        return valueType;
    }

    public CellVO setValueType(FieldType valueType) {
        this.valueType = valueType;
        return this;
    }

    public String getValueDef() {
        return valueDef;
    }

    public CellVO setValueDef(String valueDef) {
        this.valueDef = valueDef;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public CellVO setWidth(int width) {
        this.width = width;
        return this;
    }

    public boolean isDecimalEnable() {
        return decimalEnable;
    }


    public int getDecimalMaxCount() {
        return decimalMaxCount;
    }


    public boolean isIntEnable() {
        return intEnable;
    }


    public int getIntMaxCount() {
        return intMaxCount;
    }

    public CellVO setInt(int max, int min) {
        this.intMaxCount = max;
        this.intMinCount = min;
        this.intEnable = max >= min;
        return this;
    }

    public CellVO setDecimal(int max, int min) {
        this.decimalMaxCount = max;
        this.decimalMinCount = min;
        this.decimalEnable = max >= min;
        return this;
    }


    public int getDecimalMinCount() {
        return decimalMinCount;
    }

    public int getIntMinCount() {
        return intMinCount;
    }

    @Override
    public String toString() {
        return "CellVO{" +
                "value=" + value +
                ", xAlign=" + xAlign +
                ", yAlign=" + yAlign +
                ", valueX=" + valueX +
                ", valueY=" + valueY +
                ", valueType=" + valueType +
                ", decimalEnable=" + decimalEnable +
                ", decimalMaxCount=" + decimalMaxCount +
                ", decimalMinCount=" + decimalMinCount +
                ", intEnable=" + intEnable +
                ", intMaxCount=" + intMaxCount +
                ", intMinCount=" + intMinCount +
                ", width=" + width +
                ", valueDef='" + valueDef + '\'' +
                '}';
    }
}
