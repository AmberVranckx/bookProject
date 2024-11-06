package fact.it.readinglistservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "readinglistline")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReadingListLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private int rating;
    private String feedback;

}
