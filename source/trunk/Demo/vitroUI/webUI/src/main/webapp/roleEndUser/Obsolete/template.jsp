<%@ page session='false' contentType="text/html;charset=UTF-8" import='java.util.*, presentation.webgui.vitroappservlet.*, vitro.vspEngine.service.geo.*, vitro.vspEngine.service.query.*' %>
<%@ page import='java.util.*, presentation.webgui.vitroappservlet.Common' %>
<%@ page import="vitro.vspEngine.logic.model.GatewayWithSmartNodes" %>
<%@ page import="vitro.vspEngine.service.engine.UserNode" %>

<%--
  ~ #--------------------------------------------------------------------------
  ~ # Copyright (c) 2013 VITRO FP7 Consortium.
  ~ # All rights reserved. This program and the accompanying materials
  ~ # are made available under the terms of the GNU Lesser Public License v3.0 which accompanies this distribution, and is available at
  ~ # http://www.gnu.org/licenses/lgpl-3.0.html
  ~ #
  ~ # Contributors:
  ~ #     Antoniou Thanasis (Research Academic Computer Technology Institute)
  ~ #     Paolo Medagliani (Thales Communications & Security)
  ~ #     D. Davide Lamanna (WLAB SRL)
  ~ #     Alessandro Leoni (WLAB SRL)
  ~ #     Francesco Ficarola (WLAB SRL)
  ~ #     Stefano Puglia (WLAB SRL)
  ~ #     Panos Trakadas (Technological Educational Institute of Chalkida)
  ~ #     Panagiotis Karkazis (Technological Educational Institute of Chalkida)
  ~ #     Andrea Kropp (Selex ES)
  ~ #     Kiriakos Georgouleas (Hellenic Aerospace Industry)
  ~ #     David Ferrer Figueroa (Telefonica Investigación y Desarrollo S.A.)
  ~ #
  ~ #--------------------------------------------------------------------------
  --%>

<html>
<head>
    <meta charset="utf-8">	
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/img/favicon2.ico" type="image/x-icon"/>

    <title>Template</title>
	<link href="<%=request.getContextPath()%>/css/bootstrap.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/vitrodemo.css" rel="stylesheet">

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
	
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/ico/favicon.png">
	<script type="text/javascript">
	$(document).ready(function(){
 	});     	
	</script>
	<script type="text/javascript">
	 $(document).ready(function(){
		$('#gwid').button();
		$('#gwid').click(function(){
				alert('hello'); 
				});
		$('#dashboardHomeButton').addClass("active");				
	});     	
	</script>
</head>
<body>    
    <!-- DDMenu -->
    <%= Common.printDDMenu(application.getRealPath("/"), request) %>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
			<!-- begin the side menu -->
			<%= Common.printSideMenu(application.getRealPath("/"), request) %>
			</div>
			<div class="span10">
<!-- begin the DDBody -->
	                            <%
								int i = 0;
                                boolean foundActuatingCapabilityGlobally = false;
                                HashMap<String, GatewayWithSmartNodes> infoGWHM = new HashMap<String, GatewayWithSmartNodes>();
                                UserNode ssUN = null;
                                try
                                {
                                    ssUN = (UserNode)(application.getAttribute("ssUN"));
                                    infoGWHM = ssUN.getGatewaysToSmartDevsHM();
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                Set<String> keysOfGIds = infoGWHM.keySet();
                                Iterator<String> itgwId = keysOfGIds.iterator();
                                
                                StringBuilder supportedCapabilitiesOnGwBld;
                                while(itgwId.hasNext())
                                {   
								    supportedCapabilitiesOnGwBld = new StringBuilder();
                                    supportedCapabilitiesOnGwBld.append("<b>Supported Capabilities on this WSI:</b><br/>");

                                    String currGwId = itgwId.next();
                                    String gateId = infoGWHM.get(currGwId).getId();
                                    String gateName = infoGWHM.get(currGwId).getName();

                                %>
								<button id="gwid" class="btn"><%=gateId %> <%=gateName%>  (<%=Integer.toString(infoGWHM.get(currGwId).getSmartNodesVec().size()) %> devices)
								</button>
								<% i++;
                            } %>
			</div>
		</div>
	</div>
    <%= Common.printFooter(request, application) %>
    <!-- end of footer -->
</body>
</html>
