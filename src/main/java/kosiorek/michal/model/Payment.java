package kosiorek.michal.model;

import kosiorek.michal.model.enums.EPayment;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private EPayment ePayment;

    @OneToMany(mappedBy = "payment")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CustomerOrder> customerOrders;

}
