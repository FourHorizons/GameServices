package com.fourhorizons.web.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.fourhorizons.web.service.model.UserInfo;

public interface UserInfoDao extends PagingAndSortingRepository<UserInfo, Integer> {

	@Query("select a from Person a where upper(a.name) like upper(:name)")
    List<UserInfo> listByName(@Param("name") String name);
}
