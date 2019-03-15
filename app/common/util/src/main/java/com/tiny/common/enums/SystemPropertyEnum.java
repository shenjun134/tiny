/**
 * SystemPropertyEnum.java
 */
package com.tiny.common.enums;

/**
 * @author e521907
 * @version 1.0
 *
 */
public enum SystemPropertyEnum implements EnumBase {
    CONTEXT_NAME("context.name"),

    JDBC_DRIVERCLASS("jdbc.driverClass"),

    JDBC_URL("jdbc.url"),

    JDBC_USERNAME("jdbc.username"),

    JDBC_PASSWORD("jdbc.password"),

    JDBC_KEYHOST("jdbc.keyhost"),

    FETCHSIZE("fetchSize"),

    QUERYTIMEOUT("queryTimeout"),

    C3P0_ACQUIREINCREMENT("c3p0.acquireIncrement"),

    C3P0_MINPOOLSIZE("c3p0.minPoolSize"),

    C3P0_MAXPOOLSIZE("c3p0.maxPoolSize"),

    C3P0_MAXIDLETIME("c3p0.maxIdleTime"),

    HOME_PAGE("home.page"),

    HOME_ENTRY_PAGE("home.entry.page"),

    FACADE_SIGNATURE_URL("facade.signature.url"),

    FACADE_SIGNATURE_URL_V2("facade.signature.url.v2"),

    FACADE_SIGN_FIX_URL("facade.sign.fix.url"),

    FACADE_SIGN_FIX_URL_V2("facade.sign.fix.url.v2"),

    FACADE_TEMPLATE_RECON_URL("facade.template.recon.url"),

    FACADE_OCR_RECON_URL("facade.ocr.recon.url"),

    SIGNATURE_MODE("signature.impl.mode"),

    SESSION_MAXINACTIVEINTERVAL("session.maxInactiveInterval"),

    ENV("__env"),

    SALT_KEY("salt.key"),

    OCR_UPLOAD_SERVER("ocr.upload.server"),;

    private String key;

    private SystemPropertyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String message() {
        return null;
    }

    @Override
    public Number value() {
        return null;
    }

}
