package com.swapi.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.swapi.user.dto.UserDetailsDtoResponse;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	UserRepository userRepo;
	
	@Mock
	UserMapper userMapper;
	
	@InjectMocks
	UserService userService;
	
	@Test
	void shouldReturnNullWhenUserNotExist() {
		when(userRepo.findById(1l))
			.thenReturn(Optional.empty());
		
		UserDetailsDtoResponse result = userService.getDetailsById(1L);
		
		assertNull(result);
	}
	
	@Test
	void shoulReturnDtoResponseWithStats() {
		User user = new User();
		
		UserDetailsDtoResponse dtoResponse = new UserDetailsDtoResponse();
		
		when(userRepo.findById(1L))
			.thenReturn(Optional.of(user));
		
		when(userMapper.toResponseDetails(user))
			.thenReturn(dtoResponse);
		
		when(userRepo.getCountTradesByUser(1L))
			.thenReturn(6);
		
		when(userRepo.getCountCollectionsByUser(1L))
			.thenReturn(1);
		
		UserDetailsDtoResponse result = userService.getDetailsById(1L);

		assertNotNull(result);
		assertEquals(result.getSwapsQuantity(), 6);
		assertEquals(result.getCollectionQuantity(), 1);
	}

}
