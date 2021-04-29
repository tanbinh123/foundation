package com.wuda.foundation.core.user.impl;

import com.wuda.foundation.core.user.*;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.jooq.code.generation.user.tables.records.*;
import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.wuda.foundation.jooq.code.generation.user.Tables.USER_CREDENTIAL;
import static com.wuda.foundation.jooq.code.generation.user.tables.UserCore.USER_CORE;
import static com.wuda.foundation.jooq.code.generation.user.tables.UserEmail.USER_EMAIL;
import static com.wuda.foundation.jooq.code.generation.user.tables.UserPhone.USER_PHONE;
import static com.wuda.foundation.jooq.code.generation.user.tables.UserPrincipal.USER_PRINCIPAL;

public class UserManagerImpl extends AbstractUserManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    protected void createUserCoreDbOp(CreateUserCore createUserCore, Long opUserId) {
        insert(dataSource, USER_CORE, userCoreRecordForInsert(createUserCore, opUserId));
    }

    @Override
    protected void directBatchInsertUserCoreDbOp(List<CreateUserCore> userList, Long opUserId) {
        batchInsert(dataSource, USER_CORE, userCoreRecordsForInsert(userList, opUserId));
    }

    @Override
    protected void createUserPrincipalDbOp(CreateUserPrincipal createUserPrincipal, Long opUserId) throws AlreadyExistsException {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(USER_PRINCIPAL.USER_PRICINPAL_ID)
                .from(USER_PRINCIPAL)
                .where(USER_PRINCIPAL.NAME.eq(createUserPrincipal.getName()))
                .and(USER_PRINCIPAL.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        CreateResult result = insertAfterSelectCheck(dataSource, USER_PRINCIPAL, userPrincipalRecordForInsert(createUserPrincipal, opUserId), existsRecordSelector);
        if (result.getExistsRecordId() != null) {
            throw new AlreadyExistsException("principal = " + createUserPrincipal.getName() + ",已经存在");
        }
    }

    @Override
    protected void createUserCredentialDbOp(CreateUserCredential createUserCredential, Long opUserId) {
        insert(dataSource, USER_CREDENTIAL, userCredentialRecordForInsert(createUserCredential, opUserId));
    }

    @Override
    protected void directBatchInsertUserPrincipalDbOp(List<CreateUserPrincipal> userPrincipals, Long opUserId) {
        batchInsert(dataSource, USER_PRINCIPAL, userPrincipalRecordsForInsert(userPrincipals, opUserId));
    }

    @Override
    protected Long bindUserEmailDbOp(BindUserEmail bindUserEmail, CreateMode createMode, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(USER_EMAIL.ID)
                .from(USER_EMAIL)
                .where(USER_EMAIL.USER_ID.eq(ULong.valueOf(bindUserEmail.getUserId())))
                .and(USER_EMAIL.EMAIL_ID.eq(ULong.valueOf(bindUserEmail.getEmailId())))
                .and(USER_EMAIL.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return insertDispatcher(dataSource, createMode, USER_EMAIL, userEmailRecordForInsert(bindUserEmail, opUserId), existsRecordSelector).getRecordId();
    }

    @Override
    protected void directBatchBindUserEmailDbOp(List<BindUserEmail> bindUserEmails, Long opUserId) {
        batchInsert(dataSource, USER_EMAIL, userEmailRecordsForInsert(bindUserEmails, opUserId));
    }

    @Override
    protected Long bindUserPhoneDbOp(BindUserPhone bindUserPhone, CreateMode createMode, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(USER_PHONE.ID)
                .from(USER_PHONE)
                .where(USER_PHONE.USER_ID.eq(ULong.valueOf(bindUserPhone.getUserId())))
                .and(USER_PHONE.PHONE_ID.eq(ULong.valueOf(bindUserPhone.getPhoneId())))
                .and(USER_PHONE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return insertDispatcher(dataSource, createMode, USER_PHONE, userPhoneRecordForInsert(bindUserPhone, opUserId), existsRecordSelector).getRecordId();
    }

    @Override
    protected void directBatchBindUserPhoneDbOp(List<BindUserPhone> bindUserPhones, Long opUserId) {
        batchInsert(dataSource, USER_PHONE, userPhoneRecordsForInsert(bindUserPhones, opUserId));
    }

    private List<UserCoreRecord> userCoreRecordsForInsert(List<CreateUserCore> createUserCores, Long opUserId) {
        List<UserCoreRecord> list = new ArrayList<>(createUserCores.size());
        for (CreateUserCore createUserCore : createUserCores) {
            list.add(userCoreRecordForInsert(createUserCore, opUserId));
        }
        return list;
    }

    private UserCoreRecord userCoreRecordForInsert(CreateUserCore createUserCore, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserCoreRecord(ULong.valueOf(createUserCore.getId()),
                ULong.valueOf(createUserCore.getUserId()),
                UByte.valueOf(createUserCore.getRepresent()),
                UByte.valueOf(createUserCore.getUserType()),
                UByte.valueOf(createUserCore.getUserState()),
                createUserCore.getCanSignIn(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private UserPrincipalRecord userPrincipalRecordForInsert(CreateUserPrincipal createUserPrincipal, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserPrincipalRecord(ULong.valueOf(createUserPrincipal.getId()),
                ULong.valueOf(createUserPrincipal.getUserId()),
                UByte.valueOf(createUserPrincipal.getType()),
                createUserPrincipal.getName(),
                createUserPrincipal.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<UserPrincipalRecord> userPrincipalRecordsForInsert(List<CreateUserPrincipal> createUserPrincipals, Long opUserId) {
        List<UserPrincipalRecord> list = new ArrayList<>(createUserPrincipals.size());
        for (CreateUserPrincipal createUserPrincipal : createUserPrincipals) {
            list.add(userPrincipalRecordForInsert(createUserPrincipal, opUserId));
        }
        return list;
    }

    private UserCredentialRecord userCredentialRecordForInsert(CreateUserCredential createUserCredential, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserCredentialRecord(ULong.valueOf(createUserCredential.getId()),
                ULong.valueOf(createUserCredential.getUserId()),
                UByte.valueOf(createUserCredential.getType()),
                createUserCredential.getValue(),
                createUserCredential.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private UserEmailRecord userEmailRecordForInsert(BindUserEmail bindUserEmail, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserEmailRecord(ULong.valueOf(bindUserEmail.getId()),
                ULong.valueOf(bindUserEmail.getUserId()),
                ULong.valueOf(bindUserEmail.getEmailId()),
                UByte.valueOf(bindUserEmail.getUsedFor()),
                UByte.valueOf(bindUserEmail.getState()),
                bindUserEmail.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<UserEmailRecord> userEmailRecordsForInsert(List<BindUserEmail> bindUserEmails, Long opUserId) {
        List<UserEmailRecord> list = new ArrayList<>(bindUserEmails.size());
        for (BindUserEmail bindUserEmail : bindUserEmails) {
            list.add(userEmailRecordForInsert(bindUserEmail, opUserId));
        }
        return list;
    }

    private UserPhoneRecord userPhoneRecordForInsert(BindUserPhone bindUserPhone, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserPhoneRecord(ULong.valueOf(bindUserPhone.getId()),
                ULong.valueOf(bindUserPhone.getUserId()),
                ULong.valueOf(bindUserPhone.getPhoneId()),
                UByte.valueOf(bindUserPhone.getUsedFor()),
                UByte.valueOf(bindUserPhone.getState()),
                bindUserPhone.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<UserPhoneRecord> userPhoneRecordsForInsert(List<BindUserPhone> bindUserPhones, Long opUserId) {
        List<UserPhoneRecord> list = new ArrayList<>(bindUserPhones.size());
        for (BindUserPhone bindUserPhone : bindUserPhones) {
            list.add(userPhoneRecordForInsert(bindUserPhone, opUserId));
        }
        return list;
    }
}
