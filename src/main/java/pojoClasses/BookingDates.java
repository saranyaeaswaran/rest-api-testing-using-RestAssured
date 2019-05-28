package pojoClasses;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingDates {
	
	@JsonProperty
	private String checkin;
	
	@JsonProperty
	private String checkout;
	
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		
		this.checkin = checkin;
	}
	public String getCheckout() {
		return checkout;
	}
	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}
}
