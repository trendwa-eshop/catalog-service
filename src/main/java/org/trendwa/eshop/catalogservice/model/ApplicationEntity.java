package org.trendwa.eshop.catalogservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Abstract base class for entities with auditing fields.
 * This class uses JPA annotations for ORM mapping and Spring Data annotations for auditing.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @ToString
@NoArgsConstructor
public abstract class ApplicationEntity {

    ApplicationEntity(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "UPDATED_AT", insertable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "UPDATED_BY", insertable = false)
    private String updatedBy;
}
