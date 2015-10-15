import java.util.List;


public class CommonCards extends Square {

	private String name;
	private int chanceCardNo=0;
	private int commCardNo=0;
	private String action;
	
	public CommonCards() {
		super();
		this.action=null;
	}

	public CommonCards(String name, String action) {
		super();
		this.name = name;
		this.action=action;
	}
	
	public String getName() {
		return name;
	}

	public String getAction() {
		return action;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void pickChanceCard(Player current, Player[] players){
			if(chanceCardNo==0){
				advanceToCharles(current);
				chanceCardNo=(chanceCardNo++)%4;
			} else if(chanceCardNo==1){
				advanceToSqueeze(current);
				chanceCardNo=(chanceCardNo++)%4;
			} else if(chanceCardNo==2){
				chairperson(current, players);
				chanceCardNo=(chanceCardNo++)%4;
			} else if (chanceCardNo==3){
				advanceToGo(current);
				chanceCardNo=(chanceCardNo++)%4;
			}
		}
	public void pickCommunityCard(Player current){
		if(commCardNo==0){
			consultancyFee(current);
			commCardNo=(commCardNo++)%3;
		} else if(commCardNo==1){
			bargainBusiness(current);
			commCardNo=(commCardNo++)%3;
		} else if(commCardNo==2){
			renovationSuccess();
			commCardNo=(commCardNo++)%3;
		}
	}
	

	private void renovationSuccess() {
		// TODO Auto-generated method stub
		
	}

	private void bargainBusiness(Player p) {
		// TODO Auto-generated method stub
		
	}

	private void consultancyFee(Player p) {
		p.deposit(25);
	}

	private void advanceToGo(Player p) {
		p.moveTo("Go");  //Oyuncu go kutusuna gidecek, bundan sonra Go'nun action u çağrılacak
	}

	private void chairperson(Player p, Player[] others) {
		for (int i=0; i<others.length; i++){
			p.withdraw(50);
			others[i].deposit(50);
		}
	}

	private void advanceToSqueeze(Player p) {
		if(p.passGo(p.getLocation(), "Squeeze")){  //passGo metodu iki square alacak, arada go yu geçip geçmediğin kontrol edip boolean döndürecek
			p.deposit(200);
		}
		p.moveTo("Squeeze");
		
	}

	private void advanceToCharles(Player p) {
		p.moveTo(Charles);   //Charles a gidecek ancak boardda tanımlı şeylere nasıl ulaşacak?
	}

	@Override
	public void squareAction(Player p, Player[] players){
		// TODO Auto-generated method stub
		if(this.name.equals("Chance")){
			pickChanceCard(p, players);
		} else{
			pickCommunityCard(p);
		}
	}
	
}