package lowleveldesign.bank.entities.transaction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum TransactionStatus {
    INITIATE,
    EXECUTE,
    FAIL,
    SUCCESS;

    public static Set<TransactionStatus> TRANSACTION_COMPLETED_STATUS =
            new HashSet<>(Arrays.asList(FAIL, SUCCESS));
}
