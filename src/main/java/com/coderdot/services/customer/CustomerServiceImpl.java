package com.coderdot.services.customer;

import com.coderdot.dtos.BookACarDto;
import com.coderdot.dtos.CarDto;
import com.coderdot.dtos.CarDtoList;
import com.coderdot.dtos.SearchCarDto;
import com.coderdot.entities.BookACar;
import com.coderdot.entities.Car;
import com.coderdot.entities.Users;
import com.coderdot.enums.BookCarStatus;
import com.coderdot.repositories.BookACarRepository;
import com.coderdot.repositories.CarRepository;
import com.coderdot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    private final BookACarRepository bookACarRepository;

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public CarDto getCarById(Long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }

    @Override
    public boolean bookACar(Long carId, BookACarDto bookACarDto) {
        Optional<Users> optionalUser = userRepository.findById(bookACarDto.getUserId());
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isPresent() && optionalUser.isPresent()) {
            BookACar bookACar = new BookACar();
            long diffInMilliseconds = bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();
            long days = TimeUnit.MILLISECONDS.toDays(diffInMilliseconds);
            bookACar.setDays(days);
            bookACar.setUser(optionalUser.get());
            bookACar.setCar(optionalCar.get());
            bookACar.setAmount(optionalCar.get().getPrice() * days);
            bookACar.setFromDate(bookACarDto.getFromDate());
            bookACar.setToDate(bookACarDto.getToDate());
            bookACar.setBookCarStatus(BookCarStatus.PENDING);
            bookACarRepository.save(bookACar);
            return true;
        }
        return false;
    }

    @Override
    public List<BookACarDto> getBookingsByUserId(Long userId) {
        return bookACarRepository.findAllByUserId(userId).stream().map(BookACar::getBookACarDto).collect(Collectors.toList());
    }

    @Override
    public CarDtoList searchCar(SearchCarDto searchCarDto) {
        Car car = new Car();
        car.setBrand(searchCarDto.getBrand());
        car.setType(searchCarDto.getType());
        car.setTransmission(searchCarDto.getTransmission());
        car.setColor(searchCarDto.getColor());
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Car> carExample = Example.of(car,exampleMatcher);
        List<Car> cars = carRepository.findAll(carExample);
        CarDtoList carDtoList = new CarDtoList();
        carDtoList.setCarDtoList(cars.stream().map(Car::getCarDto).collect(Collectors.toList()));
        return carDtoList;
    }

}
