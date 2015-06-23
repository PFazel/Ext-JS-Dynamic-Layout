/**
 * @author Parham Fazel
 * @since 12/30/12
 */
Ext.define('extdl.view.template.Navigation', {
    alias: 'widget.masternavigation',
    extend: 'Ext.view.View',
    requires: [
        'Ext.XTemplate'
    ],
    initComponent: function () {
        this.loadingText = extdl.getText('COMMONS.LOADING');
        this.store ='IB.store.template.navigation.MenuItems';
        this.loadMask = false;
        this.html = 'Navigation';
        this.tpl = [
            '<div id="navigation">',
            '<ul class="mainMenu">',
            '<tpl for=".">',
            '<li class="menus" id={id}>',
            '<a href="' + extdl.ctx + '{actionUrl}',
            '<tpl if="actionParameter">',
            '?actionParameter={actionParameter}',
            '</tpl>',
            '">{[this.resourceRenderer(values.resourceKey)]}</a>',
            '<tpl if="children.length &gt; 0">',
            '<ul class="navList" >',
            '<tpl for="children" >',
            '<li>',
            '<a id="{id}" href="{actionUrl}">{resourceKey}</a>',
            '</li>',
            '</tpl>',
            '</ul>',
            '</tpl>',
            '</li>',
            '</tpl>',
            '</ul>',
            '</div>', {
                resourceRenderer: function (val) {
                    return l10n.navigation[val];
                },
                logger: function (val) {
                    console.log(val);
                }
            }
        ];
        this.itemSelector = 'li.menus';
        this.callParent();

    }
});