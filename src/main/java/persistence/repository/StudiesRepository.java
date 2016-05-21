package persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import persistence.model.Study;

public interface StudiesRepository extends JpaRepository<Study, Long> {
	
	Study findByName(String name);
	
	Long countByName(String name);
}
