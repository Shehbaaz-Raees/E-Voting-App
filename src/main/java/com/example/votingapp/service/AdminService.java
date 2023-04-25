package com.example.votingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.votingapp.entity.Admin;
import com.example.votingapp.repository.AdminRepository;

@Service
public class AdminService {

    private AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public boolean addNew(Admin admin) {
		Admin entity = new Admin();
		entity.setUsername(admin.getUsername());
		entity.setPassword(admin.getPassword());
		entity.setEmail(admin.getEmail());
		entity.setPhone(admin.getPhone());
		adminRepository.save(entity);
		return true;
	}
    
    public Admin getUserByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public boolean isUserExists(String username) {
        return adminRepository.findByUsername(username) != null;
    }

    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }
}