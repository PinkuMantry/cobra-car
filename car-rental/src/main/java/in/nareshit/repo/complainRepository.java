package in.nareshit.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nareshit.Entity.complain;

public interface complainRepository extends JpaRepository<complain, Serializable> {

}
