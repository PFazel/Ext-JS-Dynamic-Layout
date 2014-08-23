Ext.define('extdl.controller.ViewManager', {
    extend: 'Ext.app.Controller',
    requires: ['Ext.History'],
    views: ['extdl.view.UnsupportedBrowsers'],

    refs: [
        {
            selector: 'mastercontent',
            ref: 'content'
        }
    ],

    addViews: function () {
        var views = extdl.view;
        var view;
        var that = this;
        var findViews = function (rootViews) {
            for (view in rootViews) {
                if (typeof rootViews[view] === 'function' && rootViews[view].xtype !== undefined && rootViews[view].xtype.match('main') !== null) {
                    that.getContent().add(rootViews[view]);
                } else if (view !== 'Viewport' && typeof rootViews[view] !== 'function') {
                    findViews(rootViews[view])
                }
            }
        };
        findViews(views);
    },

    /* Takes a view id and sets active view of mainView panel to this view, also removes extra views components and their buttons,
     * before try changing the views it calls abortRequest method.
     * @param params id of the view that should be shown.
     */
    setView: function (requestedView) {
        var view = this.viewToXtype(requestedView);
        Ext.getBody().removeCls(extdl.viewManager.recentView);
        extdl.viewManager.recentView = requestedView;
        Ext.getBody().addCls(requestedView);
        this.getContent().getLayout().setActiveItem(Ext.ComponentQuery.query(view)[0]);
    },

    /* Takes an array of views which are not showing at the moment and removes all of them from mainView panel.
     * @param container array of inactive views.
     */
    removeInactiveViews: function () {
        var container = this.getContent();
        var containerItems = container.items.items;
        var activeViewId = container.getLayout().activeItem.getId();
        for (var i = containerItems.length - 1; i > -1; i--) {
            if (activeViewId !== containerItems[i].getId()) {
                container.remove(containerItems[i], false);
            }
        }
    },

    getActiveView: function () {
        var container = this.getContent();
        return container.getLayout().getActiveItem();
    },

    viewToXtype: function (view) {
        var viewToXType = {
            'deposits': 'mainDepositList',
            'resourceEditor': 'mainResourceEditor',
            'unsupportedBrowsers': 'unsupportedBrowsers'
        };
        return  viewToXType[view];
    },

    init: function () {

        Ext.History.init();

        extdl.viewManager = this;

        extdl.viewManager.recentView = '';

        this.control({
            'mastercontent': {

                afterrender: function () {
                    this.addViews();
                    if (Ext.isIE7) {
                        this.setView('unsupportedBrowsers');
                    } else {
                        var locationStr = location.href;
                        var startIndex = locationStr.indexOf('#');
                        var self = this;
                        if (startIndex !== -1) {
                            var requestedView = locationStr.slice(startIndex + 1, locationStr.length);
                            this.setView(requestedView);
                        }
                        Ext.History.on('change', function (token) {
                            self.setView(token);
                        });
                    }
                }
            }
        });

    }
});
