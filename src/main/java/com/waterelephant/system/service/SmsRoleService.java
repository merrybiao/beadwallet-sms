package com.waterelephant.system.service;

import java.util.List;

import com.waterelephant.system.dto.SmsRoleDto;

public interface SmsRoleService {

	List<SmsRoleDto> selectRoleList();

	SmsRoleDto selectRoleById(Long roleId);

}
