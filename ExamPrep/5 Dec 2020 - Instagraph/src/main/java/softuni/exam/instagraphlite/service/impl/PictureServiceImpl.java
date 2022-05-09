package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Dto.Json.PictureSeedDto;
import softuni.exam.instagraphlite.models.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    private static final String PICTURE_POST_FILE_PATH="src/main/resources/files/pictures.json";

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count()>0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURE_POST_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();



      Arrays.stream(this.gson.fromJson(readFromFileContent(), PictureSeedDto[].class))
                .filter(pictureSeedDto -> {
                    Picture picture = this.pictureRepository.findPictureByPath(pictureSeedDto.getPath());
                    boolean isValid =this.validationUtil.isValid(pictureSeedDto)&&picture==null;

                    sb.append(isValid ? String.format("Successfully imported Picture, with size %.2f", pictureSeedDto.getSize())
                            : "Invalid Picture").append(System.lineSeparator());

                    return isValid;
                }).map(pictureSeedDto -> modelMapper.map(pictureSeedDto, Picture.class))
                .forEach(this.pictureRepository::save);

        return sb.toString();
    }

    @Override
    public String exportPictures() {
        StringBuilder sb = new StringBuilder();
        double size = 30000;
        List<Picture> pictureWithSizeBiggerThan = this.pictureRepository.findAllBySizeGreaterThanOrderBySize(size);
        for (Picture picture : pictureWithSizeBiggerThan) {
            sb.append(String.format("%.2f – %s", picture.getSize(), picture.getPath())).append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public boolean ifPictureExist(String path) {
       Picture picture= pictureRepository.findPictureByPath(path);
        return picture==null;
    }

    @Override
    public Picture findPictureByPath(String path) {
        return pictureRepository.findPictureByPath(path);
    }
}
