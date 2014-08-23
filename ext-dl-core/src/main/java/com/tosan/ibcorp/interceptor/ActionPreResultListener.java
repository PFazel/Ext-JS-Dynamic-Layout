package com.extdl.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.PreResultListener;

/**
 * User: mrezaei
 * Date: 12/18/12
 */
public class ActionPreResultListener implements PreResultListener {
    private String result;

    public ActionPreResultListener(String result) {
        this.result = result;
    }

    @Override
    public void beforeResult(ActionInvocation invocation, String resultCode) {
        invocation.setResultCode(this.result);
    }
}
