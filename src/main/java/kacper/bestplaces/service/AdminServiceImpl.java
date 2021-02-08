package kacper.bestplaces.service;

import java.util.List;

import kacper.bestplaces.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kacper.bestplaces.model.User;

@Service("adminService")
@Transactional
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {


    private final AdminRepository adminRepository;


    @Override
    public Page<User> findAll(Pageable pageable) {
        Page<User> userList = adminRepository.findAll(pageable);
        return userList;
    }

    @Override
    public User findUserById(int id) {
        User user = adminRepository.findUserById(id);
        return user;
    }

    @Override
    public void updateUser(int id, int nrRoli, int activity) {
        adminRepository.updateActivationUser(activity, id);
        adminRepository.updateRoleUser(nrRoli, id);
    }

    @Override
    public Page<User> findAllSearch(String param, Pageable pg) {
        Page<User> userList = adminRepository.findAllSearch(param, pg);
        return userList;
    }

    public void saveAll(List<User> userList) {
        adminRepository.saveAll(userList);
    }

    @Override
    public void deleteUserById(int id) {
        adminRepository.deleteUserFromUserRole(id);
        adminRepository.deleteUserFromUser(id);
    }

}