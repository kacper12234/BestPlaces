package kacper.bestplaces.service;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kacper.bestplaces.model.User;

public interface AdminService {

	 Page<User> findAll(Pageable pageable);
	 User findUserById(int id);
	 void updateUser(int id,int nrRoli,int active);
	 Page<User> findAllSearch(String param, Pageable pg);
	void saveAll(List<User> userList);
	void deleteUserById(int id);
}