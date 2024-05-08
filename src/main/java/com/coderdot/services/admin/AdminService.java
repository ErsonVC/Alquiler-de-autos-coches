package com.coderdot.services.admin;

import com.coderdot.dtos.BookACarDto;
import com.coderdot.dtos.CarDto;
import com.coderdot.dtos.CarDtoList;
import com.coderdot.dtos.SearchCarDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    boolean postCar(CarDto carDto);

    List<CarDto> getAllCars();

    void deleteCar(Long carId);

    CarDto getCarById(Long cardId);

    boolean updateCar(Long carId,CarDto carDto) throws IOException;

    List<BookACarDto> getBookings();

    boolean changeBookingStatus(Long bookingId, String status);

    CarDtoList searchCar(SearchCarDto searchCarDto);

}
