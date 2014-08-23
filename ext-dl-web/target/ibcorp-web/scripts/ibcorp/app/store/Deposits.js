/**
 * @author Parham Fazel
 * @since 12/24/12
 */
Ext.define('ibcorp.store.Deposits', {
    extend: 'Ext.data.Store',
    model: 'ibcorp.model.Deposit',
    autoLoad: true,
    groupField: 'group'
});
