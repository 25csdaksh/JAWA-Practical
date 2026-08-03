public class StatementFormatter {
    public static String buildStatement(Account account) {
        if (account == null) {
            return "No account details to display.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------------------\n");
        sb.append("      MINIBANK OFFICIAL STATEMENT       \n");
        sb.append("----------------------------------------\n");
        sb.append("Account Number : ").append(account.getAccountNumber()).append("\n");
        sb.append("Owner Name     : ").append(account.getOwnerName()).append("\n");
        sb.append("Current Balance: Rs. ").append(account.getBalance()).append("\n");
        sb.append("Account Status : ").append(account.isActive() ? "ACTIVE" : "INACTIVE").append("\n");
        sb.append("----------------------------------------");
        
        return sb.toString();
    }
}
