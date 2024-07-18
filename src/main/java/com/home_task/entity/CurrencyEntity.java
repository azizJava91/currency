package com.home_task.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "currency")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long currencyId;

    @JsonProperty("Nominal")
    @Column(name = "nominal")
    private Double nominal;

    @JsonProperty("Code")
    @Column(name = "code")
    private String code;

    @JsonProperty("Name")
    @Column(name = "name")
    private String name;

    @JsonProperty("Value")
    @Column(name = "aznValue", scale = 4, precision = 10)
    private BigDecimal value;

}
