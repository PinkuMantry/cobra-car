package in.nareshit.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.Entity.booking;

public interface bookingRepository extends JpaRepository<booking, Serializable> {

	@Query("select b from booking b where b.consumerName=:s")
	List<booking> findByConsumerName(String s);
}
