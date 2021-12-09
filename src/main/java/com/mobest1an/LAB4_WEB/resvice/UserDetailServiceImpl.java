package com.mobest1an.LAB4_WEB.resvice;

import com.mobest1an.LAB4_WEB.model.Role;
import com.mobest1an.LAB4_WEB.model.Status;
import com.mobest1an.LAB4_WEB.model.User;
import com.mobest1an.LAB4_WEB.repository.ChecksRepository;
import com.mobest1an.LAB4_WEB.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UserDetailServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return SecurityUser.fromUser(user);
    }

    public User findByUsername(String username) {
        return usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean saveUser(User user) {
        User userFromDB = usersRepository.findByUsername(user.getUsername()).orElse(null);

        if (userFromDB != null) {
            return false;
        }

        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
        usersRepository.save(user);
        return true;
    }
}
