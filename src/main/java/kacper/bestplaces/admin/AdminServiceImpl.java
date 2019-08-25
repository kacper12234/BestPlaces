package kacper.bestplaces.admin;



import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kacper.bestplaces.places.PlacesRepository;
import kacper.bestplaces.user.Role;
import kacper.bestplaces.user.RoleRepository;
import kacper.bestplaces.user.User;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

	private static final Logger LOG=LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private JpaContext jpaContext;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PlacesRepository placesRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Page<User> findAll(Pageable pageable) {
		Page<User> userList = adminRepository.findAll(pageable);
		return userList;
	}

	@Override
	public User findUserById(int id)
	{
		User user=adminRepository.findUserById(id);
		return user;
	}
	
	@Override
	public void updateUser(int id,int nrRoli,int activity)
	{
		adminRepository.updateActivationUser(activity, id);
		adminRepository.updateRoleUser(nrRoli, id);
	}
	
	@Override
	public Page<User> findAllSearch(String param, Pageable pg)
	{
		Page<User> userList=adminRepository.findAllSearch(param,pg);
		return userList;
	}
	
	@Override
	public void insertInBatch(List<User> userList)
	{
		EntityManager em=jpaContext.getEntityManagerByManagedType(User.class);
		for(int i=0;i<userList.size();i++)
		{
			User u=userList.get(i);
			Role role=roleRepository.findByRole("ROLE_USER");
			u.setRoles(new HashSet<Role>(Arrays.asList(role)));
			u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
			em.persist(u);
			if(i%50==0&&i>0)
			{
				em.flush();
				em.clear();
			}
		}
	}
	
	public void saveAll(List<User> userList)
	{
		adminRepository.saveAll(userList);
	}

	@Override
	public void deleteUserById(int id) {
		LOG.debug("[WYWOŁANIE >>> AdminServiceImpl.deleteUserFromUser > PARAMETR: "+id);
		adminRepository.deleteUserFromUserRole(id);
		adminRepository.deleteUserFromUser(id);
	}
	
	@Override
	public void deletePlace(String name) {
		long i=placesRepository.findByName(name).getId()+1;
		long count=placesRepository.count();
		File r=new File(System.getProperty("user.dir")+"/target/classes/static/images/"+placesRepository.findByName(name).getLink());
		adminRepository.deletePlace(name);
		r.delete();
		for(;i<=count;i++)
			adminRepository.updateId(i-1, i);
	}
}