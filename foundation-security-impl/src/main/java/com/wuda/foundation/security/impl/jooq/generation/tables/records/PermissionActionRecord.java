/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.security.impl.jooq.generation.tables.records;


import com.wuda.foundation.security.impl.jooq.generation.tables.PermissionAction;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * permission允许的行为。参考: java.security.Permission#getActions。为什么要把作用对象和对该对象的action分开呢？因为对于同一个作用对象，可能有多个action，比如对于一个文件可以有读和写权限。action可以关联外部对象，具体的解释可以参考permission 
 * targe ,它们对于关联外部对象的定义是一样的。t
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PermissionActionRecord extends UpdatableRecordImpl<PermissionActionRecord> implements Record11<ULong, ULong, String, String, UByte, ULong, LocalDateTime, ULong, LocalDateTime, ULong, ULong> {

    private static final long serialVersionUID = -1611814053;

    /**
     * Setter for <code>foundation_security.permission_action.permission_action_id</code>.
     */
    public void setPermissionActionId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>foundation_security.permission_action.permission_action_id</code>.
     */
    public ULong getPermissionActionId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>foundation_security.permission_action.permission_target_id</code>.
     */
    public void setPermissionTargetId(ULong value) {
        set(1, value);
    }

    /**
     * Getter for <code>foundation_security.permission_action.permission_target_id</code>.
     */
    public ULong getPermissionTargetId() {
        return (ULong) get(1);
    }

    /**
     * Setter for <code>foundation_security.permission_action.name</code>. action name
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>foundation_security.permission_action.name</code>. action name
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>foundation_security.permission_action.description</code>.
     */
    public void setDescription(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>foundation_security.permission_action.description</code>.
     */
    public String getDescription() {
        return (String) get(3);
    }

    /**
     * Setter for <code>foundation_security.permission_action.referenced_type</code>. 关联的外部对象的类型，0表示没有关联其他外部对象。
     */
    public void setReferencedType(UByte value) {
        set(4, value);
    }

    /**
     * Getter for <code>foundation_security.permission_action.referenced_type</code>. 关联的外部对象的类型，0表示没有关联其他外部对象。
     */
    public UByte getReferencedType() {
        return (UByte) get(4);
    }

    /**
     * Setter for <code>foundation_security.permission_action.referenced_idenfier</code>. 关联的外部对象的identifier，0表示没有关联外部对象。
     */
    public void setReferencedIdenfier(ULong value) {
        set(5, value);
    }

    /**
     * Getter for <code>foundation_security.permission_action.referenced_idenfier</code>. 关联的外部对象的identifier，0表示没有关联外部对象。
     */
    public ULong getReferencedIdenfier() {
        return (ULong) get(5);
    }

    /**
     * Setter for <code>foundation_security.permission_action.create_time</code>.
     */
    public void setCreateTime(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>foundation_security.permission_action.create_time</code>.
     */
    public LocalDateTime getCreateTime() {
        return (LocalDateTime) get(6);
    }

    /**
     * Setter for <code>foundation_security.permission_action.create_user_id</code>.
     */
    public void setCreateUserId(ULong value) {
        set(7, value);
    }

    /**
     * Getter for <code>foundation_security.permission_action.create_user_id</code>.
     */
    public ULong getCreateUserId() {
        return (ULong) get(7);
    }

    /**
     * Setter for <code>foundation_security.permission_action.last_modify_time</code>.
     */
    public void setLastModifyTime(LocalDateTime value) {
        set(8, value);
    }

    /**
     * Getter for <code>foundation_security.permission_action.last_modify_time</code>.
     */
    public LocalDateTime getLastModifyTime() {
        return (LocalDateTime) get(8);
    }

    /**
     * Setter for <code>foundation_security.permission_action.last_modify_user_id</code>.
     */
    public void setLastModifyUserId(ULong value) {
        set(9, value);
    }

    /**
     * Getter for <code>foundation_security.permission_action.last_modify_user_id</code>.
     */
    public ULong getLastModifyUserId() {
        return (ULong) get(9);
    }

    /**
     * Setter for <code>foundation_security.permission_action.is_deleted</code>.
     */
    public void setIsDeleted(ULong value) {
        set(10, value);
    }

    /**
     * Getter for <code>foundation_security.permission_action.is_deleted</code>.
     */
    public ULong getIsDeleted() {
        return (ULong) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<ULong> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row11<ULong, ULong, String, String, UByte, ULong, LocalDateTime, ULong, LocalDateTime, ULong, ULong> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    @Override
    public Row11<ULong, ULong, String, String, UByte, ULong, LocalDateTime, ULong, LocalDateTime, ULong, ULong> valuesRow() {
        return (Row11) super.valuesRow();
    }

    @Override
    public Field<ULong> field1() {
        return PermissionAction.PERMISSION_ACTION.PERMISSION_ACTION_ID;
    }

    @Override
    public Field<ULong> field2() {
        return PermissionAction.PERMISSION_ACTION.PERMISSION_TARGET_ID;
    }

    @Override
    public Field<String> field3() {
        return PermissionAction.PERMISSION_ACTION.NAME;
    }

    @Override
    public Field<String> field4() {
        return PermissionAction.PERMISSION_ACTION.DESCRIPTION;
    }

    @Override
    public Field<UByte> field5() {
        return PermissionAction.PERMISSION_ACTION.REFERENCED_TYPE;
    }

    @Override
    public Field<ULong> field6() {
        return PermissionAction.PERMISSION_ACTION.REFERENCED_IDENFIER;
    }

    @Override
    public Field<LocalDateTime> field7() {
        return PermissionAction.PERMISSION_ACTION.CREATE_TIME;
    }

    @Override
    public Field<ULong> field8() {
        return PermissionAction.PERMISSION_ACTION.CREATE_USER_ID;
    }

    @Override
    public Field<LocalDateTime> field9() {
        return PermissionAction.PERMISSION_ACTION.LAST_MODIFY_TIME;
    }

    @Override
    public Field<ULong> field10() {
        return PermissionAction.PERMISSION_ACTION.LAST_MODIFY_USER_ID;
    }

    @Override
    public Field<ULong> field11() {
        return PermissionAction.PERMISSION_ACTION.IS_DELETED;
    }

    @Override
    public ULong component1() {
        return getPermissionActionId();
    }

    @Override
    public ULong component2() {
        return getPermissionTargetId();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public String component4() {
        return getDescription();
    }

    @Override
    public UByte component5() {
        return getReferencedType();
    }

    @Override
    public ULong component6() {
        return getReferencedIdenfier();
    }

    @Override
    public LocalDateTime component7() {
        return getCreateTime();
    }

    @Override
    public ULong component8() {
        return getCreateUserId();
    }

    @Override
    public LocalDateTime component9() {
        return getLastModifyTime();
    }

    @Override
    public ULong component10() {
        return getLastModifyUserId();
    }

    @Override
    public ULong component11() {
        return getIsDeleted();
    }

    @Override
    public ULong value1() {
        return getPermissionActionId();
    }

    @Override
    public ULong value2() {
        return getPermissionTargetId();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public String value4() {
        return getDescription();
    }

    @Override
    public UByte value5() {
        return getReferencedType();
    }

    @Override
    public ULong value6() {
        return getReferencedIdenfier();
    }

    @Override
    public LocalDateTime value7() {
        return getCreateTime();
    }

    @Override
    public ULong value8() {
        return getCreateUserId();
    }

    @Override
    public LocalDateTime value9() {
        return getLastModifyTime();
    }

    @Override
    public ULong value10() {
        return getLastModifyUserId();
    }

    @Override
    public ULong value11() {
        return getIsDeleted();
    }

    @Override
    public PermissionActionRecord value1(ULong value) {
        setPermissionActionId(value);
        return this;
    }

    @Override
    public PermissionActionRecord value2(ULong value) {
        setPermissionTargetId(value);
        return this;
    }

    @Override
    public PermissionActionRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public PermissionActionRecord value4(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public PermissionActionRecord value5(UByte value) {
        setReferencedType(value);
        return this;
    }

    @Override
    public PermissionActionRecord value6(ULong value) {
        setReferencedIdenfier(value);
        return this;
    }

    @Override
    public PermissionActionRecord value7(LocalDateTime value) {
        setCreateTime(value);
        return this;
    }

    @Override
    public PermissionActionRecord value8(ULong value) {
        setCreateUserId(value);
        return this;
    }

    @Override
    public PermissionActionRecord value9(LocalDateTime value) {
        setLastModifyTime(value);
        return this;
    }

    @Override
    public PermissionActionRecord value10(ULong value) {
        setLastModifyUserId(value);
        return this;
    }

    @Override
    public PermissionActionRecord value11(ULong value) {
        setIsDeleted(value);
        return this;
    }

    @Override
    public PermissionActionRecord values(ULong value1, ULong value2, String value3, String value4, UByte value5, ULong value6, LocalDateTime value7, ULong value8, LocalDateTime value9, ULong value10, ULong value11) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PermissionActionRecord
     */
    public PermissionActionRecord() {
        super(PermissionAction.PERMISSION_ACTION);
    }

    /**
     * Create a detached, initialised PermissionActionRecord
     */
    public PermissionActionRecord(ULong permissionActionId, ULong permissionTargetId, String name, String description, UByte referencedType, ULong referencedIdenfier, LocalDateTime createTime, ULong createUserId, LocalDateTime lastModifyTime, ULong lastModifyUserId, ULong isDeleted) {
        super(PermissionAction.PERMISSION_ACTION);

        set(0, permissionActionId);
        set(1, permissionTargetId);
        set(2, name);
        set(3, description);
        set(4, referencedType);
        set(5, referencedIdenfier);
        set(6, createTime);
        set(7, createUserId);
        set(8, lastModifyTime);
        set(9, lastModifyUserId);
        set(10, isDeleted);
    }
}
