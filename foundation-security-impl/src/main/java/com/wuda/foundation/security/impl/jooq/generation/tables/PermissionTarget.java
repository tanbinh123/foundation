/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.security.impl.jooq.generation.tables;


import com.wuda.foundation.security.impl.jooq.generation.FoundationSecurity;
import com.wuda.foundation.security.impl.jooq.generation.Indexes;
import com.wuda.foundation.security.impl.jooq.generation.Keys;
import com.wuda.foundation.security.impl.jooq.generation.tables.records.PermissionTargetRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row12;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * permission作用的对象，分为两类，1，关联外部对象，使用type字段表明外部对象的类型，referenced_id表明外部对象的唯一标记，比如在web系统中，已经拥有了菜单表，如果要对菜单权限控制，使用referenced_id关联菜单表的主键ID，就可以将permission与菜单数据建立联系，而不需要把菜单相关的逻辑引入到权限体系中；2，不关联外部对象，当前表中的信息就已经描述了作用对象的信息。对于permission体系来说，permission 
 * target是主体（一等公民），permiss
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PermissionTarget extends TableImpl<PermissionTargetRecord> {

    private static final long serialVersionUID = -849944650;

    /**
     * The reference instance of <code>foundation_security.permission_target</code>
     */
    public static final PermissionTarget PERMISSION_TARGET = new PermissionTarget();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PermissionTargetRecord> getRecordType() {
        return PermissionTargetRecord.class;
    }

    /**
     * The column <code>foundation_security.permission_target.permission_target_id</code>.
     */
    public final TableField<PermissionTargetRecord, ULong> PERMISSION_TARGET_ID = createField(DSL.name("permission_target_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_security.permission_target.permission_category_id</code>. 分类
     */
    public final TableField<PermissionTargetRecord, ULong> PERMISSION_CATEGORY_ID = createField(DSL.name("permission_category_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "分类");

    /**
     * The column <code>foundation_security.permission_target.name</code>. permission target  name。在java.security.Permission#getName设计中，name就唯一识别了作用对象，类似的，在我们这里，由于有分类，因此只要在分类中唯一即可
     */
    public final TableField<PermissionTargetRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(45).nullable(false), this, "permission target  name。在java.security.Permission#getName设计中，name就唯一识别了作用对象，类似的，在我们这里，由于有分类，因此只要在分类中唯一即可");

    /**
     * The column <code>foundation_security.permission_target.type</code>. permission target的类型。假设在一个web系统中，有两种类型的权限控制，一种是为用户授权可以使用系统的哪些功能；另外一种是为用户授权可以使用哪种终端访问系统(比如App，pc)，这两类是完全不同的对象，需要区分开,便于管理。
     */
    public final TableField<PermissionTargetRecord, UByte> TYPE = createField(DSL.name("type"), org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false), this, "permission target的类型。假设在一个web系统中，有两种类型的权限控制，一种是为用户授权可以使用系统的哪些功能；另外一种是为用户授权可以使用哪种终端访问系统(比如App，pc)，这两类是完全不同的对象，需要区分开,便于管理。");

    /**
     * The column <code>foundation_security.permission_target.referenced_type</code>. 关联的外部对象的类型。注意和type字段的区别，在实际中，有可能这两个字段的值是一样的，但是在意义上却是完全不一样的，而且有可能一种type的target，由关联的多种referenced_type组成
     */
    public final TableField<PermissionTargetRecord, UByte> REFERENCED_TYPE = createField(DSL.name("referenced_type"), org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINTUNSIGNED)), this, "关联的外部对象的类型。注意和type字段的区别，在实际中，有可能这两个字段的值是一样的，但是在意义上却是完全不一样的，而且有可能一种type的target，由关联的多种referenced_type组成");

    /**
     * The column <code>foundation_security.permission_target.referenced_identifier</code>. 该target关联的外部对象的唯一标记，如果为0，表示并没有关联外部对象。这样设计的目的是：不把作用对象放在权限体系中，而是任何想要使用权限体系的外部对象，通过该字段关联到自己，这样就可以做到权限体系的最大可扩展性。举例：在web系统中，如果已经拥有了菜单表，如果要对菜单权限控制，就可以使用该字段将permission与菜单数据建立联系，而不需要把菜单相关的逻辑引入到权限体系中，但是，如果多种外部对象通过该字段关联进来，有可能identifier冲突，因此需要type字段一起做唯一控制
     */
    public final TableField<PermissionTargetRecord, ULong> REFERENCED_IDENTIFIER = createField(DSL.name("referenced_identifier"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINTUNSIGNED)), this, "该target关联的外部对象的唯一标记，如果为0，表示并没有关联外部对象。这样设计的目的是：不把作用对象放在权限体系中，而是任何想要使用权限体系的外部对象，通过该字段关联到自己，这样就可以做到权限体系的最大可扩展性。举例：在web系统中，如果已经拥有了菜单表，如果要对菜单权限控制，就可以使用该字段将permission与菜单数据建立联系，而不需要把菜单相关的逻辑引入到权限体系中，但是，如果多种外部对象通过该字段关联进来，有可能identifier冲突，因此需要type字段一起做唯一控制");

    /**
     * The column <code>foundation_security.permission_target.description</code>. 描述
     */
    public final TableField<PermissionTargetRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.VARCHAR(45), this, "描述");

    /**
     * The column <code>foundation_security.permission_target.create_time</code>.
     */
    public final TableField<PermissionTargetRecord, LocalDateTime> CREATE_TIME = createField(DSL.name("create_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>foundation_security.permission_target.create_user_id</code>.
     */
    public final TableField<PermissionTargetRecord, ULong> CREATE_USER_ID = createField(DSL.name("create_user_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_security.permission_target.last_modify_time</code>.
     */
    public final TableField<PermissionTargetRecord, LocalDateTime> LAST_MODIFY_TIME = createField(DSL.name("last_modify_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>foundation_security.permission_target.last_modify_user_id</code>.
     */
    public final TableField<PermissionTargetRecord, ULong> LAST_MODIFY_USER_ID = createField(DSL.name("last_modify_user_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_security.permission_target.is_deleted</code>.
     */
    public final TableField<PermissionTargetRecord, ULong> IS_DELETED = createField(DSL.name("is_deleted"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINTUNSIGNED)), this, "");

    /**
     * Create a <code>foundation_security.permission_target</code> table reference
     */
    public PermissionTarget() {
        this(DSL.name("permission_target"), null);
    }

    /**
     * Create an aliased <code>foundation_security.permission_target</code> table reference
     */
    public PermissionTarget(String alias) {
        this(DSL.name(alias), PERMISSION_TARGET);
    }

    /**
     * Create an aliased <code>foundation_security.permission_target</code> table reference
     */
    public PermissionTarget(Name alias) {
        this(alias, PERMISSION_TARGET);
    }

    private PermissionTarget(Name alias, Table<PermissionTargetRecord> aliased) {
        this(alias, aliased, null);
    }

    private PermissionTarget(Name alias, Table<PermissionTargetRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("permission作用的对象，分为两类，1，关联外部对象，使用type字段表明外部对象的类型，referenced_id表明外部对象的唯一标记，比如在web系统中，已经拥有了菜单表，如果要对菜单权限控制，使用referenced_id关联菜单表的主键ID，就可以将permission与菜单数据建立联系，而不需要把菜单相关的逻辑引入到权限体系中；2，不关联外部对象，当前表中的信息就已经描述了作用对象的信息。对于permission体系来说，permission target是主体（一等公民），permiss"), TableOptions.table());
    }

    public <O extends Record> PermissionTarget(Table<O> child, ForeignKey<O, PermissionTargetRecord> key) {
        super(child, key, PERMISSION_TARGET);
    }

    @Override
    public Schema getSchema() {
        return FoundationSecurity.FOUNDATION_SECURITY;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PERMISSION_TARGET_FK_PERMISSION_CATEGORY_ID, Indexes.PERMISSION_TARGET_IDX_REFERENCED);
    }

    @Override
    public UniqueKey<PermissionTargetRecord> getPrimaryKey() {
        return Keys.KEY_PERMISSION_TARGET_PRIMARY;
    }

    @Override
    public List<UniqueKey<PermissionTargetRecord>> getKeys() {
        return Arrays.<UniqueKey<PermissionTargetRecord>>asList(Keys.KEY_PERMISSION_TARGET_PRIMARY, Keys.KEY_PERMISSION_TARGET_IDX_PERMISSION_NAME);
    }

    @Override
    public PermissionTarget as(String alias) {
        return new PermissionTarget(DSL.name(alias), this);
    }

    @Override
    public PermissionTarget as(Name alias) {
        return new PermissionTarget(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PermissionTarget rename(String name) {
        return new PermissionTarget(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PermissionTarget rename(Name name) {
        return new PermissionTarget(name, null);
    }

    // -------------------------------------------------------------------------
    // Row12 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row12<ULong, ULong, String, UByte, UByte, ULong, String, LocalDateTime, ULong, LocalDateTime, ULong, ULong> fieldsRow() {
        return (Row12) super.fieldsRow();
    }
}
