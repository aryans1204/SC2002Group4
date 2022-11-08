package src.entities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import java.time.*;

public class Ticket implements Serializable {
    Movie movie;  //associated movie with the ticket
    Cinema cinema; //associated cinema of the movie, this will also provide seat number
    Customer customer;  //purchasing customer details
    Transaction transaction;
    LocalDateTime showtime;
    Seat seat;
    double price;
    String holidayFileName;
    Scanner sc= new Scanner(System.in);

    public Ticket(Movie a, Cinema b, Customer c, LocalDateTime d, Seat seat, String TID, String custName, String custEmail, String custMobileNumber,String holidayFileName) {
    	movie = a;
    	cinema = b;
    	customer = c;
    	showtime = d;
    	this.seat = seat;
	this.holidayFileName = holidayFileName;
    	transaction = new Transaction(TID, custName, custEmail, custMobileNumber); 	
	    double priceL = b.getTicketPrice(a.getTitle());
	    if (priceL == 0) {
	    	price = 0;
	    	calculatePrice();
	    }
	    else {
	    	price = priceL;
	    	calculatePrice();
	    }
    }
    
    public void calculatePrice() {
    	//gets price from prices database and does algorithm on it.
    	//prices database stores default values for this particular movie and cinema and showtime
    	//if child or senior x0.8
    	double moviePrice;
    	MovieType movieType;
    	int customerAge;
	SeatType seatType;
    	int date;
    	double multiplier = 1;
	string day;
    	
    	
    	if (price==0)
    		moviePrice = 10.0;
    	else
    		moviePrice = price;
    		
    	movieType = movie.getType();
    	customerAge = customer.getAge();
    	seatType = seat.getType();

        //update multiplier for different days of week
	//need to create class to calculate holidays
    	HolidayController a = new HolidayController(holidayFileName);
	day = showtime.getDayOfWeek().toString()
    	if ("SATURDAY".equalsIgnoreCase(day)||"SUNDAY".equalsIgnoreCase(day)||a.isAHoliday(showtime)) {
    		multiplier = multiplier*1.5;
    	}

        //update multiplier for movietype
        switch(movieType){
               case TWO_D:
                    //no change in multiplier;
                    break;
               case THREE_D:
                    multiplier = multiplier *1.2;
                    break;
               case BLOCKBUSTER:
                    multiplier = multiplier * 1.5;
                    break;
               default:
                   //no change
                   break;
         }

         //update multiplier for cinemaType
         switch(seatType){
                case STANDARD:
                    //no change in multiplier
                    break;
                case GOLD:
                    multiplier = multiplier * 1.2;
                    break;
                case PLATINUM:
                    multiplier = multiplier * 1.5;
                    break;
                default:
                    //no change
                    //break;
        }
        
        //update multiplier by age
        if (customerAge<12) multiplier = multiplier * 0.8;
        //no price change if its adult since default price is for adults
        else if (customerAge>=56)multiplier = 0.7*multiplier;
              

       
              
    	//by the end of all the multipliers, the formula will look like this:
    	//multiplier = multiplier * movieType * clientAge * cinemaType * date;
    	
    	price = moviePrice * multiplier;
    	

    }
    
    /*public int[] getPricesFromDB() {
		int[] prices = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		
		try {
			fis = new FileInputStream(priceFileName);
			in = new ObjectInputStream(fis);
			prices = (int[]) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return prices;
	}*/
	
    public Customer getCustomer(){
         return customer;
    }
   
    public double getPrice(){
    	return price;
    }
	
    public Seat getSeat() {
	return seat;
    }
	
    public Transaction getTransaction() {
	return this.transaction;
    }
    
    public Cinema getCinema() {
    	//method to return cinema associated with the ticket.
    	return cinema;
    }
    public Movie getMovie() {
    	//method to return the movie for the ticket
    	return movie;
    }
    public LocalDateTime getShowtime() {
    	//method to return showtime of the ticket.
    	return showtime;
    }
    
    public void setPrice(double newPrice){
    	price = newPrice;
    }

    @Override
    public String toString() {
	return "Date of Movie: " + showtime.toString() + "\n" + 
		"Movie Title: " + movie.getTitle() + "\n" +
		"Cinema Name: " + cinema.getName() + "\n" +
		"Price paid: " + price + "\n" +
		"Transaction ID: " + transaction.getTID();
    }   
}
