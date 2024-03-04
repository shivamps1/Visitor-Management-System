package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
public class VisitDTO {

    private Long id;

    @NotNull
    private VisitStatus status;

    private LocalDateTime inTime;

    private LocalDateTime outTime;

    @Size(max = 255)
    private String purpose;

    @Size(max = 255)
    private String imageUrl;

    @NotNull
    private Integer noOfPeople;

    @NotNull
    private Long visitor;

    @NotNull
    private Long flat;

}
