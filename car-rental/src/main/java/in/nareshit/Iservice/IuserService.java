package in.nareshit.Iservice;

import java.util.List;
import java.util.Optional;

import in.nareshit.Entity.booking;
import in.nareshit.Entity.complain;
import in.nareshit.Entity.user;

public interface IuserService {
	Integer saveUser(user u);
	List<user> getAllUser();
	user getOneUser(String email);
	void deleteUser(Integer Id);
	void updateUser(user u);
	user getUserById(Integer Id);
	void saveComplain(complain c);
	void saveBooking(booking b);
	List<booking> allBooking();
	List<booking> allBookingUser(String s);
	booking bookingById(Integer Id);
	Optional<user> findByEmail(String email);
}
