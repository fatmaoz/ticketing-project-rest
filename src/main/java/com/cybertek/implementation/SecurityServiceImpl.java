package com.cybertek.implementation;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.User;
import com.cybertek.entity.common.UserPrincipal;
import com.cybertek.mapper.MapperUtil;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.SecurityService;
import com.cybertek.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    //Which person will be bring from DB
    private UserService userService;
    private MapperUtil mapperUtil;

    public SecurityServiceImpl(UserService userService, MapperUtil mapperUtil) {
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO user = userService.findByUserName(username);
        if (user == null){//eger login page de yanlis username/password girilirse app crash olmasin diye koyuyuoruz
            throw new UsernameNotFoundException("This user doesnt exists");
        }

        //it is for data verification
        return new org.springframework.security.core.userdetails.User(user.getId().toString(),user.getPassWord(),listAuthorities(user));
    }

    @Override
    public User loadUser(String param) {

        UserDTO userDTO =userService.findByUserName(param);
        return mapperUtil.convert(userDTO,new User());
    }
//It is converting to what spring can understand for authority
    private Collection<? extends GrantedAuthority> listAuthorities(UserDTO user){
        List<GrantedAuthority> authorityList = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getDescription());
        authorityList.add(authority);

        return authorityList;
    }
}
