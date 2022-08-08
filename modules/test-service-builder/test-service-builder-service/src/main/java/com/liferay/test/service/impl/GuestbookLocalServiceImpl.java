/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.test.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.test.exception.GuestBookNameException;
import com.liferay.test.model.Guestbook;
import com.liferay.test.service.base.GuestbookLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.test.model.Guestbook",
	service = AopService.class
)
public class GuestbookLocalServiceImpl extends GuestbookLocalServiceBaseImpl {
	
	public List<Guestbook> getGuestbooks (long groupId) throws SystemException {
	    return guestbookPersistence.findByGroupId(groupId);
	}
	
	public List<Guestbook> getGuestbooks (long groupId, int start, int end)
	   throws SystemException {
	    return guestbookPersistence.findByGroupId(groupId, start, end);
	}
	
	protected void validate(String name) throws PortalException {
		if (Validator.isNotNull(name)) {
			throw new GuestBookNameException();
		}
	}
	
	public Guestbook add(long userId, String name, ServiceContext context) throws PortalException {
		
		Date now = new Date();
		long groupId = context.getScopeGroupId();		
		User user = userLocalService.getUser(userId);
		validate(name);
		long guestbookId = counterLocalService.increment();
		Guestbook guestbook = guestbookPersistence.create(guestbookId);
		
		guestbook.setUuid(context.getUuid());
		guestbook.setUserId(userId);
		guestbook.setGroupId(groupId);
		guestbook.setCompanyId(user.getCompanyId());
		guestbook.setUserName(user.getFullName());
		guestbook.setCreateDate(context.getCreateDate(now));
		guestbook.setModifiedDate(context.getModifiedDate(now));
		guestbook.setName(name);
		guestbook.setExpandoBridgeAttributes(context);

		guestbookPersistence.update(guestbook);

		return guestbook;
				
	}
}