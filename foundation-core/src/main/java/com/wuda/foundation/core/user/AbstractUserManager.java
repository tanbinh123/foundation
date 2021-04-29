package com.wuda.foundation.core.user;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.identify.Identifier;

import java.util.List;
import java.util.Objects;

/**
 * 抽象实现,用于统一数据校验,设置和更新缓存等逻辑.比如,不管数据保存到MySQL还是MongoDB中,
 * 数据模型的完整性校验是一样的,因此,在接口和具体的存储实现类之间,加一层抽象实现,用于
 * 统一处理这些操作.
 *
 * @author wuda
 */
public abstract class AbstractUserManager implements UserManager {
    @Override
    public void createUserCore(CreateUserCore createUserCore, Long opUserId) {
        createUserCoreDbOp(createUserCore, opUserId);
    }

    protected abstract void createUserCoreDbOp(CreateUserCore createUserCore, Long opUserId);

    @Override
    public void directBatchInsertUserCore(List<CreateUserCore> userCores, Long opUserId) {
        directBatchInsertUserCoreDbOp(userCores, opUserId);
    }

    protected abstract void directBatchInsertUserCoreDbOp(List<CreateUserCore> userList, Long opUserId);

    @Override
    public void createUserPrincipal(CreateUserPrincipal createUserPrincipal, Long opUserId) throws AlreadyExistsException {
        createUserPrincipalDbOp(createUserPrincipal, opUserId);
    }

    protected abstract void createUserPrincipalDbOp(CreateUserPrincipal createUserPrincipal, Long opUserId) throws AlreadyExistsException;

    @Override
    public void createUserCredential(CreateUserCredential createUserCredential, Long opUserId) {
        createUserCredentialDbOp(createUserCredential, opUserId);
    }

    protected abstract void createUserCredentialDbOp(CreateUserCredential createUserCredential, Long opUserId);

    @Override
    public void directBatchInsertUserPrincipal(List<CreateUserPrincipal> userPrincipals, Long opUserId) {
        directBatchInsertUserPrincipalDbOp(userPrincipals, opUserId);
    }

    protected abstract void directBatchInsertUserPrincipalDbOp(List<CreateUserPrincipal> userPrincipals, Long opUserId);

    @Override
    public Long bindUserEmail(BindUserEmail bindUserEmail, CreateMode createMode, Long opUserId) {
        return bindUserEmailDbOp(bindUserEmail, createMode, opUserId);
    }

    protected abstract Long bindUserEmailDbOp(BindUserEmail bindUserEmail, CreateMode createMode, Long opUserId);

    @Override
    public void directBatchBindUserEmail(List<BindUserEmail> bindUserEmails, Long opUserId) {
        directBatchBindUserEmailDbOp(bindUserEmails, opUserId);
    }

    protected abstract void directBatchBindUserEmailDbOp(List<BindUserEmail> bindUserEmails, Long opUserId);

    @Override
    public Long bindUserPhone(BindUserPhone bindUserPhone, CreateMode createMode, Long opUserId) {
        return bindUserPhoneDbOp(bindUserPhone, createMode, opUserId);
    }

    protected abstract Long bindUserPhoneDbOp(BindUserPhone bindUserPhone, CreateMode createMode, Long opUserId);

    @Override
    public void directBatchBindUserPhone(List<BindUserPhone> bindUserPhones, Long opUserId) {
        directBatchBindUserPhoneDbOp(bindUserPhones, opUserId);
    }

    protected abstract void directBatchBindUserPhoneDbOp(List<BindUserPhone> bindUserPhones, Long opUserId);

    @Override
    public void updatePassword(Long userId, String newPassword) {

    }

    @Override
    public void changeUserState(Long userId, Byte newState) {

    }

    @Override
    public DescribeUser getUser(Long userId) {
        return null;
    }

}
