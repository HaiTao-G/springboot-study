package com.haitao.enums;

/**
 * 文件类型
 */
public enum FileType {

    //
    XLSX(".xlsx"),

    XLSY(".xlsy"),

    ZIP(".zip");

    private final String value;

    FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
