package com.hackerrank.weather.dto.adapter;

import com.hackerrank.weather.dto.ResponseDTO;
import com.hackerrank.weather.model.BaseEntity;

import java.util.List;

public interface ResponseDTOAdapter<M extends BaseEntity, D extends ResponseDTO> {
    public D build(M m);
    public List<D> buildAll(List<M> mList);
}
