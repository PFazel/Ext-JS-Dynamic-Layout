package com.extdl.action.login;

import com.kishware.ebank.channelmanager.bank.constraint.login.BlockUserException;
import com.kishware.ebank.channelmanager.bank.context.Context;
import com.kishware.ebank.channelmanager.bank.context.LanguageType;
import com.kishware.ebank.channelmanager.bank.dto.login.LoginResultDto;
import com.kishware.ebank.channelmanager.bank.dto.service.ServiceConstraintInformationDto;
import com.kishware.ebank.channelmanager.bank.ejb.InvalidCredentialsException;
import com.kishware.ebank.channelmanager.bank.ejb.login.LoginFacadeRemote;
import com.kishware.ebank.channelmanager.bank.ejb.setting.UserChannelSettingFacadeRemote;
import com.kishware.ebank.menu.MenuComponent;
import com.kishware.ebank.menu.MenuGenerator;
import com.opensymphony.xwork2.ActionContext;
import com.extdl.action.BaseAction;
import com.extdl.tag.TagHelper;
import com.extdl.util.Restrictor;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author ahadi
 * @since 12/23/12
 */
public class LoginAction extends BaseAction implements SessionAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAction.class);
    private String username;
    private String password;
    private MenuGenerator menuGenerator;
    private Restrictor restrictor;
    private LoginResultDto loginResult;
    private LoginFacadeRemote loginFacadeRemote;
    private List<MenuComponent> menuComponents;
    private static final Logger log = LoggerFactory.getLogger(LoginAction.class.getName());
    private static final String VERSION = "1.0";
    private static final String USER_NAME = "USER_NAME";
    public static final String LAST_LOGIN_KEY = "LastLogin";
    private static final String LOGIN_TYPE = "LOGIN_TYPE";
    private LoginType loginType;
    private Map session;
    private UserChannelSettingFacadeRemote userChannelSettingFacadeRemote;
    private boolean captchaRequired;
    private List<LoginType> loginTypes;

    public String execute() {
        restrictor.newRequest(httpServletRequest);
        if (!validateInput()) {
            return INPUT;
        }
        httpServletRequest.getSession().setAttribute("COMPLETE_LOGIN", false);
        try {
            loginResult = loginFacadeRemote.login(getContext(LoginType.STATIC_PASSWORD));
            setContextParams(loginResult);
            createNewSession();
//            setMenuComponents(setUserServiceConstraintsInfo());
            return SUCCESS;

        } catch (BlockUserException e) {
            log.info("Invalid username or password for user: ", getUsername());
            addActionError(getText("login.inValidUserNameOrPasswordOrBlocked"));
        } catch (InvalidCredentialsException e) {
            log.info("Invalid username or password for user: ", getUsername());
            addActionError(getText("login.inValidUserNameOrPasswordOrBlocked"));
        }
        return INPUT;
    }

    public String loginPage() {
/*
        if (httpServletRequest.getSession().getAttribute(DISPATCHER_APPLIED) == null ||
                (!(Boolean) httpServletRequest.getSession().getAttribute(DISPATCHER_APPLIED))) {
            return "dispatcher";
        }
        httpServletRequest.getSession().removeAttribute(DISPATCHER_APPLIED);
*/
        invalidateSessionExceptChannelName();
        captchaRequired = restrictor.isCaptchaRequired(httpServletRequest);
//        loginTypes = loginTypesAndPrioritySpecifier.prepareOrderedLoginTypes();
        return INPUT;
    }

    private void invalidateSessionExceptChannelName() {
        Locale lang;
        lang = (httpServletRequest.getSession().getAttribute(TagHelper.SESSION_LOCALE_ATTRIBUTE_NAME) != null) ?
                (Locale) httpServletRequest.getSession().getAttribute(TagHelper.SESSION_LOCALE_ATTRIBUTE_NAME) : null;
        try {
            if (getContext() != null) {
                loginFacadeRemote.logout(getContext());
            }
        } catch (Exception e) {
            // skip
        }
        String channelName = (String) httpServletRequest.getSession().getAttribute(CHANNEL_NAME);
        String mobileType = (String) httpServletRequest.getSession().getAttribute(MOBILE_TYPE);
        invalidateSession();
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute(CHANNEL_NAME, channelName);
        httpSession.setAttribute(MOBILE_TYPE, mobileType);
        if (lang != null) {
            httpSession.setAttribute(TagHelper.SESSION_LOCALE_ATTRIBUTE_NAME, lang);
        }
    }

    private void invalidateSession() {
        if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
            try {
                ((org.apache.struts2.dispatcher.SessionMap) session).invalidate();
            } catch (IllegalStateException e) {
                if (log.isDebugEnabled()) {
                    log.error("Session is already invalidated.", e);
                } else {
                    log.info("Session is already invalidated.");
                }
            }
        }
    }


    private Context getContext(LoginType loginType) {
        Context context = new Context();
        context.addData(Context.CLIENT_ADDRESS, getClientAddress());
        context.addData(Context.USERNAME, username);
        context.addData(Context.PASSWORD, password);

        context.addData(Context.CHANNELNAME, (httpServletRequest.getSession().getAttribute("channelName") != null) ?
                (String) httpServletRequest.getSession().getAttribute(CHANNEL_NAME) : channelName);

        context.addData(Context.VERSION, VERSION);
        switch (loginType) {
            case STATIC_PASSWORD:
                context.addData(Context.AUTHENTICATE_TYPE, Context.AUTHENTICATE_STATIC_USERNAME_CHANNEL);
                break;
            case OTP_COUNTER:
                context.addData(Context.AUTHENTICATE_TYPE, Context.AUTHENTICATE_OTP1);
                break;
            case OTP_PIN_COUNTER:
                context.addData(Context.AUTHENTICATE_TYPE, Context.AUTHENTICATE_OTP2);
                break;
            case OTP_CHALLENGE_RESPONSE:
                context.addData(Context.AUTHENTICATE_TYPE, Context.AUTHENTICATE_OTP3);
                context.addData(Context.CHALLENGE_IDENTIFIER,
                        (String) httpServletRequest.getSession().getAttribute(Context.CHALLENGE_IDENTIFIER));
                break;
            default:
                context.addData(Context.AUTHENTICATE_TYPE, Context.AUTHENTICATE_INTERNAL);
                break;
        }
        //        context.addData(Context.AUTHENTICATE_TYPE, Context.AUTHENTICATE_INTERNAL);
        return context;
    }

    private void setContextParams(LoginResultDto loginResult) {
        httpServletRequest.getSession().setAttribute(Context.SESSIONID, loginResult.getSessionId());
        httpServletRequest.getSession().setAttribute(Context.VERSION, VERSION);
        httpServletRequest.getSession().setAttribute(Context.CLIENT_ADDRESS, getClientAddress());
        setLanguage(getLocale().toString());
        httpServletRequest.getSession().setAttribute(BaseAction.LANGUAGE,
                LanguageType.valueOf((getLanguage() != null) ? getLanguage() : LanguageType.Local.toString()));
        httpServletRequest.getSession().setAttribute(LAST_LOGIN_KEY, loginResult.getLastLoginTime());
    }

    private void createNewSession() {
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        SessionMap sessionMap = (SessionMap) this.session;
        Collection sessionMapCollection = sessionMap.keySet();
        for (Object aSessionMapCollection : sessionMapCollection) {
            String keyName = (String) aSessionMapCollection;
            attributes.put(keyName, (sessionMap).get(keyName));
        }
        sessionMap.invalidate();
        this.session = ActionContext.getContext().getSession();
        for (String key : attributes.keySet()) {
            this.session.put(key, attributes.get(key));
        }
        this.session.put(USER_NAME, username);
        httpServletRequest.getSession().setAttribute(LOGIN_TYPE, getLoginType());
        /*
                for (String key : attributes.keySet()) {
                    ((SessionMap) this.session).put(key, attributes.get(key));
                }
                ((SessionMap) this.session).put(USER_NAME, username);
        */
    }

    public void setMenuComponents(List<MenuComponent> menuComponents) {
        this.menuComponents = menuComponents;
    }

    private List<MenuComponent> setUserServiceConstraintsInfo() {
        Context context = getContext();
        List<ServiceConstraintInformationDto> serviceList =
                userChannelSettingFacadeRemote.getServiceConstraintList(context);
        if (log.isDebugEnabled()) {
            for (ServiceConstraintInformationDto serviceConstraintInformationDto : serviceList) {
                log.debug(serviceConstraintInformationDto.toString());
            }
        }
        Map<String, ServiceConstraintInformationDto> serviceConstraints =
                new HashMap<String, ServiceConstraintInformationDto>();

        for (ServiceConstraintInformationDto serviceConstraintInfo : serviceList) {
            serviceConstraints.put(serviceConstraintInfo.getServiceName(), serviceConstraintInfo);
        }

        httpServletRequest.getSession().setAttribute(USER_ACTIVATED_SERVICES, serviceConstraints);
        List<MenuComponent> menu = getMenuGenerator().generateMenu(serviceList);
        httpServletRequest.getSession().setAttribute("userMenu", menu);
        return menu;
    }


    private boolean validateInput() {
        boolean result = true;
        if (StringUtils.isEmpty(username)) {
            addActionError(getText("error.login.emptyUserName"));
            result = false;
        }
        if (StringUtils.isEmpty(password)) {
            addActionError(getText("error.login.emptyPassword"));
            result = false;
        }
        return result;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MenuGenerator getMenuGenerator() {
        return menuGenerator;
    }

    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public void setRestrictor(Restrictor restrictor) {
        this.restrictor = restrictor;
    }

    public void setLoginFacadeRemote(LoginFacadeRemote loginFacadeRemote) {
        this.loginFacadeRemote = loginFacadeRemote;
    }

    public void setUserChannelSettingFacadeRemote(UserChannelSettingFacadeRemote userChannelSettingFacadeRemote) {
        this.userChannelSettingFacadeRemote = userChannelSettingFacadeRemote;
    }
}
