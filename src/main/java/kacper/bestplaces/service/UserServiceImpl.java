package kacper.bestplaces.service;

import java.util.Arrays;
import java.util.HashSet;

import kacper.bestplaces.model.Role;
import kacper.bestplaces.repository.RoleRepository;
import kacper.bestplaces.model.User;
import kacper.bestplaces.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(0);
		
		Role role = roleRepository.findByRole("ROLE_USER");
		user.setRoles(new HashSet<>(Arrays.asList(role)));
		
		userRepository.save(user);
		
	}

	@Override
	public void updateUserPassword(String newPassword, String email) {
		// TODO Auto-generated method stub
		userRepository.updateUserPassword(bCryptPasswordEncoder.encode(newPassword), email);
	}

	@Override
	public void updateUserProfile(String newName, String newLastName, String newEmail, int id) {
		userRepository.updateUserProfile(newName, newLastName, newEmail, id);
	}

	@Override
	public void updateUserActivation(int activeCode, String activationCode) {
		userRepository.updateActivation(activeCode, activationCode);
	}

	@Override
	public void resetUserPassword(String newPassword, String code) {
		// TODO Auto-generated method stub
		userRepository.resetUserPassword(bCryptPasswordEncoder.encode(newPassword), code);
	}

	
}
