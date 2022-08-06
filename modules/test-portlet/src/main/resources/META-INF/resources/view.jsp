<%@ include file="/init.jsp" %>
<jsp:useBean id="entries" class="java.util.ArrayList" scope="request"/>

<h2>Guestbook</h2>

<liferay-ui:search-container>
    
    <liferay-ui:search-container-results results="<%= entries %>"/>
    
    <liferay-ui:search-container-row className="com.liferay.test.portlet.web.model.Entry" modelVar="entry">
        
        <liferay-ui:search-container-column-text property="name" />
        <liferay-ui:search-container-column-text property="message" />        
        
    </liferay-ui:search-container-row>
    
    <liferay-ui:search-iterator />    
    
</liferay-ui:search-container>

<aui:button-row cssClass="guestbook-buttons">
	
	<portlet:renderURL var="addEntryURL">
		<portlet:param name="mvcPath" value="/edit_entry.jsp" />
	</portlet:renderURL>
	
	<aui:button onClick="<%= addEntryURL.toString() %>" value="Add Entry"></aui:button>

</aui:button-row>




