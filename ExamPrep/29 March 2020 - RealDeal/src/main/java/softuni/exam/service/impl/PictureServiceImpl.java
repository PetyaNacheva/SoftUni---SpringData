package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.Dto.PictureSeedDto;
import softuni.exam.models.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PICTURE_SEED_PATH = "src/main/resources/files/json/pictures.json";

    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CarService carService;

    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, CarService carService) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.carService = carService;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count()>0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(PICTURE_SEED_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();
        List<Picture> validPictures = Arrays.stream(this.gson.fromJson(readPicturesFromFile(), PictureSeedDto[].class))
                .filter(pictureSeedDto -> {
                    boolean isValid = validationUtil.isValid(pictureSeedDto);

                    sb.append(isValid ? String.format("Successfully import picture %s", pictureSeedDto.getName()) : "Invalid picture").append(System.lineSeparator());

                            return isValid;
                })
                .map(pictureSeedDto -> {
                    Picture picture = modelMapper.map(pictureSeedDto, Picture.class);
                    Long carId = pictureSeedDto.getCar();
                    Car car = this.carService.findById(carId);
                    picture.setCar(car);
                    return picture;
                }).collect(Collectors.toList());

        this.pictureRepository.saveAll(validPictures);


        return sb.toString();
    }
}
