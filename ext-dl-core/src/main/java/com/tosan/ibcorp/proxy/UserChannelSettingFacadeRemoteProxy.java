package com.extdl.proxy;

import com.kishware.ebank.channelmanager.bank.constraint.general.OTPException;
import com.kishware.ebank.channelmanager.bank.context.Context;
import com.kishware.ebank.channelmanager.bank.dto.bill.BillSettingDto;
import com.kishware.ebank.channelmanager.bank.dto.contact.*;
import com.kishware.ebank.channelmanager.bank.dto.deposit.FavoriteDepositNumberDto;
import com.kishware.ebank.channelmanager.bank.dto.otp.AuthenticationType;
import com.kishware.ebank.channelmanager.bank.dto.otp.PasswordPrefixDto;
import com.kishware.ebank.channelmanager.bank.dto.person.UserNameRequestDto;
import com.kishware.ebank.channelmanager.bank.dto.service.SecondPasswordDto;
import com.kishware.ebank.channelmanager.bank.dto.service.ServiceConstraintInformationDto;
import com.kishware.ebank.channelmanager.bank.dto.tracker.RequestStatusDtos;
import com.kishware.ebank.channelmanager.bank.dto.tracker.RequestTrackerDto;
import com.kishware.ebank.channelmanager.bank.ejb.InvalidCredentialsException;
import com.kishware.ebank.channelmanager.bank.ejb.InvalidUserException;
import com.kishware.ebank.channelmanager.bank.ejb.setting.UserChannelSettingFacadeRemote;
import com.kishware.ebank.channelmanager.client.locator.ServiceLocator;
import com.kishware.ebank.channelmanager.common.DuplicateEntityException;
import com.kishware.ebank.channelmanager.common.EntityNotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * @author ahadi
 * @since 7/16/12
 */
public class UserChannelSettingFacadeRemoteProxy implements UserChannelSettingFacadeRemote {
    private ServiceLocator serviceLocator;
    private UserChannelSettingFacadeRemote userChannelSettingFacadeRemote;

    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    @Override
    public RequestStatusDtos getRequestStatus(Context context, RequestTrackerDto dto) {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.getRequestStatus(context, dto);
    }

    @Override
    public void changeOtpPasswordPrefix(Context context, PasswordPrefixDto passwordPrefixDto) throws
            OTPException, InvalidCredentialsException {
        prepareUserChannelSettingFacadeRemote();
        userChannelSettingFacadeRemote.changeOtpPasswordPrefix(context, passwordPrefixDto);
    }

    @Override
    public void saveUserProfile(Context context, byte[] bytes) {
        prepareUserChannelSettingFacadeRemote();
        userChannelSettingFacadeRemote.saveUserProfile(context, bytes);
    }

    @Override
    public byte[] getUserProfile(Context context) {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.getUserProfile(context);
    }

    @Override
    public Collection<AuthenticationType> getUserOtpTypes(Context context) throws OTPException,
            InvalidCredentialsException {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.getUserOtpTypes(context);
    }

    @Override
    public ContactInfoUpdateDto updateContactInfo(Context context, ContactInfoUpdateDto dto)
            throws EntityNotFoundException, DuplicateEntityException {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.updateContactInfo(context, dto);
    }

    @Override
    public ContactFullInfoResponse getContactFullInfoList(Context context, ContactRequestDto contactRequestDto) {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.getContactFullInfoList(context, contactRequestDto);
    }

    @Override
    public void removeContactInfo(Context context, long contactInfoId) {
        prepareUserChannelSettingFacadeRemote();
        userChannelSettingFacadeRemote.removeContactInfo(context, contactInfoId);
    }

    @Override
    public long addContactInfo(Context context, ContactInfoAddDto dto)
            throws EntityNotFoundException, DuplicateEntityException {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.addContactInfo(context, dto);
    }

    @Override
    public ContactInfoResponse getContactInfoList(Context context, ContactInfoRequestDto dto) {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.getContactInfoList(context, dto);
    }

    @Override
    public ContactDto updateContact(Context context, ContactDto dto)
            throws EntityNotFoundException, DuplicateEntityException {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.updateContact(context, dto);
    }

    @Override
    public void removeContact(Context context, long contactId) {
        prepareUserChannelSettingFacadeRemote();
        userChannelSettingFacadeRemote.removeContact(context, contactId);
    }

    @Override
    public long addContact(Context context, ContactAddDto dto) throws DuplicateEntityException {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.addContact(context, dto);
    }

    @Override
    public ContactResponse getContactList(Context context, ContactRequestDto dto) {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.getContactList(context, dto);
    }

    @Override
    public void changeUserName(Context context, UserNameRequestDto userName)
            throws InvalidUserException, InvalidCredentialsException {
        prepareUserChannelSettingFacadeRemote();
        userChannelSettingFacadeRemote.changeUserName(context, userName);
    }

    @Override
    public void saveSecondPassword(Context context, SecondPasswordDto dto) throws InvalidCredentialsException {
        prepareUserChannelSettingFacadeRemote();
        userChannelSettingFacadeRemote.saveSecondPassword(context, dto);
    }

    @Override
    public List<ServiceConstraintInformationDto> getServiceConstraintList(Context context) {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.getServiceConstraintList(context);
    }

    @Override
    public void updateUserBillSetting(Context context, BillSettingDto dto) {
        prepareUserChannelSettingFacadeRemote();
        userChannelSettingFacadeRemote.updateUserBillSetting(context, dto);
    }

    @Override
    public BillSettingDto getUserBillSetting(Context context) {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.getUserBillSetting(context);
    }

    @Override
    public void updateFavoriteAccountSetting(Context context,
                                             List<FavoriteDepositNumberDto> favoriteAccountSettingEco) {
        prepareUserChannelSettingFacadeRemote();
        userChannelSettingFacadeRemote.updateFavoriteAccountSetting(context, favoriteAccountSettingEco);
    }

    @Override
    public List<FavoriteDepositNumberDto> getFavoriteAccountSettingList(Context context) {
        prepareUserChannelSettingFacadeRemote();
        return userChannelSettingFacadeRemote.getFavoriteAccountSettingList(context);
    }

    private void prepareUserChannelSettingFacadeRemote() {
        userChannelSettingFacadeRemote = getFacade(UserChannelSettingFacadeRemote.class);
    }

    protected <T> T getFacade(Class<T> facadeName) {
        return serviceLocator.getFacade(facadeName);
    }
}
