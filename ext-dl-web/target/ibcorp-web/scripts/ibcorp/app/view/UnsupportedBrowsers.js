/**
 * User: paYam
 * Date: 10/17/12
 * Time: 4:50 PM
 */

Ext.define('ibcorp.view.UnsupportedBrowsers', {
    alias: 'widget.unsupportedBrowsers',
    extend: 'Ext.panel.Panel',
    baseCls: 'container',
    initComponent: function () {
        this.html = '<div class="unsupportedBrowsers">' +
            '<div class="icon"></div>' +
            '<div class="message">' +
            ibcorp.getText('WARNINGS.UNSUPPORTED_BROWSER_MESSAGE') +
            '</div>' +
            '</div>';

        this.callParent();
    }

});
