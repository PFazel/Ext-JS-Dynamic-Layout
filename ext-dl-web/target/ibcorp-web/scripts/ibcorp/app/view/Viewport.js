Ext.define('ibcorp.view.Viewport', {
    renderTo: Ext.getBody(),
    extend: 'Ext.container.Viewport',
    requires: [
        'Ext.layout.container.Border',
        'ibcorp.view.template.Header',
        'ibcorp.view.template.Footer',
        'ibcorp.view.template.Navigation',
        'ibcorp.view.template.Content'
    ],

    layout: {
        type: 'border'
    },

    items: []
});