<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<jsp:include page="/WEB-INF/view/client/layout/header.jsp">
    <jsp:param name="title" value="Smart Home" />
</jsp:include>

<jsp:include page="/WEB-INF/view/client/layout/banner.jsp"/>
<jsp:include page="/WEB-INF/view/client/layout/feature.jsp"/>

<jsp:include page="/WEB-INF/view/client/layout/footer.jsp" />
