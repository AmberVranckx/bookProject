package fact.it.readinglistservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "readinglists")
public class ReadingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String readingListNumber;

}
