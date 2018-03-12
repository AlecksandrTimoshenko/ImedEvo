package imedevo.service;

import imedevo.model.AppUser;
import imedevo.repository.ElasticRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ElasticAppUserService {

  @Autowired
  ElasticRepository elasticRepository;

  public Page<AppUser> findByLastName(String lastName, PageRequest pageRequest) {
    return elasticRepository.findByLastName(lastName, pageRequest);
  }

  public List<AppUser> findByLastName(String lastName) {
    return elasticRepository.findByLastName(lastName);
  }

}
