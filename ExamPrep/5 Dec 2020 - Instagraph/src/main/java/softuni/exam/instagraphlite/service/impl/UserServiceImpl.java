package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Dto.Json.UserSeedDto;
import softuni.exam.instagraphlite.models.Picture;
import softuni.exam.instagraphlite.models.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final String USER_SEED_FILE_PATH = "src/main/resources/files/users.json";

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final PictureService pictureService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, PictureRepository pictureRepository, PictureService pictureService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;

        this.pictureService = pictureService;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count()>0;
    }

    @Override
    public String readFromFileContent() throws IOException {

        return Files.readString(Path.of(USER_SEED_FILE_PATH));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder sb = new StringBuilder();


       Arrays.stream(this.gson.fromJson(readFromFileContent(), UserSeedDto[].class))
                .filter(userSeedDto -> {
                    User user = userRepository.findUserByUsername(userSeedDto.getUsername());
                   Picture picture=pictureService.findPictureByPath(userSeedDto.getProfilePicture());
                    boolean isValid = validationUtil.isValid(userSeedDto)&&user==null&&picture!=null;
                    sb.append(isValid ? String.format("Successfully imported User: %s", userSeedDto.getUsername())
                            : "Invalid User").append(System.lineSeparator());
                return isValid;
                }).map(userSeedDto -> {
                    User user = this.modelMapper.map(userSeedDto, User.class);
                    Picture pictureByPath = this.pictureService.findPictureByPath(userSeedDto.getProfilePicture());
                    if(pictureByPath!=null){
                        user.setProfilePicture(pictureByPath);
                    }
                    return user;
               }).forEach(userRepository::save);
        return sb.toString();

    }

    @Override
    public String exportUsersWithTheirPosts() {
        StringBuilder sb = new StringBuilder();
        List<User>allUsersByPost = this.userRepository.findAllUsersByPostCountDescThenByUserId();

        allUsersByPost.forEach(user -> {
            String username = user.getUsername();
            int size = user.getPosts().size();
            sb.append(String.format("User: %s", username))
                    .append(System.lineSeparator());
            sb.append(String.format("Post count: %d", size))
                    .append(System.lineSeparator());
            sb.append("==Post Details:")
                    .append(System.lineSeparator());
            user.getPosts()
                    .stream()
                    .sorted(Comparator.comparing(p->p.getPicture().getSize()))
                    .forEach(post -> {
                        String caption = post.getCaption();
                        sb.append(String.format("---Caption: %s", caption))
                                .append(System.lineSeparator());
                        double picSize = post.getPicture().getSize();
                        sb.append(String.format("---Picture Size: %.2f", picSize))
                                .append(System.lineSeparator());
                    });
        });
        return sb.toString();
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }
}
