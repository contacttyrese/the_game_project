package com.domain.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.domain.mvc.UserDao;
import com.domain.user.Member;
import com.domain.user.User;

public class UserUtilsTest {
	
	@Test
	public void testConvertUserCredentialsIntoUserObjectReturnsNullWhenUserCredentialsAreNull() {
		// Arrange

		// Act
		User actual = UserUtils.convertUserCredentialsIntoUserObject(null);

		// Assert
		assertNull(actual);
	}

	@Test
	public void testConvertUserCredentialsIntoUserObjectReturnsUserObjectWhenUserCredentialsContainsFourElementsAndUserRoleIsMemberAndElementsAreCorrectStringFormat() {
		// Arrange
		List<String> userCredentials = new ArrayList<String>();
		userCredentials.add("UserId:1");
		userCredentials.add("Username:username");
		userCredentials.add("Password:password");
		userCredentials.add("Role:Member");

		// Act
		User actual = UserUtils.convertUserCredentialsIntoUserObject(userCredentials);

		// Assert
		assertEquals(1, actual.getUserId());
		assertEquals("username", actual.getUsername());
		assertEquals("password", actual.getPassword());
		assertEquals("Member", actual.getRole());
	}

	@Test
	public void testConvertUserCredentialsIntoUserObjectReturnsNullWhenUserCredentialsContainsFourElementsAndUserRoleIsMemberAndElementsAreIncorrectStringFormatForUserId() {
		// Arrange
		List<String> userCredentials = new ArrayList<String>();
		userCredentials.add("1");
		userCredentials.add("username");
		userCredentials.add("password");
		userCredentials.add("role");

		// Act
		User actual = UserUtils.convertUserCredentialsIntoUserObject(userCredentials);

		// Assert
		assertNull(actual);
	}

	@Test
	public void testConvertUserCredentialsIntoUserObjectReturnsNullWhenUserCredentialsContainsFourElementsAndUserRoleIsMemberAndElementsAreIncorrectStringFormatForUsername() {
		// Arrange
		List<String> userCredentials = new ArrayList<String>();
		userCredentials.add("UserId:1");
		userCredentials.add("username");
		userCredentials.add("password");
		userCredentials.add("role");

		// Act
		User actual = UserUtils.convertUserCredentialsIntoUserObject(userCredentials);

		// Assert
		assertNull(actual);
	}

	@Test
	public void testConvertUserCredentialsIntoUserObjectReturnsNullWhenUserCredentialsContainsFourElementsAndUserRoleIsMemberAndElementsAreIncorrectStringFormatForPassword() {
		// Arrange
		List<String> userCredentials = new ArrayList<String>();
		userCredentials.add("UserId:1");
		userCredentials.add("Username:username");
		userCredentials.add("password");
		userCredentials.add("role");

		// Act
		User actual = UserUtils.convertUserCredentialsIntoUserObject(userCredentials);

		// Assert
		assertNull(actual);
	}

	@Test
	public void testConvertUserCredentialsIntoUserObjectReturnsNullWhenUserCredentialsContainsFourElementsAndUserRoleIsMemberAndElementsAreIncorrectStringFormatForRole() {
		// Arrange
		List<String> userCredentials = new ArrayList<String>();
		userCredentials.add("UserId:1");
		userCredentials.add("Username:username");
		userCredentials.add("Password:password");
		userCredentials.add("role");

		// Act
		User actual = UserUtils.convertUserCredentialsIntoUserObject(userCredentials);

		// Assert
		assertNull(actual);
	}

	@Test
	public void testConvertUserCredentialsIntoUserObjectReturnsUserObjectWhenUserCredentialsContainsFourElementsAndUserRoleIsAdmin() {
		// Requires implementation for admin
	}

	@Test
	public void testConvertUserCredentialsIntoUserObjectReturnsNullWhenUserCredentialsContainsLessThanFourElements() {
		// Arrange
		List<String> userCredentials = new ArrayList<String>();
		userCredentials.add("UserId:1");
		userCredentials.add("Username:username");
		userCredentials.add("Password:password");

		// Act
		User actual = UserUtils.convertUserCredentialsIntoUserObject(userCredentials);

		// Assert
		assertNull(actual);
	}

	@Test
	public void testConvertUserCredentialsIntoUserObjectReturnsNullWhenUserCredentialsContainsMoreThanFourElements() {
		// Arrange
		List<String> userCredentials = new ArrayList<String>();
		userCredentials.add("UserId:1");
		userCredentials.add("Username:username");
		userCredentials.add("Password:password");
		userCredentials.add("Role:Member");
		userCredentials.add("role");

		// Act
		User actual = UserUtils.convertUserCredentialsIntoUserObject(userCredentials);

		// Assert
		assertNull(actual);

	}

	@Test
	public void testConvertUserCredentialsIntoUserObjectReturnsNullWhenUserRoleIsNotMemberAndNotAdmin() {
		// Arrange
		List<String> userCredentials = new ArrayList<String>();
		userCredentials.add("UserId:1");
		userCredentials.add("Username:username");
		userCredentials.add("Password:password");
		userCredentials.add("Role:User");

		// Act
		User actual = UserUtils.convertUserCredentialsIntoUserObject(userCredentials);

		// Assert
		assertNull(actual);
	}

	@Test
	public void testConvertUserObjectIntoUserCredentialsReturnsUserWhenMemberHasUserIdAndUsernameAndPasswordAndRoleAreSet() {
		// Arrange
		User user = new Member(1, "username", "password");

		// Act
		List<String> actual = UserUtils.convertUserObjectIntoUserCredentials(user);

		// Assert
		assertEquals("UserId:1", actual.get(0));
		assertEquals("Username:username", actual.get(1));
		assertEquals("Password:password", actual.get(2));
		assertEquals("Role:Member", actual.get(3));
	}

	@Test
	public void testConvertUserObjectIntoUserCredentialsReturnsNullWhenUserIsNull() {
		// Arrange

		// Act
		List<String> actual = UserUtils.convertUserObjectIntoUserCredentials(null);

		// Assert
		assertNull(actual);
	}

	@Test
	public void testConvertUserObjectIntoUserCredentialsReturnsNullWhenUserHasNoUserId() {
		// Arrange
		User user = new Member();

		// Act
		List<String> actual = UserUtils.convertUserObjectIntoUserCredentials(user);

		// Assert
		assertNull(actual);
	}

	@Test
	public void testConvertUserObjectIntoUserCredentialsReturnsNullWhenUserHasNoUsername() {
		// Arrange
		User user = new Member();
		user.setUserId(1);

		// Act
		List<String> actual = UserUtils.convertUserObjectIntoUserCredentials(user);

		// Assert
		assertNull(actual);
	}

	@Test
	public void testConvertUserObjectIntoUserCredentialsReturnsNullWhenUserHasNoPassword() {
		// Arrange
		User user = new Member();
		user.setUserId(1);
		user.setUsername("username");

		// Act
		List<String> actual = UserUtils.convertUserObjectIntoUserCredentials(user);

		// Assert
		assertNull(actual);
	}

	@Test
	public void testConvertUserObjectIntoUserCredentialsReturnsNullWhenUserHasNoRole() {
		// Arrange
		User user = new Member();
		user.setUserId(1);
		user.setUsername("username");
		user.setPassword("password");

		// Act
		List<String> actual = UserUtils.convertUserObjectIntoUserCredentials(user);

		// Assert
		assertNull(actual);

	}

}
