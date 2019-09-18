package kacper.bestplaces.admin;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kacper.bestplaces.user.User;

public interface AdminService {

	 Page<User> findAll(Pageable pageable);
	 User findUserById(int id);
	 void updateUser(int id,int nrRoli,int active);
	 Page<User> findAllSearch(String param, Pageable pg);
	void saveAll(List<User> userList);
	void deleteUserById(int id);
}