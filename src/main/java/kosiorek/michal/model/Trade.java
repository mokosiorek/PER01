package kosiorek.michal.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "trade")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Producer> producers;

}
