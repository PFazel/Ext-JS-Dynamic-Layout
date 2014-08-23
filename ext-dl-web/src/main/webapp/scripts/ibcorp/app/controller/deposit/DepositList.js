/**
 * @author Parham Fazel
 * @since 12/31/12
 */
Ext.define('extdl.controller.deposit.DepositList', {
    extend: 'Ext.app.Controller',
    models: ["Deposit"],
    stores: ["Deposits"],
    views: ["deposits.DepositList"]
});
