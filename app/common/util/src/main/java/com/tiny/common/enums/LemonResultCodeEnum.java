/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.tiny.common.enums;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author shenjun
 * @version $Id: ProdMngResultCodeEnum.java, v 0.1 2016��2��14�� ����11:36:40 shenjun Exp $
 */
public enum LemonResultCodeEnum  implements EnumBase{

    /** �����ɹ� */
    SUCCESS("SUCCESS", "�����ɹ�"),

    /** ϵͳ�쳣*/
    SYSTEM_ERROR("SYSTEM_ERROR", "ϵͳ�쳣������ϵ����Ա��"),

    /** �ⲿ�ӿڵ����쳣*/
    INTERFACE_SYSTEM_ERROR("INTERFACE_SYSTEM_ERROR", "�ⲿ�ӿڵ����쳣������ϵ����Ա��"),

    /** ҵ�����Ӵ��?ʱ */
    CONNECT_TIME_OUT("CONNECT_TIME_OUT", "ϵͳ��ʱ�����Ժ�����!"),

    /** ϵͳ���� */
    SYSTEM_FAILURE("SYSTEM_FAILURE", "ϵͳ����"),

    /** �Ƿ����� */
    ILLEGAL_OPERATION("ILLEGAL_OPERATION", "�Ƿ�����"),

    /** ����Ϊ�� */
    NULL_ARGUMENT("NULL_ARGUMENT", "����Ϊ��"),

    /** �û�idΪ�� */
    NULL_USERID("NULL_USERID", "��ԱidΪ��!"),

    /** ����Ϊ�� */
    NULL_OBJECT("NULL_OBJECT", "����Ϊ��"),

    /** ������ȷ */
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "������ȷ"),

    /** �߼����� */
    LOGIC_ERROR("LOGIC_ERROR", "�߼�����"),

    /** ����쳣 */
    DATA_ERROR("DATA_ERROR", "����쳣"),

    /**����ֻ�Ų�ѯ������Ӧ���û�*/
    QUERY_USERID_BY_PHONENO_ERROR("QUERY_USERID_BY_PHONENO_ERROR", "���ֻ����δ���û�"), ;

    /** ö�ٱ�� */
    private String code;

    /** ö������ */
    private String message;

    /**
     * ���췽��
     * 
     * @param code         ö�ٱ��
     * @param message      ö������
     */
    private LemonResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * ͨ��ö��<code>code</code>���ö�١�
     * @param code ö��ֵ
     * @return  �����ڷ���NUll<br/>�����ڷ������ֵ
     */
    public static final LemonResultCodeEnum getByCode(String code) {

        //���ΪNUll���� NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (LemonResultCodeEnum item : values()) {
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }

        return null;
    }

    /**
     * Setter method for property <tt>message</tt>.
     * 
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /** 
     * @see com.yylc.platform.common.enums.EnumBase#message()
     */
    @Override
    public String message() {
        return message;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     * 
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /** 
     * @see com.yylc.platform.common.enums.EnumBase#value()
     */
    @Override
    public Number value() {
        return null;
    }

}
