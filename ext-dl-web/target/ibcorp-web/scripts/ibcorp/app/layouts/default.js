/**
 * @author Parham Fazel
 * @since 12/31/12
 */
Ext.define('ibcorp.view.Viewport', {
    renderTo: Ext.getBody(),
    extend: 'Ext.container.Viewport',
    rtl: Ext.getBody().hasCls('rtl'),
    requires: [
        'Ext.layout.container.Border',
        'ibcorp.view.template.Header',
        'ibcorp.view.template.Footer',
        'ibcorp.view.template.Navigation',
        'ibcorp.view.template.Content'
    ],

    layout: {
        type: 'border'
    }
});
