package fact.it.readinglistservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "readinglists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String readingListNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ReadingListLine> readingListLine;

}
