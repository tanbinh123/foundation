package com.wuda.foundation.core.security.impl;

import com.wuda.foundation.core.security.*;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.jooq.code.generation.security.tables.records.PermissionAssignmentRecord;
import com.wuda.foundation.lang.FoundationConfiguration;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.identify.IdentifierType;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.UpdateConditionStep;
import org.jooq.types.UByte;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.wuda.foundation.jooq.code.generation.security.tables.PermissionAssignment.PERMISSION_ASSIGNMENT;

public class PermissionGrantManagerImpl extends AbstractPermissionGrantManager implements JooqCommonDbOp {

    @Override
    protected void createAssignmentDbOp(Subject subject, Set<Target> targetSet, PermissionEffect permissionEffect, Long version, Long opUserId) {
        for (Target target : targetSet) {
            createAssignmentDbOp(subject, target, permissionEffect, version, opUserId);
        }
    }

    protected void createAssignmentDbOp(Subject subject, Target target, PermissionEffect permissionEffect, Long version, Long opUserId) {
        if (assignedTargetToSubject(subject, target, permissionEffect)) {
            return;
        }
        List<PermissionAssignmentRecord> records = queryPermissionAssignmentRecords(subject, target);
        if (records != null && records.size() > 0) {
            // 之前已经建立过关联
            deletePermissionAssignment(subject, target, null);
        }
        PermissionAssignmentRecord record = permissionAssignmentRecordForInsert(subject, target, Action.virtual(), permissionEffect, version, opUserId);
        insert(JooqContext.getDataSource(), PERMISSION_ASSIGNMENT, record);
    }

    private PermissionAssignmentRecord permissionAssignmentRecordForInsert(Subject subject,
                                                                           Target target,
                                                                           Action action,
                                                                           PermissionEffect permissionEffect,
                                                                           Long version,
                                                                           Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new PermissionAssignmentRecord(
                ULong.valueOf(FoundationConfiguration.getGlobalSingletonInstance().getLongKeyGenerator().next()),
                UByte.valueOf(subject.getType().getCode()),
                ULong.valueOf(subject.getValue()),
                UShort.valueOf(target.getType().getCode()),
                ULong.valueOf(target.getValue()),
                UShort.valueOf(action.getType().getCode()),
                ULong.valueOf(action.getValue()),
                PermissionEffect.allow(permissionEffect),
                ULong.valueOf(version),
                now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    protected List<PermissionAssignmentRecord> queryPermissionAssignmentRecords(Subject subject, Target target) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        SelectConditionStep<PermissionAssignmentRecord> selectWhereStep = dslContext.selectFrom(PERMISSION_ASSIGNMENT)
                .where(PERMISSION_ASSIGNMENT.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(PERMISSION_ASSIGNMENT.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        if (target != null) {
            selectWhereStep.and(PERMISSION_ASSIGNMENT.TARGET_TYPE.eq(UShort.valueOf(target.getType().getCode())))
                    .and(PERMISSION_ASSIGNMENT.TARGET_IDENTIFIER.eq(ULong.valueOf(target.getValue())));
        }
        return selectWhereStep.fetch();
    }


    protected List<PermissionAssignmentRecord> queryPermissionAssignmentRecords2(Subject subject, IdentifierType targetType) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        SelectConditionStep<PermissionAssignmentRecord> selectWhereStep = dslContext.selectFrom(PERMISSION_ASSIGNMENT)
                .where(PERMISSION_ASSIGNMENT.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(PERMISSION_ASSIGNMENT.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        if (targetType != null) {
            selectWhereStep.and(PERMISSION_ASSIGNMENT.TARGET_TYPE.eq(UShort.valueOf(targetType.getCode())));
        }
        return selectWhereStep.fetch();
    }

    @Override
    protected void createAssignmentDbOp(Subject subject, Target target, Set<Action> actionSet, PermissionEffect permissionEffect, Long version, Long opUserId) {
        for (Action action : actionSet) {
            Objects.requireNonNull(action);
            createAssignmentDbOp(subject, target, action, permissionEffect, version, opUserId);
        }
    }

    protected void createAssignmentDbOp(Subject subject, Target target, Action action, PermissionEffect permissionEffect, Long version, Long opUserId) {
        PermissionAssignmentRecord permissionAssignmentRecord = queryPermissionAssignmentRecord(subject, target, action);
        if (permissionAssignmentRecord != null
                && PermissionEffect.equals(permissionEffect, permissionAssignmentRecord.getEffect())) {
            // 已经分配过
            return;
        }
        if (permissionAssignmentRecord != null) {
            deletePermissionAssignment(subject, target, action);
        }
        PermissionAssignmentRecord record = permissionAssignmentRecordForInsert(subject, target, action, permissionEffect, version, opUserId);
        insert(JooqContext.getDataSource(), PERMISSION_ASSIGNMENT, record);
    }

    protected void deletePermissionAssignment(Subject subject, Target target, Action action) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        UpdateConditionStep<PermissionAssignmentRecord> updateConditionStep = dslContext.update(PERMISSION_ASSIGNMENT)
                .set(PERMISSION_ASSIGNMENT.IS_DELETED, PERMISSION_ASSIGNMENT.ID)
                .where(PERMISSION_ASSIGNMENT.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(PERMISSION_ASSIGNMENT.TARGET_TYPE.eq(UShort.valueOf(target.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.TARGET_IDENTIFIER.eq(ULong.valueOf(target.getValue())));
        if (action != null) {
            updateConditionStep.and(PERMISSION_ASSIGNMENT.ACTION_TYPE.eq(UShort.valueOf(action.getType().getCode())))
                    .and(PERMISSION_ASSIGNMENT.ACTION_IDENTIFIER.eq(ULong.valueOf(action.getValue())));
        }
        updateConditionStep.execute();
    }

    protected PermissionAssignmentRecord queryPermissionAssignmentRecord(Subject subject, Target target, Action action) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        return dslContext.selectFrom(PERMISSION_ASSIGNMENT)
                .where(PERMISSION_ASSIGNMENT.SUBJECT_TYPE.eq(UByte.valueOf(subject.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.SUBJECT_IDENTIFIER.eq(ULong.valueOf(subject.getValue())))
                .and(PERMISSION_ASSIGNMENT.TARGET_TYPE.eq(UShort.valueOf(target.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.TARGET_IDENTIFIER.eq(ULong.valueOf(target.getValue())))
                .and(PERMISSION_ASSIGNMENT.ACTION_TYPE.eq(UShort.valueOf(action.getType().getCode())))
                .and(PERMISSION_ASSIGNMENT.ACTION_IDENTIFIER.eq(ULong.valueOf(action.getValue())))
                .and(PERMISSION_ASSIGNMENT.IS_DELETED.eq(notDeleted()))
                .fetchOne();
    }

    @Override
    protected void clearAssignmentDbOp(Subject subject, Set<Target> targetSet, Long opUserId) {
        for (Target target : targetSet) {
            deletePermissionAssignment(subject, target, null);
        }
    }

    @Override
    protected void clearAssignmentDbOp(Subject subject, Target target, Set<Action> actionSet, Long opUserId) {
        for (Action action : actionSet) {
            deletePermissionAssignment(subject, target, action);
        }
    }

    @Override
    protected List<DescribePermissionAssignment> getPermissionsDbOp(Subject subject, IdentifierType targetType) {
        List<PermissionAssignmentRecord> permissionAssignmentRecords = queryPermissionAssignmentRecords2(subject, targetType);
        return EntityConverter.fromAssignmentRecords(permissionAssignmentRecords);
    }

    @Override
    protected List<DescribePermissionAssignment> getPermissionsDbOp(List<Subject> subjects, IdentifierType targetType) {
        if (subjects == null || subjects.isEmpty()) {
            return null;
        }
        List<DescribePermissionAssignment> list = new ArrayList<>();
        for (Subject subject : subjects) {
            List<DescribePermissionAssignment> permissions = getPermissionsDbOp(subject, targetType);
            if (permissions != null && !permissions.isEmpty()) {
                list.addAll(permissions);
            }
        }
        return list;
    }

    @Override
    protected boolean assignedTargetToSubjectDbOp(Subject subject, Target target, PermissionEffect permissionEffect) {
        List<PermissionAssignmentRecord> records = queryPermissionAssignmentRecords(subject, target);
        if (records == null) {
            return false;
        }
        for (PermissionAssignmentRecord record : records) {
            DescribePermissionAssignment describePermissionAssignment = EntityConverter.fromAssignmentRecord(record);
            boolean assigned = DescribePermissionAssignment.assignWholeTarget(describePermissionAssignment);
            if (assigned) {
                return true;
            }
        }
        return false;
    }
}
