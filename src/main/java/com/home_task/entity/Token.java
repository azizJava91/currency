package com.home_task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@Table(name = "token")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private String token;
    @ColumnDefault(value = "1")
    private Integer active;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

}
