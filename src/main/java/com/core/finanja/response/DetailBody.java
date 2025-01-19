package com.core.finanja.response;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DetailBody {

    private Boolean success;

    private Integer index;

    private ArrayList<ErrorBody> error;
}
