package com.waterelephant.system.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.system.dto.SmsRoleDto;
import com.waterelephant.system.enums.RoleType;
import com.waterelephant.system.service.SmsRoleService;

@Service
public class SmsRoleServiceImpl implements SmsRoleService {

	@Override
	public List<SmsRoleDto> selectRoleList() {
		SmsRoleDto admin = new SmsRoleDto();
		admin.setRoleId(1);
		admin.setRoleType(RoleType.ADMIN.getRoleType());
		admin.setRoleName("管理员");
		
		SmsRoleDto user = new SmsRoleDto();
		user.setRoleId(2);//
		user.setRoleType(RoleType.USER.getRoleType());
		user.setRoleName("普通用户");
		
		return Arrays.asList(admin,user);
	}

	@Override
	public SmsRoleDto selectRoleById(Long roleId) {
		SmsRoleDto role = new SmsRoleDto();
		
		if(1 == roleId) {
			role.setRoleId(1);
			role.setRoleType(RoleType.ADMIN.getRoleType());
			role.setRoleName("管理员");
		} else {

			role.setRoleId(2);//
			role.setRoleType(RoleType.USER.getRoleType());
			role.setRoleName("普通用户");
		}
		return role;
	}

}
