package in.nareshit.serviceImpl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.nareshit.Entity.booking;
import in.nareshit.Entity.complain;
import in.nareshit.Entity.user;
import in.nareshit.Iservice.IuserService;
import in.nareshit.repo.bookingRepository;
import in.nareshit.repo.complainRepository;
import in.nareshit.repo.userRepository;

@Service
public class userServiceImpl implements IuserService,UserDetailsService {

	@Autowired
	private userRepository urepo;
	@Autowired
	private complainRepository comRepo;
	@Autowired
	private bookingRepository bookrepo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	//user view
	public Integer saveUser(user u) {
	String encPwd = encoder.encode(u.getPass());
	u.setPass(encPwd);
	//c.setRole("employee");
	return urepo.save(u).getId();
	}
	//admin can view
	public List<user> getAllUser(){
		return urepo.findAll();
	}
	public user getOneUser(String email){
		return urepo.findByEmail(email).get();
	}
	public void deleteUser(Integer Id) {
		urepo.deleteById(Id);
	}
	public void updateUser(user u) {
		urepo.save(u);
	}
	public user getUserById(Integer Id){
		//System.out.println(urepo.findById(Id).get());
		return urepo.findById(Id).get();
	}
	
	public void saveComplain(complain c) {
		comRepo.save(c);
	}
	public void saveBooking(booking b) {
		bookrepo.save(b);
	}
	public List<booking> allBooking(){
		return bookrepo.findAll();
	}
	public List<booking> allBookingUser(String s){
		return bookrepo.findByConsumerName(s);
	}
	public booking bookingById(Integer id){
		return bookrepo.findById(id).get();
	}
	
	@Override
	public Optional<user> findByEmail(String email) {
		return urepo.findByEmail(email);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		Optional<user> opt = findByEmail(username);
		
		if(!opt.isPresent())
				throw new UsernameNotFoundException("Not exist");
		else {
			user user = opt.get();
			Set<String> s=new LinkedHashSet<String>();
			s.add(user.getRole());
			return new org.springframework.security.core.userdetails.User(
					user.getEmail(),
					user.getPass(), 
					s.stream()
					.map(role->new SimpleGrantedAuthority(role))
					.collect(Collectors.toSet())
					);
		}
		
	}
	
}
