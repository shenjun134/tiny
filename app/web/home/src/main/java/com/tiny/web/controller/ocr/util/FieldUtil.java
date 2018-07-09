package com.tiny.web.controller.ocr.util;

import com.tiny.core.model.MarkBox;
import com.tiny.web.controller.ocr.model.Field;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FieldUtil {

    private interface MAP_SEP {
        String like = "[%]";
        String add = "[+]";
    }

    private static final Logger logger = Logger.getLogger(FieldUtil.class);

    public static Field getField(Map<String, Field> fieldMap, String... propValues) {

            for (String propValue : propValues) {
                int index = StringUtils.indexOfAny(propValue, Constants.OCR_MAP_SEP);
                if (index < 0) {
                    Field f = fieldMap.get(propValue);
                    if (f != null)
                        return f;
                }
                else
                {
                    String separator = propValue.substring(index, index + 3);
                    switch (separator)
                    {
                        case MAP_SEP.like:
                            if (propValue.lastIndexOf(MAP_SEP.like) == index) {
                                if (index == 0) {
                                    for (String key : fieldMap.keySet())
                                        if (StringUtils.endsWith(key, StringUtils.removeStart(propValue, MAP_SEP.like)))
                                            return fieldMap.get(key);
                                }
                                else
                                {
                                    for (String key : fieldMap.keySet())
                                        if (StringUtils.startsWith(key, StringUtils.removeEnd(propValue, MAP_SEP.like)))
                                            return fieldMap.get(key);
                                }
                            }
                            else
                            {
                                for (String key : fieldMap.keySet())
                                    if (StringUtils.contains(key, StringUtils.remove(propValue, MAP_SEP.like)))
                                        return fieldMap.get(key);
                            }
                            break;
                        case MAP_SEP.add:
                            String addStr = "";
                            Field f = null;
                            for (String value : StringUtils.split(propValue ,MAP_SEP.add)) {
                                Field field = getField(fieldMap, value);
                                if (field != null) {
                                    addStr += field.getValue() + " ";
                                    if (f == null)
                                        f = field;
                                }
                            }
                            f.setValue(addStr);
                            return f;
                    }
                }
            }
        return null;
    }

    public static String get(Map<String, Field> fieldMap, String... names) {
        Set<String> keySet = fieldMap.keySet();
        Field f = getField(fieldMap, names);
        return f != null ? f.getValue() : "";
    }

    /**
     * @param o
     * @param fieldMap
     * @return
     */
    public static List<MarkBox> setMappingField(Object o, Map<String, Field> fieldMap) {
        List<MarkBox> mbList = new ArrayList<MarkBox>(20);
        java.lang.reflect.Field[] fields = o.getClass().getDeclaredFields();
        Set<String> keySet = fieldMap.keySet();
        for (java.lang.reflect.Field temp : fields) {
            String name = temp.getName();
            if (StringUtils.contains("serialVersionUID", name)) {
                continue;
            }
            temp.setAccessible(true);
            Object value = null;
            try {
                Field f = getField(fieldMap, StringUtils.split(PropUtil.load("ocr-cover").get(name), ','));
                if (f != null) {
                    value = f.getValue();
                    // logger.info(name + "--" + f.getLabel() + "--" + f.getValue());
                    mbList.add(new MarkBox(name, (long) f.getX(), (long) f.getY(), (long) f.getWidth(), (long) f.getHeight()));
                    temp.set(o, value);
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return mbList;
    }


    /**
     * @param fieldList
     * @param fieldMap
     * @param mappingKey
     */
    public static void pickField(List<Field> fieldList, Map<String, Field> fieldMap, String mappingKey) {
        Map<String, String> propMap = PropUtil.load(mappingKey);
        if (propMap == null || propMap.size() == 0) {
            return;
        }

        Set<String> keySet = fieldMap.keySet();

        for (Map.Entry<String, String> entry : propMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isBlank(value)) {
                continue;
            }
            Field f = getField(fieldMap, StringUtils.split(value, ','));
            if (f == null) {
                continue;
            }

            Field field = null;
            try {
                field = (Field) f.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            if (("FundID").equalsIgnoreCase(key))
                field.setValue(StringUtils.split(field.getValue(), "#", 2)[1].trim());
            if (mappingKey.contains("pimco"))
            {
                if (("PrincipalCurrency").equalsIgnoreCase(key))
                    field.setValue(StringUtils.split(field.getValue(), " ", 2)[0].trim());
                else if (("PrincipalAmount").equalsIgnoreCase(key))
                    field.setValue(StringUtils.split(field.getValue(), " ", 2)[1].trim());
                else if (("SettlementCurrency").equalsIgnoreCase(key))
                    field.setValue(StringUtils.split(field.getValue(), " ", 2)[0].trim());
                else if (("SettlementAmount").equalsIgnoreCase(key))
                    field.setValue(StringUtils.split(field.getValue(), " ", 2)[1].trim());
            }

            field.setId(key);
            fieldList.add(field);
        }
    }
}
