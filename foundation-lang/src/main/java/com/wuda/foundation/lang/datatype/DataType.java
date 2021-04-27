package com.wuda.foundation.lang.datatype;

/**
 * 数据类型的定义.可以使用已有系统的数据类型,比如：MySQL的VARCHAR类型;
 * 比如Java的Integer类型,也可以自定义数据类型.添加数据类型后,记得调用
 * {@link DataTypeRegistry#register(DataType)}方法将自己注册,否则这个
 * 数据类型就像一个孤岛一样不能被查找到.
 *
 * @author wuda
 * @see DataTypeSchema
 */
public interface DataType {

    /**
     * 获取该数据类型所属的schema.
     *
     * @return schema
     */
    DataTypeSchema getSchema();

    /**
     * 获取data type name.
     *
     * @return data type name
     */
    String getName();

    /**
     * 完整的名称,包含schema和data type name.
     * 我们定义数据类型的完整名称由三部分组成,
     * <ol>
     * <li>第一部分表示该数据类型所处的体系</li>
     * <li>第二部分是固定的英文冒号</li>
     * <li>第三部分表示该体系下具体的数据类型的名称</li>
     * </ol>
     * 因此一个data type的完整名称的格式是：scheme:data type name
     * 比如：MySQLDataTypeSchema:VARCHAR
     *
     * @return fully qualified name
     */
    String getFullyQualifiedName();

    /**
     * 处理该数据类型的Handler.
     *
     * @return handler
     */
    DataTypeHandler getHandler();
}
