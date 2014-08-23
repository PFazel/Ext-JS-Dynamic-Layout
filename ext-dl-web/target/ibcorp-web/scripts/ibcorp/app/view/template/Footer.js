/**
 * @author Parham Fazel
 * @since 12/30/12
 */
Ext.define('ibcorp.view.template.Footer', {
    alias:'widget.masterfooter',
    extend:'Ext.container.Container',
    layout:'hbox',
    items:[
        {
            cls: 'footer',
            height: 30,
            html:'Footer',
            flex: 1
        }
    ]
});