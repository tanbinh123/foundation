/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.security.impl.jooq.generation.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;

import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * subject可以代表用户，也可以代表想要访问其他资源的应用，suibject与permission的关联关系表。比如我们可以说user 【IS 
 * A】 subject
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SubjectPermission implements Serializable {

    private static final long serialVersionUID = 160341189;

    private ULong         id;
    private UByte         subjectType;
    private ULong         subjectIdentifier;
    private ULong         persissionTargetId;
    private ULong         permissionActionId;
    private LocalDateTime createTime;
    private ULong         createUserId;
    private ULong         isDeleted;

    public SubjectPermission() {}

    public SubjectPermission(SubjectPermission value) {
        this.id = value.id;
        this.subjectType = value.subjectType;
        this.subjectIdentifier = value.subjectIdentifier;
        this.persissionTargetId = value.persissionTargetId;
        this.permissionActionId = value.permissionActionId;
        this.createTime = value.createTime;
        this.createUserId = value.createUserId;
        this.isDeleted = value.isDeleted;
    }

    public SubjectPermission(
        ULong         id,
        UByte         subjectType,
        ULong         subjectIdentifier,
        ULong         persissionTargetId,
        ULong         permissionActionId,
        LocalDateTime createTime,
        ULong         createUserId,
        ULong         isDeleted
    ) {
        this.id = id;
        this.subjectType = subjectType;
        this.subjectIdentifier = subjectIdentifier;
        this.persissionTargetId = persissionTargetId;
        this.permissionActionId = permissionActionId;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.isDeleted = isDeleted;
    }

    public ULong getId() {
        return this.id;
    }

    public void setId(ULong id) {
        this.id = id;
    }

    public UByte getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(UByte subjectType) {
        this.subjectType = subjectType;
    }

    public ULong getSubjectIdentifier() {
        return this.subjectIdentifier;
    }

    public void setSubjectIdentifier(ULong subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    public ULong getPersissionTargetId() {
        return this.persissionTargetId;
    }

    public void setPersissionTargetId(ULong persissionTargetId) {
        this.persissionTargetId = persissionTargetId;
    }

    public ULong getPermissionActionId() {
        return this.permissionActionId;
    }

    public void setPermissionActionId(ULong permissionActionId) {
        this.permissionActionId = permissionActionId;
    }

    public LocalDateTime getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public ULong getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(ULong createUserId) {
        this.createUserId = createUserId;
    }

    public ULong getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(ULong isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SubjectPermission (");

        sb.append(id);
        sb.append(", ").append(subjectType);
        sb.append(", ").append(subjectIdentifier);
        sb.append(", ").append(persissionTargetId);
        sb.append(", ").append(permissionActionId);
        sb.append(", ").append(createTime);
        sb.append(", ").append(createUserId);
        sb.append(", ").append(isDeleted);

        sb.append(")");
        return sb.toString();
    }
}
