/**
 * @author Parham Fazel
 * @since 12/29/12
 */

Ext.define('extdl.view.cp.ResourceEditor', {
    extend: 'Ext.form.Panel',
    alias: 'widget.mainResourceEditor',
    title: 'Resource Editor Form',
    bodyPadding: 5,
    bodyStyle: 'overflow-y: auto !important',
    width: 350,

    // The form will submit an AJAX request to this URL when submitted
    url: extdl.ctx + 'scripts/extdl/app/i18n/'+ 'fa.json',

    // Fields will be arranged vertically, stretched to full width
    layout: 'anchor',
    defaults: {
        anchor: '100%',
        labelWidth: 300
    },

    // The fields
    defaultType: 'textfield',

    // Reset and Submit buttons
    buttons: [{
        text: 'Reset',
        handler: function() {
            this.up('form').getForm().reset();
        }
    }, {
        text: 'Submit',
        formBind: true, //only enabled once the form is valid
        disabled: true,
        handler: function() {
            var form = this.up('form').getForm();
            if (form.isValid()) {
                form.submit({
                    success: function(form, action) {
                        Ext.Msg.alert('Success', action.result.msg);
                    },
                    failure: function(form, action) {
                        Ext.Msg.alert('Failed', action.result.msg);
                    }
                });
            }
        }
    }]
});
