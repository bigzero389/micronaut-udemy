package kr.co.bigzero.udemy.broker.data;

import jakarta.inject.Singleton;
import kr.co.bigzero.udemy.broker.symbol.Symbol;
import kr.co.bigzero.udemy.broker.wallet.DepositFiatMoney;
import kr.co.bigzero.udemy.broker.wallet.Wallet;
import kr.co.bigzero.udemy.broker.wallet.WithdrawFiatMoney;
import kr.co.bigzero.udemy.broker.watchlist.WatchList;

import java.math.BigDecimal;
import java.util.*;

@Singleton
public class InMemoryAccountStore {

  public static final UUID ACCOUNT_ID = UUID.fromString("2f48f241-9d64-4d16-bf56-70b9d4e0e79a");

  private final Map<UUID, WatchList> watchListsPerAccount = new HashMap<>();
  private final Map<UUID, Map<UUID, Wallet>> walletsPerAccount = new HashMap<>();

  public WatchList getWatchList(final UUID accountId) {
    return watchListsPerAccount.getOrDefault(accountId, new WatchList());
  }

  public WatchList updateWatchList(final UUID accountId, final WatchList watchList) {
    watchListsPerAccount.put(accountId, watchList);
    return getWatchList(accountId);
  }

  public void deleteWatchList(final UUID accuntId) {
    watchListsPerAccount.remove(accuntId);
  }

  public Collection<Wallet> getWallets(UUID account) {
    return Optional.ofNullable(walletsPerAccount.get(account))
        .orElse(new HashMap<>())
        .values();
  }

  public Wallet depositToWallet(DepositFiatMoney deposit) {
    return addAvailableInWallet(deposit.accountId(), deposit.walletId(), deposit.symbol(), deposit.amount());
  }

  public Wallet withdrawFromWallet(WithdrawFiatMoney withdraw) {
    return addAvailableInWallet(withdraw.accountId(), withdraw.walletId(), withdraw.symbol(), withdraw.amount());
  }

  private Wallet addAvailableInWallet(UUID accountId, UUID walletId, Symbol symbol, BigDecimal changeAmount) {
    final var wallets = Optional.ofNullable(walletsPerAccount.get(accountId)).orElse(new HashMap<>());

    var oldWallet = Optional.ofNullable(wallets.get(walletId)).orElse(new Wallet(ACCOUNT_ID, walletId, symbol, BigDecimal.ZERO, BigDecimal.ZERO));

    var newWallet = oldWallet.addAvailable(changeAmount);

    // update wallet in store
    wallets.put(newWallet.walletId(), newWallet);
    walletsPerAccount.put(newWallet.accountId(), wallets);
    return newWallet;
  }

}
