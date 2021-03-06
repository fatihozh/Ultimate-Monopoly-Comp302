package domain;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
@XmlAccessorType(XmlAccessType.FIELD)
public class Company {
	@XmlID
	private String name;
	private int parValue;
	private int[] dividendValue;
	private int numOfSoldShares;
	@XmlIDREF
	private ArrayList<Share> shareList;
	
	@SuppressWarnings("unused")
	private Company(){
		//For JAXB
	}
	
	public Company(String name,int parValue, int[] dividendValue){
		this.name = name;
		this.parValue = parValue;
		this.dividendValue = dividendValue;	
		shareList = new ArrayList<Share>();
		fillShareList();
		numOfSoldShares = 0;
	}

	private void fillShareList(){
		for (int i = 0; i < 5; i++) {
			shareList.add(new Share(this));	
		}
	}
		public String getName(){
		return name;
	}

	public int getPlayerShareAmount(Player player){ //Player'�n bu companyde ka� tane share'i oldu�unu d�nd�r�yor.
		int count = 0;
		for (int i = 0; i < numOfSoldShares; i++) {
			Player owner = shareList.get(i).getOwner();
			if(owner == player && !shareList.get(i).isMortgaged()) count++;
		}
		return count;
	}
	public int getPlayerMoneyReceiveAmount(Player player){ //Player'�n ka� para alaca��n� d�nd�r�yor. E�er -1 d�nerse i�lem yapmay�p player�n bu companyde share'i yok dersin.
		int count = getPlayerShareAmount(player);
		if(count>0) return dividendValue[count-1];
		return -1; 		
	}
	
	public int getPriceOfShare(){
		return parValue;
	}
	public int getRemainingShareAmount(){
		return 5 - numOfSoldShares;
	}
	public boolean hasBuyableShare() {
		return getRemainingShareAmount() > 0;
	}
	
	public boolean buyShare(Player player) { //player will use this method in its own buy method and make transactions.
		if(hasBuyableShare()){
			shareList.get(numOfSoldShares).setOwner(player);
			numOfSoldShares++;
			return true;
		}
		return false;
	}
	
	public boolean sellShare(Player player, Player targetPlayer){ //player will use this method in its own buy method and make transactions.
		for (int i = 0; i < numOfSoldShares; i++) {
			Share share = shareList.get(i);
			Player owner = share.getOwner();
			if(owner == player){
				share.setOwner(targetPlayer);
				numOfSoldShares--;
				return true;
			}
		}
		return false;
	}
	
	public boolean mortgageShare(Player player){ //player will use this method in its own buy method and make transactions.
		for (int i = 0; i < numOfSoldShares; i++) {
			Share share = shareList.get(i);
			Player owner = share.getOwner();
			if(owner == player && !share.isMortgaged()){
				share.setMortgaged(true);
				return true;
			}
		}
		return false;
	}
	

	/**
	 * @return the dividendValue
	 */
	public int[] getDividendValue() {
		return dividendValue;
	}

	/**
	 * @return the loanValue
	 */
	public int getLoanValue() {
		return parValue/2;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + ", Par Value=" + parValue + ", Loan Value=" + parValue/2 + ", Number of Sold Shares="
				+ numOfSoldShares + ", Has Buyable Share ?:" + hasBuyableShare();
	}
	// EFFECTS: Returns true if the rep invariant holds 	
	// for this; otherwise returns false. 
	/**
	 * @effects Returns true if the rep invariant holds for this; otherwise returns false. 
	 * @return boolen true or false
	 */
	public boolean repOk(){
		if(parValue<0)
			return false;
		if(numOfSoldShares>5)
			return false;

		return true;
	}

}
