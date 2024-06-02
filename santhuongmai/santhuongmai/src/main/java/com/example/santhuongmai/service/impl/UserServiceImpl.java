package com.example.santhuongmai.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.santhuongmai.entity.Blog;
import com.example.santhuongmai.entity.ERole;
import com.example.santhuongmai.entity.Image;
import com.example.santhuongmai.entity.Role;
import com.example.santhuongmai.entity.Tag;
import com.example.santhuongmai.entity.User;
import com.example.santhuongmai.exception.BadRequestException;
import com.example.santhuongmai.exception.NotFoundException;
import com.example.santhuongmai.model.request.ChangePasswordRequest;
import com.example.santhuongmai.model.request.CreateBlogRequest;
import com.example.santhuongmai.model.request.CreateUserRequest;
import com.example.santhuongmai.model.request.UpdateProfileRequest;
import com.example.santhuongmai.repository.RoleRepository;
import com.example.santhuongmai.repository.UserRepository;
import com.example.santhuongmai.service.UserService;
import com.example.santhuongmai.util.EmailUtil;
import com.example.santhuongmai.util.OtpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.MessagingException;



@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	  private OtpUtil otpUtil;
	  @Autowired
	  private EmailUtil emailUtil;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public List<User> getListuser() {
        // TODO Auto-generated method stub
        return userRepository.findAll(Sort.by("id").descending());
    }
    @Override
    public User getUser(long id){
    	User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Blog"));
        return user;
    }
    @Override
    public void register(CreateUserRequest request) {
        // TODO Auto-generated method stub
    	String verificationCode = otpUtil.generateOtp();
        try {
          emailUtil.sendOtpEmail(request.getEmail(), verificationCode);
        } catch (MessagingException e) {
          throw new RuntimeException("Unable to send otp please try again");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setVerificationCode(verificationCode);
        user.setOtpGeneratedTime(LocalDateTime.now());
        Set<String> strRoles = request.getRole();
          Set<Role> roles = new HashSet<>();
      
          if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
          } else {
            strRoles.forEach(role -> {
              switch (role) {
              case "admin":
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
      
                break;
              case "mod":
                Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);
      
                break;
              default:
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
              }
            });
          }
          user.setRoles(roles);
          userRepository.save(user);
    }

    public String verifyAccount(String email, String otp) {
      //System.out.println(otp);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (user.getVerificationCode().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
            LocalDateTime.now()).getSeconds() < (1 * 60 * 10)) {
          user.setEnabled(true);
          userRepository.save(user);
          return "<form style='text-align: center;'>" +
       "<div style='color: green; font-size: 36px;'>" +
       "Xác Thực OTP Thành Công<br>" +
       "<a href='http://localhost:4200/login' style='text-decoration: none; color: red;'>Đăng Nhập Ngay</a></div>" +
       "</form>";


}
        return "OTP Đã Hết Hạn Sử Dụng";
      }
    public String regenerateOtp(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again", e);
        }

        
        
        user.setVerificationCode(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 1 minute";
      }

    @Override
    public User getUserByUsername(String username) {
      // TODO Auto-generated method stub
      User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User"));
      return user;
    }

    @Override
    public User updateUser(UpdateProfileRequest request) {
      // TODO Auto-generated method stub
      User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User"));
      user.setFirstname(request.getFirstname());
      user.setLastname(request.getLastname());
      user.setEmail(request.getEmail());
      user.setCountry(request.getCountry());
      user.setState(request.getState());
      user.setAddress(request.getAddress());
      user.setPhone(request.getPhone());
//      //
//      Set<Role> roles = new HashSet<>();
//      for(Long roleId : request.getRoles()){
//    	  Role role = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Not Found Tag"));
//    	  roles.add(role);
//      }
//      user.setRoles(roles);
//      //
      userRepository.save(user);
      return user;
    }
    @Override
    public void changePassword(ChangePasswordRequest request) {
        // Tìm kiếm người dùng
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("Not Found User"));
        // So sánh mật khẩu cũ
        if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Old Password Not Same");
        }
        // Mã hóa và cập nhật mật khẩu mới
        user.setPassword(encoder.encode(request.getNewPassword()));        
        // Lưu người dùng đã được cập nhật
        userRepository.save(user);
    }

    @Override
    public void deleleUser(long id) {
        // TODO Auto-generated method stub
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found USER"));
        user.getRoles().remove(this);
        userRepository.delete(user);
    }

    //
    @Override
    public User updatesUser(long id, CreateUserRequest request) {
        // TODO Auto-generated method stub
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found User"));
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setCountry(request.getCountry());
        user.setState(request.getState());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        Set<Role> roles = new HashSet<>();
      for(Long roleId : request.getRoles()){
    	  Role role = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Not Found Tag"));
    	  roles.add(role);
      }
      user.setRoles(roles);
        userRepository.save(user);
        return user;
    }


    
}
