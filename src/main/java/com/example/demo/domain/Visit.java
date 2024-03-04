package com.example.demo.domain;


import com.example.demo.model.VisitStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visit {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VisitStatus status;

    @Column
    private LocalDateTime inTime;

    @Column
    private LocalDateTime outTime;

    @Column
    private String purpose;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private Integer noOfPeople;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flat_id", nullable = false)
    private Flat flat;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date dateCreated;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date lastUpdated;

}
