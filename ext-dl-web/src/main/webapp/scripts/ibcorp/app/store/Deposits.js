/**
 * @author Parham Fazel
 * @since 12/24/12
 */
Ext.define('extdl.store.Deposits', {
    extend: 'Ext.data.Store',
    model: 'extdl.model.Deposit',
    autoLoad: true,
    groupField: 'group'
});
