Ext.define('extdl.view.Viewport', {
    renderTo: Ext.getBody(),
    extend: 'Ext.container.Viewport',
    requires: [
        'Ext.layout.container.Border',
        'extdl.view.template.Header',
        'extdl.view.template.Footer',
        'extdl.view.template.Navigation',
        'extdl.view.template.Content'
    ],

    layout: {
        type: 'border'
    },

    items: []
});