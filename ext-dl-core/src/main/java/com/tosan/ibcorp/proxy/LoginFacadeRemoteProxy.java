package com.extdl.proxy;

import com.kishware.ebank.channelmanager.bank.constraint.login.BlockUserException;
import com.kishware.ebank.channelmanager.bank.context.Context;
import com.kishware.ebank.channelmanager.bank.dto.login.ChangePasswordDto;
import com.kishware.ebank.channelmanager.bank.dto.login.LoginResultDto;
import com.kishware.ebank.channelmanager.bank.dto.login.UserInfoRequestDto;
import com.kishware.ebank.channelmanager.bank.dto.login.UserInfoResponseDto;
import com.kishware.ebank.channelmanager.bank.dto.otp.OtpChallengeCredentialDto;
import com.kishware.ebank.channelmanager.bank.ejb.InvalidCredentialsException;
import com.kishware.ebank.channelmanager.bank.ejb.login.LoginFacadeRemote;
import com.kishware.ebank.channelmanager.client.locator.ServiceLocator;

/**
 * @author ahadi
 * @since 7/16/12
 */
public class LoginFacadeRemoteProxy implements LoginFacadeRemote {
    private ServiceLocator serviceLocator;
    private LoginFacadeRemote loginFacadeRemote;

    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    @Override
    public LoginResultDto login(Context context) throws InvalidCredentialsException, BlockUserException {
        prepareLoginFacadeRemote();
        return loginFacadeRemote.login(context);
    }

    @Override
    public String getUserName(Context ctx) {
        prepareLoginFacadeRemote();
        return loginFacadeRemote.getUserName(ctx);
    }

    @Override
    public UserInfoResponseDto getUserInfo(Context ctx) {
        prepareLoginFacadeRemote();
        return loginFacadeRemote.getUserInfo(ctx);
    }

    @Override
    public UserInfoResponseDto getUserInfo(Context ctx, UserInfoRequestDto dto) {
        prepareLoginFacadeRemote();
        return loginFacadeRemote.getUserInfo(ctx, dto);
    }

    @Override
    public void changePassword(Context context, ChangePasswordDto dto) throws InvalidCredentialsException {
        prepareLoginFacadeRemote();
        loginFacadeRemote.changePassword(context, dto);
    }

    @Override
    public void logout(Context context) {
        prepareLoginFacadeRemote();
        loginFacadeRemote.logout(context);
    }

    @Override
    public OtpChallengeCredentialDto getChallengeOTP3() {
        prepareLoginFacadeRemote();
        return loginFacadeRemote.getChallengeOTP3();
    }

    private void prepareLoginFacadeRemote() {
        loginFacadeRemote = getFacade(LoginFacadeRemote.class);
    }

    protected <T> T getFacade(Class<T> facadeName) {
        return serviceLocator.getFacade(facadeName);
    }
}
