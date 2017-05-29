package com.fourhorizons.web.service.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fourhorizons.web.service.model.UserPoints;

public interface UserPointsDao extends PagingAndSortingRepository<UserPoints, Integer> {

}