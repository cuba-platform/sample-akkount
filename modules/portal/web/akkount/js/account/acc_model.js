/**
 * Created by krivopustov on 27.04.2014.
 */
(function() {
    "use strict";

    app.AccountModel = Backbone.Model.extend({}, {
        entityName: "akk$Account"
    });

    app.AccountsCollection = Backbone.Collection.extend({
        model: app.AccountModel,

        jpqlQuery: "select a from akk$Account a order by a.name",
        maxResults: 100,
        view: "account-with-currency",
        sortOrder: "name",

        byId: function(id) {
            return this.find(function(acc) {
                return acc.id == id;
            });
        },

        currencyCodeByAccId: function(id) {
            return this.byId(id).get("currency").code;
        }
    });
}());