package com.greenlyte712.model.hotel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Room {

	private int roomNumber;
	private BigDecimal nightlyRentAmount;
	private LocalDate startDate;
	private LocalDate endDate;
	private long daysOfRent;
	private BigDecimal totalRentAmount;
	private boolean isRented;
  
	@Override
	public String toString()
	{
		return Integer.toString(roomNumber);
	}

	public int reservationHashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Integer.toString(roomNumber).hashCode() + startDate.hashCode() ;
		
		return result;
	}
	
	
	public BigDecimal getTotalRentAmount() {
		return totalRentAmount;
	}


	public void setTotalRentAmount(BigDecimal totalRentAmount) {
		this.totalRentAmount = totalRentAmount;
	}


	public long getDaysOfRent() {
		return daysOfRent;
	}


	public void setDaysOfRent(long daysOfRent) {
		this.daysOfRent = daysOfRent;
	}


	public int getRoomNumber() {
		return roomNumber;
	}


	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}


	public BigDecimal getNightlyRentAmount() {
		return nightlyRentAmount;
	}


	public void setNightlyRentAmount(BigDecimal rentAmount) {
		this.nightlyRentAmount = rentAmount;
	}


	public LocalDate getStartDate() {
		return startDate;
	}


	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	public LocalDate getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


	public Room(int roomNumber, BigDecimal rentAmount, LocalDate startDate, LocalDate endDate) {
		
		this.roomNumber = roomNumber;
		this.nightlyRentAmount = rentAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.daysOfRent = ChronoUnit.DAYS.between(startDate, endDate);
		this.totalRentAmount = BigDecimal.valueOf(this.daysOfRent).multiply(getNightlyRentAmount()).setScale(2, RoundingMode.HALF_UP); 
	}
public Room () {
	this.roomNumber = 0;
	
}

public Room(int roomNumber, BigDecimal nightlyRentAmount) {
	this.roomNumber = roomNumber;
	this.nightlyRentAmount = nightlyRentAmount;
	
	
}


public boolean isRented()
{
	return isRented;
}


public void setRented(boolean isRented)
{
	this.isRented = isRented;
}

}
