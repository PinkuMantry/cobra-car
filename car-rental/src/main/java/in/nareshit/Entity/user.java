package in.nareshit.Entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class user {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Integer id;
private String name;
private Integer age;
private String gen;
private Long mob;
@Column(unique=true,nullable=false)
private String email;
private String addr;
private String cnumber;
private String dlnumber;
private String pass;
private String role;
}
