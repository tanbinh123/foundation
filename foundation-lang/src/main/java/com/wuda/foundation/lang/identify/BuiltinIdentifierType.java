package com.wuda.foundation.lang.identify;

/**
 * 内建的identifier type.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinIdentifierType implements IdentifierType {

    /**
     * VIRTUAL.
     */
    VIRTUAL(0, "表示不存在的,通常用于虚拟数据"),
    /**
     * 表示item.
     */
    FOUNDATION_ITEM(1, "表示item"),
    /**
     * 表示store.
     */
    FOUNDATION_STORE(2, "表示store"),

    /**
     * 表示item_category表.
     */
    TABLE_ITEM_CATEGORY(3, "表示item_category表"),

    /**
     * 表示permission_role表.
     */
    TABLE_PERMISSION_ROLE(4, "表示permission_role表"),
    /**
     * 表示menu item.
     */
    FOUNDATION_MENU_ITEM(5, "表示menu_item"),
    /**
     * 表示menu_item_category表.
     */
    TABLE_MENU_ITEM_CATEGORY(6, "表示menu_item_category表"),
    /**
     * 表示menu.
     */
    MENU(7, "表示menu");

    /**
     * unique code.
     */
    protected int code;
    /**
     * 描述信息.
     */
    protected String description;

    /**
     * 构造实例.
     *
     * @param code        the unique code
     * @param description description
     */
    BuiltinIdentifierType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
