import java.util.*;

interface ATM_Machine{
	Scanner sc = new Scanner(System.in);
	public void welcome();
	public void options(User user); 
}


class ATM implements ATM_Machine {
	private int currentUserAccountNo;
	private int currentUserPinNo;
	
	public void welcome() {
		System.out.println("Welcome to the ATM , please enter your Account number(5 digits) to proceed. ");
		
		setCurrentUserAccountNo(sc.next());
	}
	public int getCurrentUserAccountNo() {
		return currentUserAccountNo;
	}
	public void setCurrentUserAccountNo(String currentUserAccountNo) {
		int input = isValidInput(currentUserAccountNo);
		if(input>0)
			this.currentUserAccountNo = input;
		else {
			System.out.println("Please enter valid input i.e. 5 digit integer as your Account number.");
			welcome();
		}
	}
	public int getCurrentUserPinNo() {
		return currentUserPinNo;
	}
	public void setCurrentUserPinNo() {
		System.out.println("now sir, please fill your pin.(5 digits) ");
		
		String currentUserPinNo = sc.next();
		int input = isValidInput(currentUserPinNo);
		if(input>0)
			this.currentUserPinNo = input;
		else {
			System.out.println("Please enter valid input i.e. 5 digit integer as your account pin number.");
			setCurrentUserPinNo();
			}
	}
	public void options(User user) {
		System.out.println("What can we do for you?");
		String options[] =  {"Check balance" , "Withdraw cash", "Deposite cash" , "Exit"};
		
		for(int i=0;i<4;i++) {
			System.out.println("To "+options[i]+" Press "+(i+1));
		}
		int op=sc.nextInt();
		
		if(op==1) {
			checkBalance(user);
		}else if(op==2)withdrawCash(user);
		else if(op==3)depositeCash(user);
		else if(op==4)return;
		else {
			System.out.println("Invalid option, try again.");
		}
		
	}
	
	private void depositeCash(User user) {
		System.out.println("What amount you want to deposit?");
		int amount = sc.nextInt();
		System.out.println("Please put the cash in the gap below .");
		user.addAmountInBank(amount);
		System.out.println("Sucessfully Added balance updated from "+(user.getAmountInBank()-amount)
				+" to "+ (user.getAmountInBank()));
		options(user);
		
	}
	private void withdrawCash(User user) {
		System.out.println("Enter the amount you want to withdraw.");
		int amount = sc.nextInt();
		if(amount>user.getAmountInBank()) {
			checkBalance(user);
			System.out.println("\nSo , Withdraw is not possible, no sufficient balance.");
		}else {
			System.out.println("Take the money...");
			user.removeAmountInBank(amount);
			System.out.println("balance updated from "+(user.getAmountInBank()+amount)
					+" to "+ (user.getAmountInBank()));
			
		}
		options(user);
		
	}
	private void checkBalance(User user) {
		System.out.println("Your Current balance is "+user.getAmountInBank());
		options(user);
	}
	
	
	
	private int isValidInput(String input) {
		if(input.length()==5) {
			int output=0;
			for(int i = 0;i<5;i++) {
				if(input.charAt(i)<='9'&&input.charAt(i)>='0') {
					output=output*10+(input.charAt(i)-'0');
				}else {
					return -1;
				}
			}
			
			return output;
		}else {
			return -1;
		}
	}
	
	
	
	
}

class User{
	private int accNo,pinNo;
	private int amountInBank;
	private String name;

	public int getAccNo() {
		return accNo;
	}

	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}

	public int getPinNo() {
		return pinNo;
	}

	public void setPinNo(int pinNo) {
		this.pinNo = pinNo;
	}

	public int getAmountInBank() {
		return amountInBank;
	}

	public void setAmountInBank(int amountInBank) {
		this.amountInBank = amountInBank;
	}
	
	public void addAmountInBank(int amount) {
		this.amountInBank+=amount;
	}
	
	public void removeAmountInBank(int amount) {
		this.amountInBank-=amount;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}


class BankDataBase {
	
	ArrayList<User> accountList = new ArrayList<>();
	
	BankDataBase(){
		Random rand = new Random();
		String alph = "abcdefghijklmnopqrstuvwxyz";
		for(int i=10000;i<=15000;i++) {
			User user = new User();
			user.setAccNo(i);
			user.setPinNo(100000-i);
			user.setAmountInBank(Math.abs(rand.nextInt())%1000000);
			String name = "";
			for(int j=0;j<Math.abs(rand.nextInt())%25+2;j++) {
				name+=alph.charAt(Math.abs(rand.nextInt())%26);
			}
			name=name.substring(0,1).toUpperCase()+name.substring(1);
			user.setName(name);
			
			// Making a random variable
			accountList.add(user);
			
		}
	}
	
	public boolean checkUserAccountPin(int AccNo, int Pin) {
		if(accountList.get(AccNo-10000).getPinNo()==Pin) {
			return true;
		}else {
			return false;
		}
	}
	public boolean checkUserAccount(int AccNo) {
		
		if(AccNo>=10000&&AccNo<=accountList.size()+10000) {
			
			return true;
		}
		return false;
	}
}


public class CaseStudy2 {

	public static void main(String[] args) {
		
		ATM atm = new ATM ();
		BankDataBase bankDataBase = new BankDataBase();
		
		while(true) {
			atm.welcome();
			if(bankDataBase.checkUserAccount(atm.getCurrentUserAccountNo())) {
				
				atm.setCurrentUserPinNo();
				if(bankDataBase.checkUserAccountPin(atm.getCurrentUserAccountNo(),atm.getCurrentUserPinNo())) {
					int index = atm.getCurrentUserAccountNo()-10000;
					System.out.println("Welcome to the ATM Mr. "+bankDataBase.accountList.get(index).getName());
					atm.options(bankDataBase.accountList.get(index));
					
					
				}else {
					System.out.println("Not a valid pin for the given Account number, Check the pin and try again..");
				}
			}else {
				System.out.println("This user is not avalible.");
			}
			break;
		   
		}
	}

}
