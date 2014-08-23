/**
 * @author Parham Fazel
 * @since 12/30/12
 */

Ext.define('ibcorp.util.Format', {
    singleton: true,

    english2PersianNumbers: function (englishDigitString) {
        if (englishDigitString !== '' && englishDigitString !== undefined && englishDigitString !== null) {
            englishDigitString = englishDigitString.toString();
            var integerPart = englishDigitString;
            var decimalPart = '';
            if (englishDigitString && englishDigitString.split('.').length > 1) {
                integerPart = englishDigitString.split('.')[0];
                decimalPart = englishDigitString.split('.')[1];
            }
            var converter = function (tempString) {
                if (tempString !== '' && tempString !== undefined) {
                    tempString = tempString.replace(/0/g, '۰');
                    tempString = tempString.replace(/1/g, '۱');
                    tempString = tempString.replace(/2/g, '۲');
                    tempString = tempString.replace(/3/g, '۳');
                    tempString = tempString.replace(/4/g, '۴');
                    tempString = tempString.replace(/5/g, '۵');
                    tempString = tempString.replace(/6/g, '۶');
                    tempString = tempString.replace(/7/g, '۷');
                    tempString = tempString.replace(/8/g, '۸');
                    tempString = tempString.replace(/9/g, '۹');
                    return tempString;
                }
            };
            if (decimalPart !== '') {
                return converter(integerPart) + '.' + converter(decimalPart);
            } else {
                return converter(integerPart);
            }
        }
    }
});
