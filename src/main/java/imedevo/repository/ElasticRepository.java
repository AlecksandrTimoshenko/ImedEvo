package imedevo.repository;


import imedevo.model.AppUser;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticRepository extends ElasticsearchRepository<AppUser, String> {

  Page<AppUser> findByLastName(String lastName, Pageable pageable);

  List<AppUser> findByLastName(String lastName);

}
