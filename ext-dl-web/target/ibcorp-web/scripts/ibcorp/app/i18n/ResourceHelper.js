/**
 * @author Parham Fazel
 * @since 12/25/12
 */

Ext.define('ibcorp.i18n.ResourceHelper', {
    singleton: true,
    getResourceFile: function (fileName, callBack, scope) {
        Ext.Ajax.request({
            url: ibcorp.ctx + 'scripts/ibcorp/app/i18n/'+ fileName,
            success: function(response){
                ibcorp.i18n.ResourceHelper.msgs = Ext.decode(response.responseText);
                ibcorp.getText = ibcorp.i18n.ResourceHelper.getText;
                callBack(response, scope);
            }
        });
    },
    getText: function (key) {
        var i, result, tempKey;
        var rootObj = ibcorp.i18n.ResourceHelper.msgs;
        var keys = key.split('.');
        for (i = 0; i < keys.length; i++) {
            tempKey = keys[i];
            if (typeof rootObj[tempKey] == 'object') {
                rootObj = rootObj[tempKey];
            }
            result = rootObj[tempKey]
        }
        return result;
    }
});


