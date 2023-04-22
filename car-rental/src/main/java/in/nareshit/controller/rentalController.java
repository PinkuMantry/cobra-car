package in.nareshit.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.Entity.booking;
import in.nareshit.Entity.complain;
import in.nareshit.Entity.user;
import in.nareshit.Iservice.IuserService;

@Controller
@RequestMapping("/rental")
public class rentalController {
@Autowired
public IuserService service;
//user view
@GetMapping("/saveUser")
public String saveUser(
		@ModelAttribute user u,
		Model m
		) {
	Optional<user> usr=service.findByEmail(u.getEmail());
	if(usr.isPresent()) {
	m.addAttribute("message",""+u.getEmail()+" is already exists as "+u.getRole());	
	return "new_registration";
	}
	else {
	Integer id=service.saveUser(u);
	m.addAttribute("message", ""+u.getRole()+" "+u.getEmail()+" saved");
	System.out.println("saveUser");
	return "home";
	}
}
@GetMapping("/newRegister")
public String newCust()
{
	System.out.println("newRegister");
	return "new_registration";
}
@GetMapping("/Admin_home")
public String Admin_home()
{
	System.out.println("Admin_home");
	return "Admin_home";
}
@GetMapping("/customer_home")
public String customer_home()
{
	System.out.println("customer_home");
	return "customer_home";
}
@GetMapping("/driver_home")
public String driver_home()
{
	System.out.println("driver_home");
	return "driver_home";
}

@GetMapping("/allCustomer")
public String getAllCustomer(
		@RequestParam(required=false) String message,
		Model model
		) {
List<user> usr=service.getAllUser();
List<user> u=new ArrayList<user>();
for(user u1:usr) {
	if(u1.getRole().equals("customer"))
		u.add(u1);
	}
model.addAttribute("u",u);
model.addAttribute("message",message);
System.out.println("allCustomer");
return "AllCustomer";
}
@GetMapping("/deleteCustomer")
public String deleteCust(
		@RequestParam Integer id,
		RedirectAttributes attributes) {
user u=service.getUserById(id);
service.deleteUser(id);
attributes.addAttribute("message","customer "+u.getEmail()+" deleted");
System.out.println("deleteCustomer");
return "redirect:allCustomer";
}

@GetMapping("/allDriver")
public String getAllDriver(
		@RequestParam(required=false) String message,
		Model model
		) {
List<user> usr=service.getAllUser();
List<user> u=new ArrayList<user>();
for(user u1:usr) {
	if(u1.getRole().equals("driver"))
		u.add(u1);
	}
model.addAttribute("u",u);
model.addAttribute("message",message);
System.out.println("allUser");
return "AllDriver";
}
@GetMapping("/deleteDriver")
public String deleteDriv(
		@RequestParam Integer id,
		RedirectAttributes attributes) {
user u=service.getUserById(id);
service.deleteUser(id);
attributes.addAttribute("message","driver "+u.getEmail()+" deleted");
System.out.println("deleteDriver");
return "redirect:allDriver";
}
@GetMapping("/AllBooking")
public String allBooking(Model m)
{
	List<booking> x=service.allBooking();
	List<booking> b=new ArrayList<booking>();
	for(booking b1:x) {
		if(b1.getStatus().equals("accepted")) {
		b.add(b1);	
		}
	}
	m.addAttribute("lb", b);
	return "allBooking";
}
@GetMapping("/allPending")
public String allPending(Model m)
{
	List<booking> x=service.allBooking();
	List<booking> b=new ArrayList<booking>();
	for(booking b1:x) {
		if(b1.getStatus().equals("pending")) {
		b.add(b1);	
		}
	}
	m.addAttribute("lb", b);
	return "allPending";
}
@GetMapping("/allCompleted")
public String allCompleted(Model m)
{
	List<booking> x=service.allBooking();
	List<booking> b=new ArrayList<booking>();
	for(booking b1:x) {
		if(b1.getStatus().equals("completed")) {
		b.add(b1);	
		}
	}
	m.addAttribute("lb", b);
	return "allCompleted";
}


@GetMapping("/admin")
public String admin()
{
	System.out.println("Admin_home");
	return "Admin_home";
}
@GetMapping("/about")
public String about()
{
	System.out.println("about");
	return "about";
}
@GetMapping("/Product&Services")
public String ProductServices()
{
	System.out.println("Product&Services");
	return "Product&Services";
}
@GetMapping("/contactUs")
public String contactUs()
{
	System.out.println("contactUs");
	return "contactUs";
}
@GetMapping("/FAQ")
public String FAQ()
{
	System.out.println("FAQ");
	return "FAQ";
}
@GetMapping("/home")
public String home(
		@RequestParam(required=false) String message,
		Model m
		){
	m.addAttribute("message",message);
	System.out.println("home");
	return "home";
}
@GetMapping("/Profile")
public String getUserDetails(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    user u=service.getOneUser(userDetails.getUsername());
    model.addAttribute("user", u);
    System.out.println(u);
    
    return "Profile";
}
@GetMapping("/Dprofile")
public String getDriverDetails(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    user u=service.getOneUser(userDetails.getUsername());
    model.addAttribute("user", u);
    System.out.println(u);
    
    return "driver_profile";
}
@GetMapping("/deleteOneUser")
public String deleteOneCustomer(
		@RequestParam Integer id,
		RedirectAttributes attributes) {
String role=service.getUserById(id).getRole();
String email=service.getUserById(id).getEmail();
service.deleteUser(id);
attributes.addAttribute("message",""+role+" "+email+" deleted");
System.out.println("deleteOneUser");

SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
SecurityContextHolder.clearContext();

return "redirect:home";
}
@GetMapping("/login")
public String login(){
	System.out.println("login");
	return "login";
}

@GetMapping("/logout")
public String logout()
{
	System.out.println("logout");
	return "logout";
}
@GetMapping("/editUser")
public String editUser(
		@RequestParam Integer Id,
		Model m
		) {
user u=service.getUserById(Id);
m.addAttribute("user",u);
System.out.println("editUser");
return "Edit";
}
@GetMapping("/editDriver")
public String editDriver(
		@RequestParam Integer Id,
		Model m
		) {
user u=service.getUserById(Id);
m.addAttribute("user",u);
System.out.println("editUser");
return "EditDriver";
}
@GetMapping("/updateUser")
public String updtUser(
		@ModelAttribute user u,
		RedirectAttributes attributes
		) {
service.updateUser(u);
attributes.addAttribute("message",u.getRole()+" "+u.getEmail()+" updated");
System.out.println("updateCustomer");
if(u.getRole().equals("customer"))
return "redirect:Profile";
else
return "redirect:Dprofile";
}
@GetMapping("/report")
public String report()
{
	System.out.println("book_car");
	return "report";
}
@GetMapping("/saveComplain")
public String saveComplain(
		@RequestParam String compl,
		Model m
		)
{
	complain c=new complain();

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    
	c.setEmail(userDetails.getUsername());
	c.setDate(new Date());
	c.setMessage(compl);
	service.saveComplain(c);
	System.out.println("saveComplain");
	m.addAttribute("message","complain registered our team started work on it...");
	return "report";
}

@GetMapping("/booking_details")
public String booking_details(
		@RequestParam(required=false) String s,
		Model m
		){
	List<booking> x=service.allBooking();
	List<booking> lb=new ArrayList<booking>();
	for(booking b:x) {
		if(b.getStatus().equals("pending")) {
			lb.add(b);
		}
	}
	m.addAttribute("lb", lb);
	m.addAttribute("message", s);
	System.out.println("booking_user");
	return "booking_details";
}


@GetMapping("/book")
public String book()
{
	return "book_details";
}
@GetMapping("/bookConfirm")
public String bookConfirm(
		@ModelAttribute booking b,
		RedirectAttributes attributes
		)
{
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    System.out.println(b.getDate());
	b.setConsumerName(userDetails.getUsername());
	service.saveBooking(b);
	return "redirect:booking_user";
}

@GetMapping("/cancelBook")
public String cancelBook(
		@RequestParam Integer id,
		RedirectAttributes attributes
		){
	booking b=service.bookingById(id);
	b.setStatus("cancelled");
	service.saveBooking(b);
	attributes.addAttribute("message","booking "+id+" cancelled");
	return "redirect:booking_user";
}
@GetMapping("/cancelByDriver")
public String cancelByDriver(
		@RequestParam Integer id,
		RedirectAttributes attributes
		){
	booking b=service.bookingById(id);
	b.setStatus("pending");
	b.setCarNumber(null);
	service.saveBooking(b);
	attributes.addAttribute("message","you cancelled user "+b.getConsumerName()+" for "+b.getDate());
	return "redirect:booking_details";
}
@GetMapping("/acceptBook")
public String acceptBook(
		@RequestParam Integer id,
		RedirectAttributes attributes
		){
	booking b=service.bookingById(id);
	b.setStatus("accepted");
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	//b(userDetails.getUsername());
	user u=service.getOneUser(userDetails.getUsername());
	b.setCarNumber(u.getCnumber());
	service.saveBooking(b);
	attributes.addAttribute("message","car "+u.getCnumber()+" accepted");
	return "redirect:accepted_user";
}
@GetMapping("/accepted_user")
public String acceptedUser(
		@RequestParam(required=false) String message,
		Model m
		) {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	String email=userDetails.getUsername();
	String c=service.getOneUser(email).getCnumber();
	List<booking> x=service.allBooking();
	List<booking> lb=new ArrayList<booking>();
	for(booking b:x) {
		if(b.getStatus().equals("accepted") && b.getCarNumber().equals(c))
			lb.add(b);
	}
	//System.out.println(lb);
	m.addAttribute("lb", lb);
	m.addAttribute("message", message);
	return "acceptedUser";
}

@GetMapping("/booking_user")
public String booking_user(
		@RequestParam(required=false) String message,
		Model m
		){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	String email=userDetails.getUsername();
	
	List<booking> x=service.allBooking();
	List<booking> lb=new ArrayList<booking>();
	for(booking b:x) {
		if(b.getConsumerName().equals(email)) {
			lb.add(b);
		}
	}
	m.addAttribute("lb", lb);
	m.addAttribute("message", message);
	System.out.println("booking_user");
	return "booking_user";
}

@GetMapping("/details")
public String details() {
	return "details";
}


@GetMapping("/denied")
public String denied() {
	return "denied";
}

}