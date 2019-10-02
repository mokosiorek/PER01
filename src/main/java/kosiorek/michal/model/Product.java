package kosiorek.michal.model;


import kosiorek.michal.model.enums.EGuarantee;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name="price", precision = 19, scale = 2)
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Stock> stocks;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "guarantee_components",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    @Column(name = "guarantee_component")
    @Enumerated(EnumType.STRING)
    private Set<EGuarantee> eGuarantees;

}
