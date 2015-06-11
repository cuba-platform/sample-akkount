package akkount.entitylisteners;

import akkount.entity.Account;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

public class AccountEntityListener
        implements BeforeInsertEntityListener<Account>, BeforeUpdateEntityListener<Account> {

    @Override
    public void onBeforeInsert(Account entity) {
        setCurrencyCode(entity);
    }

    @Override
    public void onBeforeUpdate(Account entity) {
        setCurrencyCode(entity);
    }

    private void setCurrencyCode(Account account) {
        account.setCurrencyCode(account.getCurrency().getCode());
    }
}
