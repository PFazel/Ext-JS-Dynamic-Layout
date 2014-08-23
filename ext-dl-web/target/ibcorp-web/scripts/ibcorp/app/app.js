/* This file contains all constants and Enums*/

Ext.require([
    'ibcorp.i18n.ResourceHelper',
    'ibcorp.util.Types',
    'ibcorp.util.Console',
    'ibcorp.util.Format',
    'Ext.grid.feature.Grouping',
    'Ext.layout.container.Card'
]);

/* context of application for correct addresses */
/*TODO: this property should be taken from server*/
ibcorp = {};
ibcorp.ctx = '/';
Ext.application({

    controllers: ["applicationManager.ApplicationManager", "ViewManager", "deposit.DepositList", "cp.ResourceEditor"],

    views: ["Viewport", "cp.ResourceEditor"],


    name: 'ibcorp',

    autoCreateViewport: false,
    launch: function () {

        ibcorp.util.Types.createCustomTypes();
        ibcorp.util.Console.checkConsole();

        var setLocale = function () {
            if (Ext.getBody().getAttribute('locale') === 'fa') {
                ibcorp.locale = 'fa';
            } else if (Ext.getBody().getAttribute('locale') === 'en') {
                ibcorp.locale = 'en';
            }
        };

        var setDirection = function () {
            if (Ext.getBody().hasCls('rtl')) {
                ibcorp.direction = 'rtl';
            } else if (Ext.getBody().hasCls('ltr')) {
                ibcorp.direction = 'ltr';
            }
        };

        var tempLocaleSetter = function () {
            var tempLocale = Ext.Object.fromQueryString(location.href.split('?')[1]);
            ibcorp.direction = tempLocale.direction.split('#')[0];
            ibcorp.locale = tempLocale.locale;
            ibcorp.layout = tempLocale.layout;
        };

//        setLocale();
//        setDirection();

        tempLocaleSetter();

        var createViewPort = function () {
            Ext.Ajax.request({
                url: ibcorp.ctx + 'scripts/ibcorp/app/layouts/' + ibcorp.layout + '.json',
                success: function (response) {
                    var responseObj = Ext.decode(response.responseText);
                    Ext.create('ibcorp.view.Viewport', {
                        items: responseObj.items,
                        rtl: ibcorp.direction === 'rtl'
                    });
                }
            });

            Ext.get('loading').remove();
            Ext.get('loading-mask').fadeOut({remove: true});
        };

        /* This file contains localization for Extjs classes in farsi */
        Ext.Loader.loadScript(ibcorp.ctx + 'scripts/ibcorp/ext/locale/ext-lang-' + ibcorp.locale + '.js');

        /* This file contains localization for ibcorp classes in farsi */
        ibcorp.i18n.ResourceHelper.getResourceFile(ibcorp.locale + '.json', createViewPort);
    }
});
