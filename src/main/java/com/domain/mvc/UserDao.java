package com.domain.mvc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.text.WordUtils;
import org.codehaus.plexus.util.FastMap;

import com.domain.user.Admin;
import com.domain.user.Member;
import com.domain.user.User;
import com.domain.util.UserUtils;

public class UserDao {
	private Reader reader = null;
	private Writer writer = null;
	private BufferedReader bufferedReader = null;
	// Instantiating a Writer on a new file causes file contents to be erased
	private BufferedWriter bufferedWriter = null;
	
	// Put the following variables in the DAO interface
	private final String filenameForMemberDirectoryWriter = "MemberDirectoryWriter.xml";
	private final String filenameForMemberDirectoryReader = "MemberDirectoryReader.xml";
	private final String userTypeAdmin = "Admin";
	private final String userTypeMember = "Member";

	public UserDao() {

	}

	// When DAO interface is implemented then this method will be overridden
	public void persistObject(User user) {
		List<User> listOfUsers = new ArrayList<User>();
		listOfUsers = retrieveAllUsers();
		
		if (!listOfUsers.contains(user)) {
			listOfUsers.add(user);
			BufferedWriter bw = getBufferedWriterObject(filenameForMemberDirectoryWriter);
			try {
				for (User theUser : listOfUsers) {
					List<String> userDataAsAList = UserUtils.convertUserObjectIntoUserCredentials(theUser);
					String userDataAsALine = UserUtils.joinListOfStringIntoLineOfSingleUserDataWithDelimiter(userDataAsAList, false);
					bw.write(userDataAsALine);
					bw.newLine();
				}
				bw.flush();
				bw = getBufferedWriterObject(filenameForMemberDirectoryReader);
				for (User theUser : listOfUsers) {
					List<String> userDataAsAList = UserUtils.convertUserObjectIntoUserCredentials(theUser);
					String userDataAsALine = UserUtils.joinListOfStringIntoLineOfSingleUserDataWithDelimiter(userDataAsAList, false);
					bw.write(userDataAsALine);
					bw.newLine();
				}
				bw.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} else {
			System.out.println("user is already in user directory");
		}
		
	}

	// When DAO interface is implemented then this method will be overridden
	public void deleteObject(int uniqueId) {
		BufferedReader br = getBufferedReaderObject(filenameForMemberDirectoryReader);
		List<String> linesOfAdminsAsAList = new ArrayList<String>();
		List<String> linesOfMembersAsAList = new ArrayList<String>();
		List<User> listOfUsers = new ArrayList<User>();
		List<String> linesOfUsersAsAList = new ArrayList<String>();
		List<String> userCredentials = new ArrayList<String>();
		Map<Integer, User> mapOfUsers = new FastMap<Integer, User>();
		User user = null;
		User userToFind = null;
		String userDataAsASingleLineString = "";

		// Retrieve users as a list
		listOfUsers = retrieveAllUsers();

		// If list is not empty and not NULL then convert into map
		if (listOfUsers != null && !listOfUsers.isEmpty()) {
			mapOfUsers = UserUtils.convertListOfUsersIntoMapOfUsers(listOfUsers);
		} else {
			System.out.println("Error in retrieving all users");
		}

		// Check the unique id of the user is in the map
		if (mapOfUsers.containsKey(uniqueId)) {
			// Find user from map
			userToFind = mapOfUsers.get(uniqueId);
			// Convert found user into a line and use REGEX to remove pattern match on XML file
			userCredentials = UserUtils.convertUserObjectIntoUserCredentials(userToFind);
			String lineOfUserFoundDataForRegex = UserUtils
					.joinListOfStringIntoLineOfSingleUserDataWithDelimiter(userCredentials, true);
			Pattern regexPatternOfUserFoundData = Pattern.compile("(?m)^" + lineOfUserFoundDataForRegex + "$");
			for (User theUser : listOfUsers) {
				userCredentials = UserUtils.convertUserObjectIntoUserCredentials(theUser);
				userDataAsASingleLineString = UserUtils
						.joinListOfStringIntoLineOfSingleUserDataWithDelimiter(userCredentials, false);
				linesOfUsersAsAList.add(userDataAsASingleLineString);
			}
			boolean isLineOfUserFoundDataInLinesOfUsers = false;
			int i = 0;
			while (isLineOfUserFoundDataInLinesOfUsers != true && linesOfUsersAsAList.size() > i) {
				for (String lineOfUserDataAsALine : linesOfUsersAsAList) {
					Matcher matcherOfUserFoundPattern = regexPatternOfUserFoundData.matcher(lineOfUserDataAsALine);
					isLineOfUserFoundDataInLinesOfUsers = matcherOfUserFoundPattern.matches();
					if (isLineOfUserFoundDataInLinesOfUsers) {
						break;
					} else {
						i++;
					}
				}
			}
			
			if (isLineOfUserFoundDataInLinesOfUsers) {
				userCredentials = UserUtils.convertUserObjectIntoUserCredentials(userToFind);
				String lineOfUserFoundData = UserUtils
						.joinListOfStringIntoLineOfSingleUserDataWithDelimiter(userCredentials, false);
				boolean isLineOfFoundUserDataRemovedFromListOfUsers = linesOfUsersAsAList.remove(lineOfUserFoundData);
				if (isLineOfFoundUserDataRemovedFromListOfUsers) {
					BufferedWriter bw = getBufferedWriterObject(filenameForMemberDirectoryWriter);
					System.out.println(linesOfUsersAsAList.toString());
					try {
						for (String userLineData : linesOfUsersAsAList) {
							bw.write(userLineData);
							bw.newLine();
						}
						bw.flush();
						// Ensure both files have the same data
						bw = getBufferedWriterObject(filenameForMemberDirectoryReader);
						for (String userLineData : linesOfUsersAsAList) {
							bw.write(userLineData);
							bw.newLine();
						}
						bw.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
				} else {
					System.out.println("Line was not removed successfully from the users directory");
				}
			} else {
				System.out.println("User data did not match with any lines in users directory");
			}
		} else {
			System.out.println("User Not Found");
		}
		
		try {
			br.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public User getUserFromDirectoryByUserId(int userId) {
		Map<Integer, User> mapOfUsers = new FastMap<Integer, User>();
		List<User> listOfUsers = new ArrayList<User>();
		User user = null;
		
		listOfUsers = retrieveAllUsers();
		
		// If list is not empty and not NULL then convert into map
		if (listOfUsers != null && !listOfUsers.isEmpty()) {
			mapOfUsers = UserUtils.convertListOfUsersIntoMapOfUsers(listOfUsers);
		} else {
			System.out.println("Error in retrieving all users");
		}
		if (mapOfUsers.containsKey(userId)){
			user = mapOfUsers.get(userId);
			return user;
		} else {
			return user;
		}
	}

//	public List<String> getAllUsersOfUserTypeFromUserDirectory(String userType) {
//		List<String> users = new ArrayList<String>();
//
//		// Check user type is correct
//		if (userType.equalsIgnoreCase("admin") || userType.equalsIgnoreCase("member")) {
//			userType = WordUtils.capitalizeFully(userType);
//			users = retrieveAllUsersOfUserTypeAsLinesFromFileAsListOfString(userType);
//		} else {
//			System.out.println("User type not recognised");
//		}
//		return users;
//	}

	public List<User> retrieveAllUsersOfUserTypeFromUserDirectory(String userType) {
		BufferedReader bufferedReader = getBufferedReaderObject(filenameForMemberDirectoryReader);
		String userAsALine;
		List<String> usersAsAListOfString = new ArrayList<String>();
		List<String> userCredentials;
		User user = null;
		boolean userAdded = false;
		List<User> listOfUsers = new ArrayList<User>();
		List<User> users = new ArrayList<User>();

		if (userType.equalsIgnoreCase(userTypeAdmin) || userType.equalsIgnoreCase(userTypeMember)) {
			userType = WordUtils.capitalizeFully(userType);
			// Retrieve all lines from XML file
			try {
				if (bufferedReader.ready()) {
					userAsALine = bufferedReader.readLine();
					while (userAsALine != null) {
						if (userAsALine.contains("Role:" + userType)) {
							usersAsAListOfString.add(userAsALine);
						}
						userAsALine = bufferedReader.readLine();
					}

				} else {
					System.out.println("Stream to read was not ready");
				}

				for (String lineOfUserData : usersAsAListOfString) {
					userCredentials = UserUtils.splitLineOfSingleUserDataByDelimiterIntoListOfString(lineOfUserData);
					user = UserUtils.convertUserCredentialsIntoUserObject(userCredentials);
					userAdded = listOfUsers.add(user);
				}

				if (listOfUsers == null || listOfUsers.isEmpty()) {
					System.out.println("list of users is empty");
				} else if (!userAdded) {
					System.out.println("list of users had an error adding all users");
				}

			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		} else {
			System.out.println("User type not recognised");
		}

		return users;
	}

	private BufferedReader getBufferedReaderObject(String filename) {
		String filenameToUse = "";
		try {
			if (this.bufferedReader == null || !this.bufferedReader.ready() || !filename.equals(filenameToUse)) {
				try {
					filenameToUse = filename;
					reader = new FileReader(filename);
					bufferedReader = new BufferedReader(reader);
				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return bufferedReader;
	}

	private BufferedWriter getBufferedWriterObject(String filename) {
		String filenameToUse = "";
		if (this.bufferedWriter == null || !filename.equals(filenameToUse)) {
			try {
				filenameToUse = filename;
				writer = new FileWriter(filename);
				bufferedWriter = new BufferedWriter(writer);
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return bufferedWriter;
	}
	
	public List<User> retrieveAllUsers(){
		BufferedReader bufferedReader = getBufferedReaderObject(filenameForMemberDirectoryReader);
		String userAsALine;
		List<String> usersAsAListOfString = new ArrayList<String>();
		List<String> userCredentials;
		User user = null;
		boolean userAdded = false;
		List<User> listOfUsers = new ArrayList<User>();
		
		// Retrieve all lines from XML file
		try {
			if (bufferedReader.ready()) {
				userAsALine = bufferedReader.readLine();
				while (userAsALine != null) {
						usersAsAListOfString.add(userAsALine);
						userAsALine = bufferedReader.readLine();
				}

			} else {
				System.out.println("Stream to read was not ready");
			}
			
			for (String lineOfUserData : usersAsAListOfString) {
				userCredentials = UserUtils.splitLineOfSingleUserDataByDelimiterIntoListOfString(lineOfUserData);
				user = UserUtils.convertUserCredentialsIntoUserObject(userCredentials);
				userAdded = listOfUsers.add(user);
			}
			
			if (listOfUsers == null || listOfUsers.isEmpty()){
				System.out.println("list of users is empty");
			} else if (!userAdded) {
				System.out.println("list of users had an error adding all users");
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return listOfUsers;
	}

}
