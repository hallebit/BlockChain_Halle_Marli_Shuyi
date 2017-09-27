import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class BlockChainDriver {
    
    /**
     * Prints out the list of possible commands.
     */
    public static void helpText(){
        System.out.print("Valid commands:\n" +
        		"\t mine: discovers the nonce for a given transaction\n" +
        		"\t append: appends a new block onto the end of the chain\n" +
        		"\t remove: removes the last block from the end of the chain\n" +
        		"\t check: checks that the block chain is valid\n" +
        		"\t report: reports the balances of Alice and Bob\n" +
        		"\t help: prints this list of commands\n" +
        		"\t quit: quits the program\n");
    }
    
    /**
     * @param args, where args[0] is the initial amount in Alice's bank account
     * @throws NumberFormatException
     * @throws NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NumberFormatException, NoSuchAlgorithmException {
        BlockChain blkchn = new BlockChain(Integer.parseInt(args[0]));
        boolean quit = false;
        String command = "";
        Scanner scan = new Scanner(System.in);
        while(!quit){
            System.out.print(blkchn.toString());
            System.out.print("Command? ");
            command = scan.next();
            if(command.equalsIgnoreCase("mine")){
                System.out.print("Amount transferred? ");
                int amt = Integer.parseInt(scan.next());
                Block mined = blkchn.mine(amt);
                System.out.println("amount = " + amt + ", nonce = " + mined.getNonce());
            } else if(command.equalsIgnoreCase("append")){
                System.out.print("Amount transferred? ");
                int amt = Integer.parseInt(scan.next());
                System.out.print("Nonce? ");
                int nonc = Integer.parseInt(scan.next());
                blkchn.append(new Block(blkchn.chainLen, amt, blkchn.getHash(), nonc));
            } else if(command.equalsIgnoreCase("remove")){
                blkchn.removeLast();
            } else if(command.equalsIgnoreCase("check")){
                if(blkchn.isValidBlockChain()){
                    System.out.println("Chain is valid!");
                } else {
                    System.out.println("Chain is invalid!");
                }
            } else if(command.equalsIgnoreCase("report")){
                blkchn.printBalances();
            } else if(command.equalsIgnoreCase("help")){
                helpText();
            } else if(command.equalsIgnoreCase("quit")){
                quit = true;
            } else {
                System.out.println("Invalid input. Please use help to discover commands.");
            }
        System.out.println();
        }
        scan.close();
    }
}
