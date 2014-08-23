/**
 * @author Parham Fazel
 * @since 12/30/12
 */
Ext.define('ibcorp.util.Console', {
    singleton: true,
    checkConsole: function () {
        try {
            console.log('');
        } catch (err) {
            console = {};
            console.log = console.error = console.info = console.debug = console.warn = console.trace = console.dir = console.dirxml = console.group = console.groupEnd = console.time = console.timeEnd = console.assert = console.profile = function () {
            };
        }
    }
});
