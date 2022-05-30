package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.Dto.CarSeedDto;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private static final String CAR_IMPORT_FILE_PATH="src/main/resources/files/json/cars.json";

    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CarServiceImpl(CarRepository carRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count()>0;
    }

    @Override
    public String readCarsFileContent() throws IOException {

        return  Files.readString(Path.of(CAR_IMPORT_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();

       CarSeedDto [] carSeedDtos = this.gson.fromJson(this.readCarsFileContent(), CarSeedDto[].class);



        Arrays.stream(this.gson.fromJson(readCarsFileContent(), CarSeedDto[].class))
                .filter(carSeedDto -> {
                    boolean isValid=this.validationUtil.isValid(carSeedDto);
                    sb.append(isValid ?
                            String.format("Successfully imported car - %s - %s",
                                    carSeedDto.getMake(),
                                    carSeedDto.getModel()) : "Invalid car" )
                                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(carSeedDto -> modelMapper.map(carSeedDto, Car.class))
                .forEach(carRepository::save);

        return sb.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder sb = new StringBuilder();
        List<Car>carsOrdered = this.carRepository.findCarsOrderedByPicturesCountThenByMake();

        carsOrdered.stream().forEach(car -> {
            sb.append(String.format("Car make - %s, model - %s",car.getMake(), car.getModel())).append(System.lineSeparator())
                    .append(String.format("\tKilometers - %d", car.getKilometers())).append(System.lineSeparator())
                    .append(String.format("\tRegistered on - %s", car.getRegisteredOn())).append(System.lineSeparator())
                    .append(String.format("\tNumber of pictures - %d", car.getPictures().size())).append(System.lineSeparator());
        });

        return sb.toString();
    }

    @Override
    public Car findById(Long carId) {
        return carRepository.findById(carId).orElse(null);
    }
}
