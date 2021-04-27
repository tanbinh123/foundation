package com.wuda.foundation.core.store.impl;

import com.wuda.foundation.core.security.BuiltinRole;
import com.wuda.foundation.core.store.AbstractStoreManager;
import com.wuda.foundation.core.store.CreateStoreCore;
import com.wuda.foundation.core.store.CreateStoreGeneral;
import com.wuda.foundation.core.store.UpdateStoreGeneral;
import com.wuda.foundation.core.user.CreateUserBelongsToGroupRoleRequest;
import com.wuda.foundation.core.user.UserBelongsToGroupManager;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.jooq.code.generation.store.tables.records.StoreCoreRecord;
import com.wuda.foundation.jooq.code.generation.store.tables.records.StoreGeneralRecord;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.LongIdentifier;
import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static com.wuda.foundation.jooq.code.generation.store.tables.StoreCore.STORE_CORE;
import static com.wuda.foundation.jooq.code.generation.store.tables.StoreGeneral.STORE_GENERAL;

public class StoreManagerImpl extends AbstractStoreManager implements JooqCommonDbOp {

    private UserBelongsToGroupManager userBelongsToGroupManager;

    public void setUserBelongsToGroupManager(UserBelongsToGroupManager userBelongsToGroupManager) {
        this.userBelongsToGroupManager = userBelongsToGroupManager;
    }


    @Override
    protected void directBatchInsertStoreDbOp(List<CreateStoreCore> createStoreCores, Long opUserId) {
        List<StoreCoreRecord> records = new ArrayList<>(createStoreCores.size());
        for (CreateStoreCore createStoreCore : createStoreCores) {
            records.add(storeRecordForInsert(createStoreCore, opUserId));
        }
        batchInsert(JooqContext.getDataSource(), STORE_CORE, records);
    }

    @Override
    public long createStoreCoreDbOp(Long ownerUserId, CreateStoreCore createStoreCore, Long opUserId) {
        long storeCoreId = insert(JooqContext.getDataSource(), STORE_CORE, storeRecordForInsert(createStoreCore, opUserId)).getRecordId();

        CreateUserBelongsToGroupRoleRequest createUserBelongsToGroupRoleRequest = new CreateUserBelongsToGroupRoleRequest.Builder()
                .setId(ownerUserId)
                .setUserId(ownerUserId)
                .setGroup(new LongIdentifier(createStoreCore.getStoreId(), BuiltinIdentifierType.FOUNDATION_STORE))
                .setPermissionRoleId(BuiltinRole.USER_BELONGS_TO_GROUP_STORE_OWNER.getCode())
                .build();
        userBelongsToGroupManager.createUserBelongsToGroupRole(createUserBelongsToGroupRoleRequest, CreateMode.CREATE_AFTER_SELECT_CHECK, opUserId);
        return storeCoreId;
    }

    @Override
    protected void directBatchInsertStoreGeneralDbOp(List<CreateStoreGeneral> createStoreGenerals, Long opUserId) {
        List<StoreGeneralRecord> records = new ArrayList<>(createStoreGenerals.size());
        for (CreateStoreGeneral createStoreGeneral : createStoreGenerals) {
            records.add(storeGeneralRecordForInsert(createStoreGeneral, opUserId));
        }
        batchInsert(JooqContext.getDataSource(), STORE_GENERAL, records);
    }

    @Override
    public long createOrUpdateStoreGeneralDbOp(CreateStoreGeneral createStoreGeneral, CreateMode createMode, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(JooqContext.getDataSource());
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(STORE_GENERAL.STORE_GENERAL_ID)
                .from(STORE_GENERAL)
                .where(STORE_GENERAL.STORE_ID.eq(ULong.valueOf(createStoreGeneral.getStoreId())))
                .and(STORE_GENERAL.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        Consumer<Long> updateAction = (storeGeneralId) -> {
            UpdateStoreGeneral updateStoreGeneral = UpdateStoreGeneral.from(storeGeneralId, createStoreGeneral);
            updateStoreGeneralByIdDbOp(updateStoreGeneral, opUserId);
        };
        return insertOrUpdate(createMode, JooqContext.getDataSource(), STORE_GENERAL, storeGeneralRecordForInsert(createStoreGeneral, opUserId), existsRecordSelector, updateAction);
    }

    @Override
    public void updateStoreGeneralByIdDbOp(UpdateStoreGeneral updateStoreGeneral, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        StoreGeneralRecord storeGeneralRecord = new StoreGeneralRecord();
        storeGeneralRecord.setStoreGeneralId(ULong.valueOf(updateStoreGeneral.getId()));
        if (Objects.nonNull(updateStoreGeneral.getStoreName())) {
            storeGeneralRecord.setStoreName(updateStoreGeneral.getStoreName());
        }
        storeGeneralRecord.setLastModifyTime(now);
        storeGeneralRecord.setLastModifyUserId(ULong.valueOf(opUserId));
        Configuration configuration = JooqContext.getConfiguration(JooqContext.getDataSource());
        storeGeneralRecord.attach(configuration);
        storeGeneralRecord.update();
    }

    private StoreGeneralRecord storeGeneralRecordForInsert(CreateStoreGeneral createStoreGeneral, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new StoreGeneralRecord(ULong.valueOf(createStoreGeneral.getId()),
                ULong.valueOf(createStoreGeneral.getStoreId()),
                createStoreGeneral.getStoreName(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private StoreCoreRecord storeRecordForInsert(CreateStoreCore createStoreCore, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new StoreCoreRecord(ULong.valueOf(createStoreCore.getId()),
                ULong.valueOf(createStoreCore.getStoreId()),
                UByte.valueOf(createStoreCore.getStoreType()),
                UByte.valueOf(createStoreCore.getStoreState()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }
}
