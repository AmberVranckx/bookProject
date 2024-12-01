package fact.it.userservice;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import fact.it.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceApplicationTests {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Test
	public void testCreateUser() {
		//Arrange
		UserRequest userRequest = new UserRequest();
		userRequest.setFirstname("firstname");
		userRequest.setLastname("lastname");
		userRequest.setEmail("test@email.com");
		userRequest.setBirthday(new Date(2004-8-15));

		//Act
		userService.createUser(userRequest);

		//Assert
		verify(userRepository, times(1)).save(any(User.class));

	}

	@Test
	public void testGetUsers() {
		//Arrange
		User user = new User();
		user.setId("1");
		user.setBirthday(new Date(2001-12-01));
		user.setFirstname("firstname");
		user.setLastname("lastname");
		user.setEmail("test@email.com");

		when(userRepository.findAll()).thenReturn(Arrays.asList(user));

		//Act
		List<UserResponse> users = userService.getUsers();

		//Assert
		assertEquals("1", users.get(0).getId());
		assertEquals("firstname", users.get(0).getFirstname());
		assertEquals("lastname", users.get(0).getLastname());
		assertEquals("test@email.com", users.get(0).getEmail());
		assertEquals(1, users.size());

		verify(userRepository, times(1)).findAll();

	}

}
