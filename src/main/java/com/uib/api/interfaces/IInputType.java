package com.uib.api.interfaces;

import com.uib.api.dtos.Property;

public interface IInputType<T> {
   T extract(Property property);
}