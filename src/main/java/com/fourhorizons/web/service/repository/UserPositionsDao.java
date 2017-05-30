package com.fourhorizons.web.service.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fourhorizons.web.service.model.UserPositions;

public interface UserPositionsDao extends PagingAndSortingRepository<UserPositions, Integer> {

}
