package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.EmailExistedException;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void registerUser() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        userService.registerUser(email, name, password);

        verify(userRepository).save(any());
    }

    @Test
    public void registerUserWithExistedEmail() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        given(userRepository.findByEmail(email))
                .willThrow(new EmailExistedException(email));

        assertThrows(EmailExistedException.class, () -> {
            userService.registerUser(email, name, password);
        });

        verify(userRepository, never()).save(any());
    }
}