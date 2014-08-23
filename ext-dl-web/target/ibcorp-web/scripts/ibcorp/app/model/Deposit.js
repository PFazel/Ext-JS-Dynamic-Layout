/**
 * @author Parham Fazel
 * @since 12/24/12
 */
Ext.define('ibcorp.model.Deposit', {
    extend: 'Ext.data.Model',
    
    fields: [
        { name: 'deposit', type: 'auto' },
        { name: 'depositNumber', type: 'auto', mapping: 'deposit.depositNumber' },
        { name: 'balance', type: 'money', mapping: 'deposit.balance.price' },
        { name: 'availableBalance', type: 'money', mapping: 'deposit.balance.availablePrice' },
        { name: 'blockedAmount', type: 'money', mapping: 'deposit.blockedAmount' },
        { name: 'currency', type: 'auto' },
        { name: 'personality', type: 'auto' },
        { name: 'owner', type: 'auto' },
        { name: 'withdrawalOption', type: 'auto' },
        { name: 'signature', type: 'auto' },
        { name: 'depositTitle', type: 'auto' },
        { name: 'branch', type: 'auto' },
        { name: 'cityCode', type: 'auto' },
        { name: 'cityName', type: 'auto' },
        { name: 'provinceCode', type: 'auto' },
        { name: 'provinceName', type: 'auto' },
        { name: 'group', type: 'auto' },
        { name: 'support', type: 'auto' },
        { name: 'supportStatus', type: 'auto' },
        { name: 'supportGroupType', type: 'auto' },
        { name: 'supportVisibility', type: 'auto' },
        { name: 'interestDeposit', type: 'auto' },
        { name: 'interestVisibility', type: 'auto' },
        { name: 'creditDeposit', type: 'auto' },
        { name: 'creditLoanRemainAmount', type: 'auto' },
        { name: 'creditProfitRemainAmount', type: 'auto' },
        { name: 'creditDepositRemainAmount', type: 'auto' },
        { name: 'creditRemainAmount', type: 'auto' },
        { name: 'creditRateAmount', type: 'auto' },
        { name: 'interestPayDay', type: 'auto' },
        { name: 'paymentPeriod', type: 'auto' },
        { name: 'lastPayment', type: 'auto' },
        { name: 'paymentWithdrawalDepositNumber', type: 'auto' },
        { name: 'paymentPayDay', type: 'auto' },
        { name: 'depositType', type: 'auto' }
    ],

    proxy: {
        type: 'ajax',
        url: ibcorp.ctx + 'scripts/ibcorp/app/mock/deposit.json',
        reader: {
            type: 'json',
            root: 'depositList'
        }
    }
});

