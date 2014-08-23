/**
 * @author Parham Fazel
 * @since 12/31/12
 */

Ext.define('extdl.util.Types', {
    singleton: true,
    createCustomTypes: function () {
        // data type which formats data for iranian rial.
        Ext.data.Types.MONEY = {
            convert: function (v, data) {
                Ext.util.Format.thousandSeparator = extdl.getText('COMMONS.RIAL_THOUSAND_SEPARATOR');
                return Ext.util.Format.currency(v, ' ' + extdl.getText('COMMONS.RIAL_SIGN'), extdl.getText('COMMONS.RIAL_DECIMAL_CURRENCY_PRECISION'), true)
            },
            sortType: function (v) {
                return v;
            },
            type: 'money'
        };
    }
});