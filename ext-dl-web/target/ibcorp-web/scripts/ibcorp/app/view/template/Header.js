/**
 * @author Parham Fazel
 * @since 12/30/12
 */
Ext.define('ibcorp.view.template.Header', {
    alias:'widget.masterheader',
    extend:'Ext.container.Container',
    layout:'hbox',
    items:[
        {
            cls: 'branding',
            height: 60,
            html:'Logo',
            width: 200
        },
        {
            html:'Test',
            height: 60,
            flex: 1
        }
    ]
});