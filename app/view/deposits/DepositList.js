Ext.define("extdl.view.deposits.DepositList", {
    extend: 'Ext.grid.Panel',
    alias: 'widget.mainDepositList',
    store: 'Deposits',
    features: [
        Ext.create('Ext.grid.feature.Grouping', {
            groupHeaderTpl: ['{[this.formatType(values.name)]}',
                {
                    formatType: function (name) {
                        return extdl.getText('DEPOSIT_GROUP_TYPE.'+name);
                    }
                }
            ]
        })
    ],
    forceFit: true,


    /* Initializing deposits grid */
    initComponent: function () {
        this.columns = [
            { dataIndex: 'depositNumber', text: extdl.getText('DEPOSIT_LIST.DEPOSIT_NUMBER')},
            { dataIndex: 'balance', text: extdl.getText('DEPOSIT_LIST.BALANCE')},
            { dataIndex: 'availableBalance', text: extdl.getText('DEPOSIT_LIST.AVAILABLE_BALANCE')},
            { dataIndex: 'blockedAmount', text: extdl.getText('DEPOSIT_LIST.BLOCKED_AMOUNT')}

            /*          { dataIndex: 'currency', text: this.currencyText },
             { dataIndex: 'personality', text: this.personalityText },
             { dataIndex: 'owner', text: this.ownerText },
             { dataIndex: 'withdrawalOption', text: this.withdrawalOptionText },
             { dataIndex: 'signature', text: this.signatureText },
             { dataIndex: 'depositTitle', text: this.depositTitleText },
             { dataIndex: 'branch', text: this.branchText },
             { dataIndex: 'cityCode', text: 'cityCode' },
             { dataIndex: 'cityName', text: this.cityNameText },
             { dataIndex: 'provinceCode', text: 'provinceCode' },
             { dataIndex: 'provinceName', text: this.provinceNameText },
             { dataIndex: 'group', text: this.groupText },
             { dataIndex: 'support', text: 'support' },
             { dataIndex: 'supportStatus', text: this.supportStatusText },
             { dataIndex: 'supportGroupType', text: this.supportGroupTypeText },
             { dataIndex: 'supportVisibility', text: 'supportVisibility' },
             { dataIndex: 'interestDeposit', text: 'interestDeposit' },
             { dataIndex: 'interestVisibility', text: 'interestVisibility' },
             { dataIndex: 'creditDeposit', text: 'creditDeposit' },
             { dataIndex: 'creditLoanRemainAmount', text: 'creditLoanRemainAmount' },
             { dataIndex: 'creditProfitRemainAmount', text: 'creditProfitRemainAmount' },
             { dataIndex: 'creditDepositRemainAmount', text: 'creditDepositRemainAmount' },
             { dataIndex: 'creditRemainAmount', text: 'creditRemainAmount' },
             { dataIndex: 'creditRateAmount', text: 'creditRateAmount' },
             { dataIndex: 'interestPayDay', text: 'interestPayDay' },
             { dataIndex: 'paymentPeriod', text: 'paymentPeriod' },
             { dataIndex: 'lastPayment', text: 'lastPayment' },
             { dataIndex: 'paymentWithdrawalDepositNumber', text: 'paymentWithdrawalDepositNumber' },
             { dataIndex: 'paymentPayDay', text: 'paymentPayDay' },
             { dataIndex: 'depositType', text: 'depositType' }*/
        ];
        this.callParent();
    }
});