package com.uib.api.interfaces;

import com.uib.api.dtos.Property;
import com.uib.api.dtos.PropertyExtractDTO;

import java.util.List;

public interface IInputType<T> {
   T extract(Property property);
}