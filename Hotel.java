package com.whatever.model.hotel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVReaderBuilder;

import jakarta.validation.constraints.Size;

public class Hotel implements Comparable<Hotel>
{

	@Override
	public String toString()
	{
		return name;
	}

	@Size(min = 3, message = "Enter at leat 3 characters")
	private String name;
	private List<Room> rentedRooms;
	private List<Room> allRoomsOfThisHotel;

	private String location;
	private BigDecimal totalRentDollars;
	private int id;
	private LocalDate grandOpeningDate;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public List<Room> getRooms()
	{
		return rentedRooms;
	}

	public void setRooms(List<Room> rooms, Room room)
	{
		rooms.add(room);
		this.rentedRooms = rooms;
	}

	public LocalDate getGrandOpeningDate()
	{
		return grandOpeningDate;
	}

	public void setGrandOpeningDate(LocalDate grandOpeningDate)
	{
		this.grandOpeningDate = grandOpeningDate;
	}

	public boolean deleteRoom(List<Room> listOfRoomsRentedPassedFromOutsideOfThisFunction, int roomNumber,
			LocalDate startDate)
	{

		if (this.getRooms().stream()
				.anyMatch(x -> roomNumber == x.getRoomNumber() && startDate.equals(x.getStartDate())))
		{

			try
			{
				Room targetRoom = this.getRooms().stream()
						.filter(room -> room.getRoomNumber() == roomNumber && room.getStartDate().equals(startDate))
						.collect(Collectors.toList()).get(0);
				if (this.getRooms().contains(targetRoom))

					listOfRoomsRentedPassedFromOutsideOfThisFunction.remove(targetRoom);
				return true;

			} catch (Exception e)
			{

				System.out.println("something went wrong in the deleteRoom method...");
				e.printStackTrace();
			}
		}
		return false;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public BigDecimal getTotalRentDollars()
	{
		return totalRentDollars;
	}

	public void setTotalRentDollars(BigDecimal totalRentDollars)
	{
		this.totalRentDollars = totalRentDollars;
	}

	public List<Room> getAllRoomsOfThisHotel()
	{
		return allRoomsOfThisHotel;
	}

	public Hotel(String name, String location, int id, LocalDate grandOpeningDate)
	{

		this.name = name;

		this.location = location;

		this.rentedRooms = new ArrayList<Room>();

		this.id = id;

		this.grandOpeningDate = grandOpeningDate;

		try
		{
			this.allRoomsOfThisHotel = new CSVReaderBuilder(
					new FileReader("C:\\Greenlyte\\RepoForPracticeData\\hotelData1.csv")).withSkipLines(1).build()
							.readAll().stream().map(data -> {
								Room roomObj = new Room(Integer.parseInt(data[0]), new BigDecimal(data[1]));

								return roomObj;
							}).collect(Collectors.toList());
		} catch (FileNotFoundException e)
		{
			System.out.println("something went wrong in the setting of all hotel rooms because of file not found");
			e.printStackTrace();
		} catch (IOException e)
		{
			System.out.println("something went wrong in the setting of all hotel rooms");
			e.printStackTrace();
		}

	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		if (name.hashCode() != other.getName().hashCode())
			return false;

		return true;
	}

	public void setTotalRentDollarsFromOutside()
	{

		BigDecimal sum = new BigDecimal("0.00");

		if (this.getRooms() != null)
		{
			for (Room room : this.getRooms())
			{

				sum = sum.add(room.getTotalRentAmount());

			}
		}
		this.setTotalRentDollars(sum.setScale(2, RoundingMode.HALF_UP));

	}

	@Override
	// to make a sorted list have the Hotels sorted by total revenue in descending
	// order.
	public int compareTo(Hotel otherHotel)
	{
		if (otherHotel.getTotalRentDollars().compareTo(this.totalRentDollars) == -1)
			return -1;
		else if (otherHotel.getTotalRentDollars().compareTo(this.totalRentDollars) == 1)
			return 1;
		// last else is returning a random number bc otherwise the Hotel will not get
		// placed into a TreeSet.
		else
			return 2;
	}
}
