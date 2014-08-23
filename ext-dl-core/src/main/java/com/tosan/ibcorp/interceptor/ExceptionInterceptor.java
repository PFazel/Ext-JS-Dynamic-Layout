package com.extdl.interceptor;

import com.kishware.ebank.channelmanager.bank.InvalidSessionException;
import com.kishware.ebank.channelmanager.bank.ServiceException;
import com.kishware.ebank.channelmanager.bank.ValidatorException;
import com.kishware.ebank.channelmanager.bank.constraint.general.OTPException;
import com.kishware.ebank.channelmanager.bank.constraint.general.SecondPasswordException;
import com.kishware.ebank.channelmanager.bank.constraint.general.ServiceCallException;
import com.kishware.ebank.channelmanager.bank.constraint.general.TicketException;
import com.kishware.ebank.channelmanager.bank.constraint.login.BlockUserException;
import com.kishware.ebank.channelmanager.bank.constraint.transfer.*;
import com.kishware.ebank.channelmanager.bank.ejb.InvalidCustomerException;
import com.kishware.ebank.channelmanager.bank.interceptor.ServiceAccessIsDeniedException;
import com.kishware.ebank.channelmanager.common.ChannelManagerException;
import com.kishware.ebank.channelmanager.common.PermissionException;
import com.kishware.ebank.epayment.services.exception.ClientAddressException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Parham Fazel
 */
public class ExceptionInterceptor implements Interceptor {
    private static Logger log = LoggerFactory.getLogger(ExceptionInterceptor.class.getName());
    public static final String INPUT = "input";

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionSupport actionSupport = (ActionSupport) actionInvocation.getAction();
        String actionName = actionInvocation.getAction().getClass().getName();
        ResultConfig resultConfig = actionInvocation.getProxy().getConfig().getResults().get("error");
        try {
            return actionInvocation.invoke();
        } catch (SecurityException e) {
            log.info("A security problem has found in parameters. {}", e.getMessage());
            if (log.isDebugEnabled()) {
                log.debug("Security problem: ", e);
            }
            //action.addActionError("securityError");
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.securityRisk.body"));
                return "securityRiskInJSON";
            }
            return "securityRisk";
        } catch (InvalidSessionException e) {
            log.info("InvalidSessionException from channelManager for action = {}", actionName, e);
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError("errorLogout");
                return "logoutInJSON";
            }
            return "logout";
        } catch (BlockUserException e) {
            log.info("BlockUserException from channelManager for action = {}", actionName, e);
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.blockUserExcep"));
                return "blockUserExceptionInJSON";
            }
            return "blockUserException";
        } catch (ClientAddressException e) {
            log.info("ClientAddressException from channelManager for action = {}", actionName, e);
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.ipConstraintExcep"));
                return "clientAddressExceptionInJSON";
            }
            return "clientAddressException";
        } catch (InvalidCustomerException e) {
            log.info("InvalidCustomerException from channelManager for action = {}", actionName, e);
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.runtime.body"));
                return "faultInJSON";
            }
            return "fault";
        } catch (SecondPasswordException e) {
            log.info("SecondPasswordException from channelManager for action = {}", actionName, e);
            log.error("The SecondPasswordException should be catch in the " + actionName +
                    " method in order to let user correct invalid data.");
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.secondPasswordExcep"));
                return "secondPasswordExceptionInJSON";
            }
            return "secondPasswordException";
        } catch (ServiceCallException e) {
            log.info("ServiceCallException from channelManager for action = {}", actionName, e);
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.serviceCallExcep"));
                return "serviceCallExceptionInJSON";
            }
            return "serviceCallException";
        } catch (DepositTypeException e) {
            log.debug("DepositTypeException from channelManager for action = {}", actionName, e);
            log.error("The DepositTypeException should be catch in the " + actionName +
                    " method in order to let user correct invalid data.", e);
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.depositTypeException"));
                return "depositTypeExceptionInJSON";
            }
//            ((BaseAction) actionInvocation.getAction()).setStep(1);
            actionInvocation.addPreResultListener(new ActionPreResultListener(INPUT));
            actionSupport.addActionError(actionSupport.getText("error.depositTypeException",
                    makeErrorMessage((e.getDepositType().size() < 1) ? "" : e.getDepositType().get(0))));
            return actionInvocation.getResultCode();
        } catch (TransferMaxAmountException e) {
            log.info("TransferMaxAmountException from channelManager for action = {}", actionName, e);
            log.error("The TransferMaxAmountException should be catch in the " + actionName +
                    " method in order to let user correct invalid data.");
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.transferMaxAmountExcep"));
                return "transferMaxAmountExceptionInJSON";
            }
            return "transferMaxAmountException";
        } catch (TransferMaxDailyAmountException e) {
            log.info("TransferMaxDailyAmountException from channelManager for action = {}", actionName, e);
            log.error("The TransferMaxDailyAmountException should be catch in the " + actionName +
                    " method in order to let user correct invalid data.");
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.transferMaxDailyExcep"));
                return "transferMaxDailyAmountExceptionInJSON";
            }
            return "transferMaxDailyAmountException";
        } catch (TransferMaxMonthlyAmountException e) {
            log.info("TransferMaxMonthlyAmountException from channelManager for action = {}", actionName, e);
            log.error("The TransferMaxMonthlyAmountException should be catch in the " + actionName +
                    " method in order to let user correct invalid data.");
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.transferMaxMonthlyExcep"));
                return "transferMaxMonthlyAmountExceptionInJSON";
            }
            return "transferMaxMonthlyAmountException";
        } catch (TransferMinAmountException e) {
            log.info("TransferMinAmountException from channelManager for action = {}", actionName, e);
            log.error("The TransferMinAmountException should be catch in the " + actionName +
                    " method in order to let user correct invalid data.");
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.transferMinAmountExcep"));
                return "transferMinAmountExceptionInJSON";
            }
            return "transferMinAmountException";
        } catch (TicketException e) {
            log.info("TicketException from channelManager for action = {}", actionName, e);
            log.error("The TicketException should be catch in the " + actionName +
                    " method in order to let user correct invalid data.");
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("ticketException"));
                return "ticketExceptionInJSON";
            }
            return "ticketException";
        } catch (OTPException e) {
            log.info("OTPException from channelManager for action = {}", actionName, e);
            log.error("The OTPException should be catch in the " + actionName +
                    " method in order to let user correct invalid data.");
            return "otpException";
        } catch (ServiceAccessIsDeniedException e) {
            log.info("ServiceAccessIsDeniedException from channelManager for action = {}", actionName, e);
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.permissionDenied"));
                return "unauthorizedInJSON";
            }
            return "unauthorized";
        } catch (ValidatorException e) {
            log.info("ValidatorException from channelManager for action = {}", actionName, e);
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.invalidDataEntry"));
                return "invalidDataEntryInJSON";
            }
            return "invalidDataEntry";
        } catch (PermissionException e) {
            log.info("PermissionException from channelManager for action = {}", actionName, e);
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.permissionDenied"));
                return "permissionDeniedInJSON";
            }
            return "permissionDenied";
        } catch (ChannelManagerException e) {
            log.info("ChannelManagerException from channelManager for action = {}", actionName, e);
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.runtime.body"));
                return "faultInJSON";
            }
            return "fault";
        } catch (ServiceException e) {
            log.info("ServiceException from channelManager for action = {}", actionName, e);
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.runtime.body"));
                return "faultInJSON";
            }
            return "fault";
        } catch (RuntimeException e) {
            log.info("RuntimeException from channelManager for action = {}", actionName, e);
            if (resultConfig != null && resultConfig.getClassName().equals("jsonResultType")) {
                actionSupport.addActionError(actionSupport.getText("error.runtime.body"));
                return "faultInJSON";
            }
            return "fault";
        } catch (Throwable e) {
            log.error("Unknown exception in action {}", actionName, e);
            return "fault";
        }
    }

    private String[] makeErrorMessage(String param) {
        return new String[]{StringUtils.trimToEmpty(param) + " "};
    }
}
