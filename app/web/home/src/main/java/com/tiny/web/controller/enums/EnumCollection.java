/**
 * EnumCollection.java
 */
package com.tiny.web.controller.enums;

import com.tiny.common.enums.DateRangEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author e521907
 * @version 1.0
 */
public class EnumCollection {

    /**
     * @return
     */
    public static List<DateRangEnum> getDateRanges() {
        return new ArrayList<DateRangEnum>(Arrays.asList(DateRangEnum.values()));
    }

    /**
     * @return
     */
    public static List<ComponentAction> getComponentActions() {
        return new ArrayList<ComponentAction>(Arrays.asList(ComponentAction.values()));
    }


}
