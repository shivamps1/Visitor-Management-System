package com.example.demo.domain;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address implements Serializable {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String line1;

    @Column
    private String line2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column
    private String pincode;

    @Column(nullable = false)
    private String state;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date dateCreated;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date lastUpdated;

}
