package com.hackerrank.weather.dto.adapter;

import com.hackerrank.weather.dto.RequestDTO;
import com.hackerrank.weather.model.BaseEntity;

public interface RequestDTOAdapter<D extends RequestDTO, M extends BaseEntity> {
    public M build(D d);
}
