package com.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Anish Panthi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto implements Serializable {

    protected String firstTermMarks;

    protected String secondTermMarks;
}
