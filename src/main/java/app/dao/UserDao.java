package app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import app.dao.entity.User;
import app.logic._0_credentialsSaver.model.ConfirmUser;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.UserBean;

@Service
@EnableCaching
public class UserDao {

	@Autowired
	private UserRepo userRepo;


	public void saveGazzettaCredentials(Credentials credentials, String username) {
//		String username = "mene"; //TROVA LO USER DALLA SESSION

		User user = userRepo.findByUsername(username);
		
		user.setGazzettaPassword(credentials.getPassword());
		user.setGazzettaUsername(credentials.getUsername());
		
		userRepo.save(user);
		
	}


	public UserBean createUser(UserBean userBean) {
		
		User existingUser = userRepo.findByUsername(userBean.getUsername());
		if (existingUser != null)
			return null;
		
		User ent = new User();
		ent.setFirstname(userBean.getFirstname());
		ent.setLastname(userBean.getLastname());
		ent.setEmail(userBean.getEmail());
		ent.setUsername(userBean.getUsername());
		ent.setPassword(userBean.getPassword());
		Integer rnd = userBean.hashCode();
		ent.setToBeConfirm(rnd);
		
		userRepo.save(ent);
		
		return userBean;
		
	}
	
	
	public Boolean confirmUser(ConfirmUser confirmUser) {
		String username = confirmUser.getUsername();
		
		User user = userRepo.findByUsername(username);

		if (user.getToBeConfirm().toString().equals(confirmUser.getRnd())) {
			user.setToBeConfirm(0);
			userRepo.save(user);
			return true;
		}
		else {
			return false;
		}
		
	}


	public UserBean login(Credentials credentials) {
		User dbUser = userRepo.findByUsername(credentials.getUsername());
		if (credentials.getPassword().equals(dbUser.getPassword())) {
			UserBean userBean = createUserBean(dbUser);
			return userBean;
		}
		return null;
	}


	private UserBean createUserBean(User ent) {
		UserBean userBean = new UserBean();
		userBean.setFirstname(ent.getFirstname());
		userBean.setLastname(ent.getLastname());
		userBean.setEmail(ent.getEmail());
		userBean.setUsername(ent.getUsername());
		userBean.setPassword(ent.getPassword());
		userBean.setGazzettaUsername(ent.getGazzettaUsername());
		return userBean;
		
	}

	public Credentials retrieveGazzettaCredentials(String username) {
		User user = userRepo.findByUsername(username);
		Credentials c = new Credentials();
		c.setUsername(user.getGazzettaUsername());
		c.setPassword(user.getGazzettaPassword());
		return c;
	}

	public User retrieveByUsername(String username) {
		User user = userRepo.findByUsername(username);
		
		return user;
	}
	

	



	
	

	
	
}
