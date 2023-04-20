package mate.academy.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.dto.UserResponseDto;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import mate.academy.spring.service.mapper.UserDtoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserDtoMapper dtoMapper;

    public UserController(UserService userService, UserDtoMapper dtoMapper) {
        this.userService = userService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("/{userId}")
    public UserResponseDto get(@PathVariable Long userId) {
        return dtoMapper.parse(userService.get(userId));
    }

    @GetMapping
    private List<UserResponseDto> getAll() {
        return userService.getAll().stream()
                .map(dtoMapper::parse)
                .collect(Collectors.toList());
    }

    @GetMapping("/inject")
    public String injectMockData() {
        User john = new User();
        john.setFirstName("John");
        john.setLastName("Doe");
        userService.add(john);

        User emily = new User();
        emily.setFirstName("Emily");
        emily.setLastName("Stone");
        userService.add(emily);

        User hugh = new User();
        hugh.setFirstName("Hugh");
        hugh.setLastName("Dane");
        userService.add(hugh);

        return "Users are injected!";
    }
}