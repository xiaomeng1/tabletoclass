package common;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mengxiangli on 2018/6/24.
 */
public enum DataTypeEnum {
    INT_INTEGER("INT", "Integer"),
    VACHAR_STRING("VARCHAR", "String"),
    TINYINT_BOOL("TINYINT", "Boolean"),
    DATETIME_DATE("DATETIME", "Date"),
    DECIMAL_BIGDECIMAL("DECIMAL", "BigDecimal"),
    TEST_STRING("TEXT", "String");

    private static Map<String, String> mapping = new HashMap<>();

    static {
        for (DataTypeEnum dataType : DataTypeEnum.values()) {
            mapping.put(dataType.dbType, dataType.javaType);
        }
    }

    private String dbType;

    private String javaType;

    DataTypeEnum(String dbType, String javaType) {
        this.dbType = dbType;
        this.javaType = javaType;
    }

    public String getDbType() {
        return dbType;
    }

    public String getJavaType() {
        return javaType;
    }


    public static String get(String dbType) {
        return mapping.get(dbType);
    }
}
