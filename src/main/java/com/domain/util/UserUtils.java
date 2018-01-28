package com.domain.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.util.FastMap;

import com.domain.user.Admin;
import com.domain.user.Member;
import com.domain.user.User;

public abstract class UserUtils {
	
	public static ArrayList<String> splitLineOfSingleUserDataByDelimiterIntoListOfString(String user) {
		ArrayList<String> userCredentials = new ArrayList<String>();
		String delimiter = "[|]";

		// Use | symbol as delimiter
		userCredentials.addAll(Arrays.asList(user.split(delimiter)));
		System.out.println(user);
		// }
		return userCredentials;
	}
	
	public static String joinListOfStringIntoLineOfSingleUserDataWithDelimiter(List<String> userCredentials, boolean requiredForRegex){
		StringBuilder sb = new StringBuilder();
		String delimiter = "|";
		String regexEscape = "\\";
		String userData = null;
		
		if (requiredForRegex){
			for (String userCredential : userCredentials){
				sb.append(userCredential);
				sb.append(regexEscape);
				sb.append(delimiter);
			}
		} else {
			for (String userCredential : userCredentials){
				sb.append(userCredential);
				sb.append(delimiter);
			}
		}
		
		// Remove delimiter character at end of line
		if (requiredForRegex){
			sb.delete(sb.length() - 2, sb.length());
		} else {
			sb.deleteCharAt(sb.length() - 1);
		}
		userData = sb.toString();
		System.out.println(userData);
		return userData;
	}
	
	public static Map<Integer, User> convertListOfUsersIntoMapOfUsers(List<User> users) {
		Map<Integer, User> mapOfUsers = new FastMap<Integer, User>();
		for (User user : users) {
			mapOfUsers.put(user.getUserId(), user);
		}
		return mapOfUsers;
	}
	
	public static User convertUserCredentialsIntoUserObject(List<String> userCredentials) {
		User user = null;
		if (userCredentials != null) {
			if (userCredentials.size() == 4 && userCredentials.get(0).contains("UserId:")
					&& userCredentials.get(1).contains("Username:") && userCredentials.get(2).contains("Password:")
					&& userCredentials.get(3).contains("Role:")) {
				int userId = Integer.parseInt(userCredentials.get(0).substring(7));
				String username = userCredentials.get(1).substring(9);
				String password = userCredentials.get(2).substring(9);
				String role = userCredentials.get(3).substring(5);
				if (role.equalsIgnoreCase("member")) {
					user = new Member(userId, username, password);
				} else if (role.equalsIgnoreCase("admin")) {
					user = new Admin(userId, username, password);
				} else {
					System.out.println("Role not recognised. No user created");
				}
			} else {
				System.out.println("Incorrect format for user credentials or insufficient credentials provided");
			}

		} else {
			System.out.println("User Credentials were NULL");
		}
		return user;
	}

	public static List<String> convertUserObjectIntoUserCredentials(User user) {
		ArrayList<String> userCredentials = null;
		if (user != null && user.getUserId() > 0 && user.getUsername() != null && user.getPassword() != null
				&& user.getRole() != null) {
			userCredentials = new ArrayList<String>();
			String userId = ("UserId:" + String.valueOf(user.getUserId()));
			String username = ("Username:" + user.getUsername());
			String password = ("Password:" + user.getPassword());
			String role = ("Role:" + user.getRole());
			userCredentials.add(userId);
			userCredentials.add(username);
			userCredentials.add(password);
			userCredentials.add(role);
		} else {
			System.out.println("User was NULL or insufficient user credentials");
		}
		return userCredentials;
	}

}
