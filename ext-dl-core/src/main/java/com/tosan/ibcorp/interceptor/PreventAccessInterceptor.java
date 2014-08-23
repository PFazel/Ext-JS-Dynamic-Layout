package com.extdl.interceptor;

import com.kishware.ebank.channelmanager.bank.dto.service.ServiceConstraintInformationDto;
import com.kishware.ebank.menu.ChannelManagerServiceMap;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.extdl.action.BaseAction;

import java.util.Map;

/**
 * This interceptor checks that the current user have access to the invoked action.
 *
 * @author Hooman Noroozinia
 * @since 1/15/11
 */
public class PreventAccessInterceptor implements Interceptor {

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        if (!(actionInvocation.getAction() instanceof BaseAction)) {
            return Action.LOGIN;
        }
        String actionName = actionInvocation.getInvocationContext().getName();
        String channelManagerServiceNameForAction =
                ChannelManagerServiceMap.getChannelManagerServiceNameForAction(actionName);
        if (ChannelManagerServiceMap.containsAction(actionName) && !channelManagerServiceNameForAction.equals("")) {
            BaseAction baseAction = (BaseAction) actionInvocation.getAction();
            Map<String, ServiceConstraintInformationDto> serviceConstraints =
                    (Map<String, ServiceConstraintInformationDto>) baseAction.getHttpServletRequest().getSession()
                            .getAttribute(BaseAction.USER_ACTIVATED_SERVICES);
            if (serviceConstraints != null) {
                ServiceConstraintInformationDto service = serviceConstraints.get(channelManagerServiceNameForAction);
                if (service != null) {
                    return actionInvocation.invoke();
                }
            }
            return "unauthorized";
        }
        return actionInvocation.invoke();
    }
}
