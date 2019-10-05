package com.chenfei.community.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenfei.community.dto.NotificationDTO;
import com.chenfei.community.dto.PaginationDTO;
import com.chenfei.community.mapper.NotificationMapper;
import com.chenfei.community.mapper.UserMapper;
import com.chenfei.community.model.Notification;
import com.chenfei.community.model.User;

@Service
public class NotificationService {
	
	@Autowired
	NotificationMapper notificationMapper ;
	
	@Autowired
	UserMapper userMapper ;
	
	public PaginationDTO list(Long userId, Integer page, Integer size) {
		PaginationDTO paginationDTO = new PaginationDTO();
		Integer totalPage ;
		Integer totalCount = notificationMapper.countByUserId(userId);
		if(totalCount % size == 0) {
			totalPage = totalCount / size  ;
		}else {
			totalPage = totalCount /size + 1 ;
		}
	
		if(page < 1) {
			page = 1 ;
		}
		if(page > totalPage) {
			page = totalPage ;
		}
		
		paginationDTO.setPagination(totalPage, page);
		//分页   size*(page-1)
		Integer offset = size*(page-1) ;
		List<Notification> notifications = notificationMapper.queryByUserId(userId, offset, size);
		
		if(notifications.size() == 0) {
			return paginationDTO ;
		}
		List<Long> disUserId = notifications.stream().map(Notification :: getNotifier).distinct().collect(Collectors.toList());
		List<User> users = userMapper.getAllUsers(disUserId);
		Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User :: getId, user->user));
		List<NotificationDTO> notificationsDTOList = notifications.stream().map(notification->{
			NotificationDTO notificationDTO = new NotificationDTO();
			BeanUtils.copyProperties(notification, notificationDTO);
			notificationDTO.setUser(userMap.get(notification.getNotifier()));
			return notificationDTO ;
		}).collect(Collectors.toList());
		paginationDTO.setNotificationsDTO(notificationsDTOList);
		
		return paginationDTO ;
	}
		
}
