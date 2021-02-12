package kacper.bestplaces.service;


import kacper.bestplaces.model.User;

public interface UserService {
	
	User findUserByEmail(String email);
	void saveUser(User user);
	void updateUserPassword(String newPassword, String email);
	void resetUserPassword(String newPassword, String code);
	void updateUserProfile(String newName, String newLastName, String newEmail, int id);
	void updateUserActivation(int activeCode, String activationCode);
}