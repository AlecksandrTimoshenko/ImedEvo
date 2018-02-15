package imedevo.repository;


import imedevo.model.Doctor;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {

  Doctor findById(long id);

  Doctor findByUserId(long userId);

  List<Doctor> findAll();

  @Query(value = "SELECT * "
      + "FROM doctors d "
      + "JOIN users u ON d.user_id = u.id "
      + "WHERE (u.last_name) LIKE (?1)",
      nativeQuery = true)
  List<Doctor> findByDoctorLastname(String doctorParams);
}
