/**
 * @author Parham Fazel
 * @since 12/29/12
 */
Ext.define('ibcorp.controller.cp.ResourceEditor', {
    extend: 'Ext.app.Controller',
    isFormCreated: false,
    refs: [
        {
            ref: 'reForm',
            selector: 'mainResourceEditor'
        }
    ],

    createForm: function () {
        if (this.isFormCreated === false) {
            var field;
            var that = this;
            var dataObj = ibcorp.i18n.ResourceHelper.msgs;
            var loopOverObj = function (rootObj) {
                for (var obj in rootObj) {
                    if (typeof rootObj[obj] === 'object') {
                        loopOverObj(rootObj[obj]);
                    } else {
                        field = that.getReForm().add({fieldLabel: obj})
                        field.setValue(rootObj[obj]);
                    }
                }
            };
            loopOverObj(dataObj);
            this.isFormCreated = true;
        }
    },

    init: function () {
        this.control({
            "mainResourceEditor": {
                show: {
                    fn: this.createForm,
                    scope: this
                },
                render: {
                    fn: this.createForm,
                    scope: this
                }
            }
        });
    }
});