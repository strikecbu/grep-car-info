package com.andysrv.carinfoservice.mapper;

import com.andysrv.carinfoservice.dto.CarEvent;
import com.andysrv.carinfoservice.dto.CarView;
import com.andysrv.carinfoservice.entity.CarInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarInfoMapper {

    CarInfo dtoToEntity(CarEvent carEvent);


    CarView entityToView(CarInfo carInfo);
}
