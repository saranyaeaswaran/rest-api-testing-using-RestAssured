package pojoClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingDetails {
	
	@JsonProperty
	private String firstname;
	
	@JsonProperty
	private String lastname;
	
	@JsonProperty
	private int totalprice;
	
	@JsonProperty
	private Boolean depositpaid;
	
	@JsonProperty
	private BookingDates bookingdates;
	
	@JsonProperty
	private String additionalneeds;
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getTotalprice() {
		return totalprice;
	}
	
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	
	public Boolean getDepositpaid() {
		return depositpaid;
	}
	
	public void setDepositpaid(Boolean depositpaid) {
		this.depositpaid = depositpaid;
	}
	
	public BookingDates getBookingdates() {
		return bookingdates;
	}
	
	public void setBookingdates(BookingDates bookingdates) {
		this.bookingdates = bookingdates;
	}
	
	public String getAdditionalneeds() {
		return additionalneeds;
	}
	
	public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}
}
