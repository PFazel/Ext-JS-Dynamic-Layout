/**
 * User: paYam
 * Date: 11/14/12
 * Time: 4:23 PM
 */

Ext.define('ibcorp.controller.applicationManager.ApplicationManager', {
    extend  : 'Ext.app.Controller',

    urls    : {
        logout  : ibcorp.ctx + '/login/loginPage.action'
    },

    init    : function () {
        Ext.Ajax.on({
            scope               : this,
            requestexception    : this.redirectRequest
        });
    },

    redirectRequest : function (connection, response, options) {
        var text = Ext.JSON.decode(response.responseText);
        if (response.status === 500 && text.errorMessages == 'errorLogout') {
            this.redirect(this.urls['logout']);
        }
    },

    redirect        : function (url) {
        window.location = url;
    }

});