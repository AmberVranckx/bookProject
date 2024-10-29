package fact.it.readinglistservice.repository;

import fact.it.readinglistservice.model.ReadingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingListRepository extends JpaRepository<ReadingList, Long> {
}
