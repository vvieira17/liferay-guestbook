package com.liferay.test.portlet.web.portlet;

import com.liferay.test.portlet.web.constants.TestPortletKeys;
import com.liferay.test.portlet.web.model.Entry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;

/**
 * @author viniciusvieirameneses
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=treinamento",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Test",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + TestPortletKeys.TEST,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class TestPortlet extends MVCPortlet {
	
	@Override
		public void init() throws PortletException {
			System.out.println("INIT...");
			super.init();
		}
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		System.out.println("RENDER...");
				
		PortletPreferences prefs = renderRequest.getPreferences();
		String[] guestBookEntries = prefs.getValues("guestbook-entries", new String[1]);
		
		if (guestBookEntries != null) {
			List<Entry> entries = parse(guestBookEntries);
			renderRequest.setAttribute("entries", entries);
		}
		
		super.render(renderRequest, renderResponse);
	}
	
	private List<Entry> parse(String[] guestBookEntries) {
		List<Entry> entries = new ArrayList<Entry>();
		
		for (String entry : guestBookEntries) {
			if (entry != null) {
				String[] parts = entry.split("\\^", 2);
				Entry currentEntry = new Entry(parts[0], parts[1]);
				entries.add(currentEntry);
			}			
		}
		
		return entries;
	}
	
	public void addEntry(ActionRequest request, ActionResponse response) {
		
		try {
				
			String name = ParamUtil.getString(request, "name");
			String message = ParamUtil.getString(request, "message");
			String entry = name + "^" + message;
			
			PortletPreferences prefs = request.getPreferences();
			String[] guestBookEntriesArray = prefs.getValues("guestbook-entries", new String[1]);
			List<String> guestBookEntriesList = new ArrayList<String>();	
			
			if (guestBookEntriesArray != null) {
				guestBookEntriesList = new ArrayList<>(Arrays.asList(guestBookEntriesArray));
			}
			
			guestBookEntriesList.add(entry);
			
			guestBookEntriesArray = guestBookEntriesList.toArray(new String[guestBookEntriesList.size()]);
			
			prefs.setValues("guestbook-entries", guestBookEntriesArray);
			prefs.store();			
			
			
		} catch (ReadOnlyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		 
		
	}
}