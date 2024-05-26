package com.juliaosistem.mcs_financial.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PlantillaResponse<E>{
    private boolean rta;
    private String message;
    private HttpStatus httpStatus;
    private E data;
    private List<E> dataList;
}
