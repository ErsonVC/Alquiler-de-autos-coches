package com.coderdot.services.customer;

import com.coderdot.dtos.BookACarDto;
import com.coderdot.dtos.CarDto;
import com.coderdot.dtos.CarDtoList;
import com.coderdot.dtos.SearchCarDto;

import java.util.List;

public interface CustomerService {

    List<CarDto> getAllCars();

    CarDto getCarById(Long carId);

    boolean bookACar(Long carId, BookACarDto bookACarDto);

    List<BookACarDto> getBookingsByUserId(Long userId);

    CarDtoList searchCar(SearchCarDto searchCarDto);

}
