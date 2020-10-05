package co.nuvu.util;

public class CreateCreditCard {
	
	private Integer id;
	private long number;
	private Integer cardTypesId;
	
	public CreateCreditCard(){
		
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public Integer getCardTypesId() {
		return cardTypesId;
	}

	public void setCardTypesId(Integer cardTypesId) {
		this.cardTypesId = cardTypesId;
	}
	
}
