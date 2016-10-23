package akkount.service;

import akkount.entity.Account;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.TimeSource;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service(PortalService.NAME)
public class PortalServiceBean implements PortalService {

    @Inject
    private UserDataService userDataService;

    @Inject
    private DataManager dataManager;

    @Inject
    private BalanceService balanceService;

    @Inject
    private TimeSource timeSource;

    @Override
    public Account getLastAccount(String opType) {
        return userDataService.loadEntity(opType, Account.class);
    }

    @Override
    public Balance getBalance() {
        Balance result = new Balance();
        LoadContext<Account> loadContext = LoadContext.create(Account.class);
        loadContext.setQueryString("select a from akk$Account a where a.active = true order by a.name");
        List<Account> accounts = dataManager.loadList(loadContext);
        if (accounts.size() > 0) {
            DecimalFormat format = new DecimalFormat("#,###");

            Map<Account, BigDecimal> balances = new LinkedHashMap<>();
            for (Account account : accounts) {
                BigDecimal balance = balanceService.getBalance(account.getId(), timeSource.currentTimestamp());
                if (BigDecimal.ZERO.compareTo(balance) != 0)
                    balances.put(account, balance);
            }

            Map<String, BigDecimal> totals = new TreeMap<>();
            for (Map.Entry<Account, BigDecimal> entry : balances.entrySet()) {
                if (BooleanUtils.isTrue(entry.getKey().getIncludeInTotal())) {
                    BigDecimal total = totals.get(entry.getKey().getCurrencyCode());
                    if (total == null)
                        total = entry.getValue();
                    else
                        total = total.add(entry.getValue());
                    totals.put(entry.getKey().getCurrencyCode(), total);
                }
            }

            result.totals = new ArrayList<>();
            for (Map.Entry<String, BigDecimal> entry : totals.entrySet()) {
                Balance.AccountBalance totalBal = new Balance.AccountBalance();
                totalBal.currency = entry.getKey();
                totalBal.amount = format.format(entry.getValue());
                result.totals.add(totalBal);
            }

            result.excludedAccounts = new ArrayList<>();
            result.includedAccounts = new ArrayList<>();
            for (Map.Entry<Account, BigDecimal> entry : balances.entrySet()) {
                Balance.AccountBalance accBal = new Balance.AccountBalance();
                accBal.name = entry.getKey().getName();
                accBal.currency = entry.getKey().getCurrencyCode();
                accBal.amount = format.format(entry.getValue());
                if (BooleanUtils.isTrue(entry.getKey().getIncludeInTotal())) {
                    result.includedAccounts.add(accBal);
                } else {
                    result.excludedAccounts.add(accBal);
                }
            }
        }

        return result;
    }
}