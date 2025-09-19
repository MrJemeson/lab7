package ru.bmstu.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.bmstu.entity.UserLocal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindUser() {
        UserLocal s = new UserLocal("Test", "Student", "test", 10);
        UserLocal saved = userRepository.save(s);
        assertThat(saved.getId()).isGreaterThan(0);

        UserLocal found = userRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getFullName()).isEqualTo("Test");
        assertThat(found.getRole()).isEqualTo("Student");
        assertThat(found.getTokens()).isEqualTo(10);
    }

    @Test
    void updateExistingStudent() {
        UserLocal s = userRepository.save(new UserLocal("Test1", "Student", "test", 10));
        s.setTokens(11);
        UserLocal updated = userRepository.save(s);

        UserLocal reloaded = userRepository.findById(updated.getId()).orElseThrow();
        assertThat(reloaded.getTokens()).isEqualTo(11);
    }

    @Test
    void deleteStudentById() {
        UserLocal s = userRepository.save(new UserLocal("Test2", "Student", "test", 10));
        int id = s.getId();
        assertThat(userRepository.existsById(id)).isTrue();
        userRepository.deleteById(id);
        assertThat(userRepository.existsById(id)).isFalse();
        assertThat(userRepository.findById(id)).isEmpty();
    }
}
