package kacper.bestplaces.service;


import kacper.bestplaces.model.User;

public interface UserService {
	
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public void updateUserPassword(String newPassword, String email);
	public void resetUserPassword(String newPassword, String code);
	public void updateUserProfile(String newName, String newLastName, String newEmail, int id);
	public void updateUserActivation(int activeCode, String activationCode);
}