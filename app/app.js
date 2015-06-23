/* This file contains all constants and Enums*/

Ext.require([
    'extdl.i18n.ResourceHelper',
    'extdl.util.Types',
    'extdl.util.Console',
    'extdl.util.Format',
    'Ext.grid.feature.Grouping',
    'Ext.layout.container.Card'
]);

/* context of application for correct addresses */
/*TODO: this property should be taken from server*/
extdl = {};
extdl.ctx = '/ExtJS-dynamic-layout/master/';
Ext.application({

    controllers: ["applicationManager.ApplicationManager", "ViewManager", "deposit.DepositList", "cp.ResourceEditor"],

    views: ["Viewport", "cp.ResourceEditor"],


    name: 'extdl',

    autoCreateViewport: false,
    launch: function () {

        extdl.util.Types.createCustomTypes();
        extdl.util.Console.checkConsole();

        var setLocale = function () {
            if (Ext.getBody().getAttribute('locale') === 'fa') {
                extdl.locale = 'fa';
            } else if (Ext.getBody().getAttribute('locale') === 'en') {
                extdl.locale = 'en';
            }
        };

        var setDirection = function () {
            if (Ext.getBody().hasCls('rtl')) {
                extdl.direction = 'rtl';
            } else if (Ext.getBody().hasCls('ltr')) {
                extdl.direction = 'ltr';
            }
        };

        var tempLocaleSetter = function () {
            var tempLocale = Ext.Object.fromQueryString(location.href.split('?')[1]);
            extdl.direction = tempLocale.direction.split('#')[0];
            extdl.locale = tempLocale.locale;
            extdl.layout = tempLocale.layout;
        };

//        setLocale();
//        setDirection();

        tempLocaleSetter();

        var createViewPort = function () {
            Ext.Ajax.request({
                url: extdl.ctx + 'app/layouts/' + extdl.layout + '.json',
                success: function (response) {
                    var responseObj = Ext.decode(response.responseText);
                    Ext.create('extdl.view.Viewport', {
                        items: responseObj.items,
                        rtl: extdl.direction === 'rtl'
                    });
                }
            });

            Ext.get('loading').remove();
            Ext.get('loading-mask').fadeOut({remove: true});
        };

        /* This file contains localization for Extjs classes in farsi */
        Ext.Loader.loadScript(extdl.ctx + 'ext/locale/ext-lang-' + extdl.locale + '.js');

        /* This file contains localization for extdl classes in farsi */
        extdl.i18n.ResourceHelper.getResourceFile(extdl.locale + '.json', createViewPort);
    }
});
