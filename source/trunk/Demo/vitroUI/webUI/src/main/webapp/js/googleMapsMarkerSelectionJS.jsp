<%@page session='false' contentType='application/x-javascript' language="java" %>
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

// global constants
    var radiansPerDegree = Math.PI / 180.0;
    var degreesPerRadian = 180.0 / Math.PI;
    var earthRadiusMeters = 6367460.0;
    var metersPerDegree = 2.0 * Math.PI * earthRadiusMeters / 360.0;
    var metersPerKm = 1000.0;
    var meters2PerHectare = 10000.0;
    var feetPerMeter = 3.2808399;
    var feetPerMile = 5280.0;
    var acresPerMile2 = 640;

    var MarkerSelection = {
        points: [],                     // points are the latlong points of the selection area
        allnodeMarkers: [],                 // replacing pointsrand are the markers representing the nodes/sensors/items to be selected (<-- should be merged with the markers of the AllMapItems object)
        allnodeEnabledStatus: [],
        allnodeSynchedStatus: [],
        selectedMarkers: [],              // is a table with the selected markers (in the defined polygon region)!
        singleMarkersSelected: [],     // is changed when a user right clicks or double clicks on a marker. New: Can be a set of distinct markers, not in a region. Double click resets it to a single marker. Second right click on marker de-selects it (removes it from table)
        routeMarkers: [],               // the markers of the selection borderline - not valid nodes/sensors etc
        lines: [],                       // unused?
        lineColor:'#6E6E98' ,//'#ff0000';
        fillColor:'#00FF00',
        lineWidth: 4,
        polygon: null,
        routePath: null,                    //????
        routePath2: null, //???             //????
//      ShowHideONOFF: 0,
        selectedCapability: 'invalid',
        areaSpan: null,   // to show the m^2 of the selected surface
        areaSpanKm: null, // to show the km^2 of the selected surface
//        selectedMarkersSpan: null,
        singleMarkersSelectedSpan: null
    };

    //
    //var points = [];
    //var pointsrand = [];
    ////var areaDiv = document.getElementById('area');
    ////var areaDivkm = document.getElementById('areakm');
    //var randomMarkers = new Array(0);
    //var routeMarkers = new Array(0);
    //var lines = [];
    //var lineColor = '#6E6E98' ;//'#ff0000';
    //var fillColor = '#00FF00';
    //var lineWidth = 4;
    //var polygon;
    //var routePath;
    //var routePath2;
    //var ShowHideONOFF = 0;

    MarkerSelection.SwitchSelectAll = function(pMap) {
        if ( (MarkerSelection.selectedMarkers && MarkerSelection.selectedMarkers.length > 0)  || ( MarkerSelection.singleMarkersSelected && MarkerSelection.singleMarkersSelected.length > 0)    )
        {
            MarkerSelection.Clean(pMap);
        }
        else {
            // TODO: code repetition (with searchPointsAdd()). Should be optimized
            //new changed to singleMarkersSelected
            MarkerSelection.selectedMarkers.length = 0;
            MarkerSelection.selectedMarkers.innerHTML = '';
            //new:
            MarkerSelection.selectedMarkers =[];
            MarkerSelection.singleMarkersSelected.length = 0;
            if(MarkerSelection.singleMarkersSelectedSpan!=null)
                MarkerSelection.singleMarkersSelectedSpan.innerHTML = '';
            MarkerSelection.singleMarkersSelected =[];

            for (var i = 0; i < MarkerSelection.allnodeMarkers.length; ++i) {
                var marker = MarkerSelection.allnodeMarkers[i];  // todo: maybe something more efficient than erasing the entire table and rewriting it?
                if (!(marker.getMap() == null)) // we are interested only on the displayed markers!
                {
                    MarkerSelection.singleMarkersSelected.push(marker);
                    drawSelectedIcon(marker, MarkerSelection.selectedCapability);
                }
            }
            MarkerSelection.updateSelectionInSpan();
        }
    }

    MarkerSelection.SelectionTotalLength = function ()
    {
        var lengthRegionSel = 0;
        var lengthDistinctSel =0;
        if (MarkerSelection.selectedMarkers && MarkerSelection.selectedMarkers.length > 0)
        {
            lengthRegionSel =MarkerSelection.selectedMarkers.length;
        }
        if ( MarkerSelection.singleMarkersSelected && MarkerSelection.singleMarkersSelected.length > 0)
        {
            lengthDistinctSel =   MarkerSelection.singleMarkersSelected.length;
         }
        return    (lengthRegionSel +   lengthDistinctSel);

    }


    // will always clear the span and will show the selection in presentable form (abbreviated after 5 entries)
    MarkerSelection.updateSelectionInSpan = function ()
    {
        var selectionInSpan = 0;
        if(MarkerSelection.singleMarkersSelectedSpan == null){
            //console.log('DEBUG: singleMarkers is null');
            return;
        }
        MarkerSelection.singleMarkersSelectedSpan.innerHTML = '';
        if(MarkerSelection.SelectionTotalLength() > 0 )
        {
            for (var i = 0;MarkerSelection.singleMarkersSelected && i < MarkerSelection.singleMarkersSelected.length; ++i) {
                if (selectionInSpan  < 5)
                {
                    MarkerSelection.singleMarkersSelectedSpan.innerHTML += MarkerSelection.singleMarkersSelected[i].getTitle() + ':' + MarkerSelection.selectedCapability + ', ';
                    selectionInSpan++;
                }
            }
            for (var i = 0; MarkerSelection.selectedMarkers && i < MarkerSelection.selectedMarkers.length; ++i) {
                if (selectionInSpan  < 5)
                {
                    MarkerSelection.singleMarkersSelectedSpan.innerHTML += MarkerSelection.selectedMarkers[i].getTitle() + ':' + MarkerSelection.selectedCapability + ', ';
                    selectionInSpan++;
                }
            }

            var strTemp = MarkerSelection.singleMarkersSelectedSpan.innerHTML;
            MarkerSelection.singleMarkersSelectedSpan.innerHTML = strTemp.replace(/(,\s*$)/g, '');

            if (MarkerSelection.SelectionTotalLength() > 5)
                MarkerSelection.singleMarkersSelectedSpan.innerHTML += ' ...and&nbsp;' + (MarkerSelection.SelectionTotalLength() - 5).toString() + '&nbsp;more.'
        }
    }

    //aux
    function drawAsUnselectedIcon(pMarker, pCap) {
        if(markerIsDisabled(pMarker) && markerStatusIsSynched(pMarker) ) {
            drawDisabledIcon(pMarker,pCap);
        }else {
            drawDefaultUnselectedIcon(pMarker, pCap);
        }
    }

    function markerIsDisabled(pMarker) {
        var retVal = false;
        if (MarkerSelection.allnodeMarkers) {
            for (var i = 0; i < MarkerSelection.allnodeMarkers.length; ++i) {
                if(pMarker ==  MarkerSelection.allnodeMarkers[i])
                {
                    //console.log('Found match is disabled()!' + MarkerSelection.allnodeEnabledStatus[i] );
                    if(MarkerSelection.allnodeEnabledStatus!=null && MarkerSelection.allnodeEnabledStatus[i] == '0'){
                        //console.log('Found match is TRUE DISABLED!');
                        retVal = true;
                    }
                    break;
                }
            }
        }
        return retVal;
    }

    function markerStatusIsSynched(pMarker) {
        var retVal = false;
        if (MarkerSelection.allnodeMarkers) {
            for (var i = 0; i < MarkerSelection.allnodeMarkers.length; ++i) {
                if(pMarker ==  MarkerSelection.allnodeMarkers[i])
                {
                    //console.log('Found match is synched()!' + MarkerSelection.allnodeSynchedStatus[i]);
                    if(MarkerSelection.allnodeSynchedStatus!=null && MarkerSelection.allnodeSynchedStatus[i] == '1'){
                        retVal = true;
                        //console.log('Found match is TRUE SYNCHED!');
                    }
                        break;
                }
            }
        }
        return retVal;
    }
    // end of aux

    MarkerSelection.Clean = function(pMap) {
        if(MarkerSelection.areaSpan!=null)
            MarkerSelection.areaSpan.innerHTML = '0 m&sup2;';
        if(MarkerSelection.areaSpanKm!=null)
            MarkerSelection.areaSpanKm.innerHTML = '0 km&sup2;';
        //MarkerSelection.selectedMarkersSpan.innerHTML = '';
        //new:
        if(MarkerSelection.singleMarkersSelectedSpan!=null)
            MarkerSelection.singleMarkersSelectedSpan.innerHTML = '';

        if (MarkerSelection.selectedMarkers) {
            for (var i = 0; i < MarkerSelection.selectedMarkers.length; ++i) {
                //MarkerSelection.selectedMarkers[i].setMap(null)
                drawAsUnselectedIcon(MarkerSelection.selectedMarkers[i], MarkerSelection.selectedCapability);

            }
        }
        //new:
        if (MarkerSelection.singleMarkersSelected) {
            for (var i = 0; i < MarkerSelection.singleMarkersSelected.length; ++i) {
                drawAsUnselectedIcon(MarkerSelection.singleMarkersSelected[i], MarkerSelection.selectedCapability);
            }
        }

        if (MarkerSelection.routeMarkers) {
            for (var i = 0; i < MarkerSelection.routeMarkers.length; ++i) {
                MarkerSelection.routeMarkers[i].setMap(null)
            }
        }
        if (! (MarkerSelection.routePath == undefined)) {
            MarkerSelection.routePath.setMap(null)
        }
        if (! (MarkerSelection.routePath2 == undefined)) {
            MarkerSelection.routePath2.setMap(null)
        }
        if (! (MarkerSelection.polygon == undefined)) {
            MarkerSelection.polygon.setMap(null)
        }

//        if (! (MarkerSelection.singleMarkerSelected == undefined)) {
//            drawAsUnselectedIcon(MarkerSelection.singleMarkerSelected, MarkerSelection.selectedCapability);
//            MarkerSelection.singleMarkerSelected = null;
//        }

        MarkerSelection.routeMarkers.length = 0;
        MarkerSelection.routeMarkers = [];
        MarkerSelection.routePath = null;
        MarkerSelection.routePath2 = null;
        MarkerSelection.polygon = null;
        MarkerSelection.points.length = 0;
        MarkerSelection.points = []
        MarkerSelection.selectedMarkers.length = 0;
        MarkerSelection.selectedMarkers = [];

        MarkerSelection.singleMarkersSelected.length = 0;
        MarkerSelection.singleMarkersSelected = [];
    }
    //function initialize() {
    //	var latlng = new google.maps.LatLng(34.12016327332972, -118.0456525824976);
    //	var myOptions = {
    //		zoom: 16,
    //		center: latlng,
    //		mapTypeId: google.maps.MapTypeId.HYBRID,
    //		draggableCursor: 'crosshair',
    //		mapTypeControlOptions: {
    //			style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
    //		}
    //	};
    //
    //	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    //	google.maps.event.addListener(map, 'click', mapclick);
    //	areaDiv.innerHTML = '0 m&sup2;';
    //	areaDivkm.innerHTML = '0 km&sup2;';
    //	Display();
    //	setTimeout('Regen()', 200)
    //}


    //function Regen() {
    //	var bounds = map.getBounds();
    //	var southWest = bounds.getSouthWest();
    //	var northEast = bounds.getNorthEast();
    //	var lngSpan = northEast.lng() - southWest.lng();
    //	var latSpan = northEast.lat() - southWest.lat();
    //	pointsrand = [];
    //	for (var i = 0; i < 100; i++) {
    //		var point = new google.maps.LatLng(southWest.lat() + latSpan * Math.random(), southWest.lng() + lngSpan * Math.random());
    //		pointsrand.push(point)
    //	}
    //}

    //function mapclick(event) {
    //	points.push(event.latLng);
    //	ShowHideONOFF = 0;
    //	Display()
    //}

    /**
    * if there is a selection polygon defined this function will push in the MarkerSelection.selectedMarkers table,
    * the markers with pointsrand[] belonging to the polygon.
    * @param pMap
    * @constructor
    */
    function SearchPointsAdd(pMap) {
        if (! (MarkerSelection.polygon == undefined)) {
            //also remove them from the distinct selection if there is a match!
            //tmpdupicateMarkersIndexes =  new Array();
            tmpdupicateMarkersIndexes = [];


            if (MarkerSelection.selectedMarkers) {
                for (var i = 0; i < MarkerSelection.selectedMarkers.length; ++i) {
                    drawAsUnselectedIcon(MarkerSelection.selectedMarkers[i], MarkerSelection.selectedCapability);
                    var foundMatchInDistinctSelection = false;
                    for (var j = 0; !foundMatchInDistinctSelection && j < MarkerSelection.singleMarkersSelected.length; ++j) {
                        if(MarkerSelection.singleMarkersSelected[j] ==  MarkerSelection.selectedMarkers[i] )
                        {
                            foundMatchInDistinctSelection = true;
                            tmpdupicateMarkersIndexes.push(j); //push the index of the duplicate (to be purged) from the singleMarkersSelected array
                        }
                    }
                    //MarkerSelection.selectedMarkers[i].setMap(null)            // we hide the existing, but won't delete them (?)
                }
                if(tmpdupicateMarkersIndexes && tmpdupicateMarkersIndexes.length > 0) {
                    //tmpNonDupicateMarkers =  new Array();
                    tmpNonDupicateMarkers = [];
                    for (var i = 0; i < MarkerSelection.singleMarkersSelected.length; ++i) {
                        var tmpMarker = MarkerSelection.singleMarkersSelected[i];
                        var indexExistsInDuplicatesList = false;
                        for (var j = 0; !indexExistsInDuplicatesList &&  j < tmpdupicateMarkersIndexes.length; ++j) {
                            if(tmpdupicateMarkersIndexes[j] == i)
                            {
                                indexExistsInDuplicatesList = true;
                            }
                        }
                        if(!  indexExistsInDuplicatesList)
                        {
                            tmpNonDupicateMarkers.push(tmpMarker);
                        }
                    }
                    MarkerSelection.singleMarkersSelected = [];
                    MarkerSelection.singleMarkersSelected.length = 0;
                    MarkerSelection.singleMarkersSelected = tmpNonDupicateMarkers;
                }

            }
            //MarkerSelection.selectedMarkers = new Array();
            MarkerSelection.selectedMarkers.length = 0;
            // new: todo change this so as to append to the span (to the distinct selection.... to test: if they should be exclusive in the ui)
            if(MarkerSelection.singleMarkersSelectedSpan!=null)
                MarkerSelection.singleMarkersSelectedSpan.innerHTML = '';
            tmpdupicateMarkersIndexes = [];
            for (var i = 0; i < MarkerSelection.allnodeMarkers.length; ++i) {
                if (MarkerSelection.points.length > 2 && MarkerSelection.polygon.containsLatLng(MarkerSelection.allnodeMarkers[i].getPosition())) {
                    //var marker = placeMarkerred(MarkerSelection.pointsrand[i], i, pMap);      // this creates a NEW marker. We don't want that!
                    //var marker =
                    //if(doesNotContain(MarkerSelection.selectedMarkers, MarkerSelection.allnodeMarkers[i]))
                    var marker = MarkerSelection.allnodeMarkers[i];  // todo: maybe something more efficient than erasing the entire table and rewriting it?
                    if (!(marker.getMap() == null)) // we are interested only on the displayed markers!
                    {
                        MarkerSelection.selectedMarkers.push(marker);         // a marker pushed in the region Selection List. OVERRIDES the one in the distinct selection list
                                                                              // so the duplicate markers in the distinct selection list should be trimmed! (similaro to code above at the start of the method)
                        drawSelectedIcon(marker, MarkerSelection.selectedCapability);
                        var foundMatchInDistinctSelection = false;
                        for (var j = 0; !foundMatchInDistinctSelection && j < MarkerSelection.singleMarkersSelected.length; ++j) {
                            if(MarkerSelection.singleMarkersSelected[j] == marker )
                            {
                                foundMatchInDistinctSelection = true;
                                tmpdupicateMarkersIndexes.push(j); //push the index of the duplicate (to be purged) from the singleMarkersSelected array
                            }
                        }
                    }
                }
            }      //end for loop

            if( MarkerSelection.singleMarkersSelected &&  MarkerSelection.singleMarkersSelected.length > 0) {
                tmpNonDupicateMarkers = [];
                for (var i = 0; i < MarkerSelection.singleMarkersSelected.length; ++i) {
                    var tmpMarker = MarkerSelection.singleMarkersSelected[i];
                    var indexExistsInDuplicatesList = false;
                    if(tmpdupicateMarkersIndexes && tmpdupicateMarkersIndexes.length > 0) {
                        for (var j = 0; !indexExistsInDuplicatesList &&  j < tmpdupicateMarkersIndexes.length; ++j) {
                            if(tmpdupicateMarkersIndexes[j] == i)
                            {
                                indexExistsInDuplicatesList = true;
                            }
                        }
                    }
                    if(!  indexExistsInDuplicatesList && !(tmpMarker.getMap() == null) )   //AND IS DISPLAYED!
                    {
                        tmpNonDupicateMarkers.push(tmpMarker);
                        //also draw it selected(helps with change of capability
                        drawSelectedIcon(tmpMarker, MarkerSelection.selectedCapability);
                    }
                }
                MarkerSelection.singleMarkersSelected = [];
                MarkerSelection.singleMarkersSelected.length = 0;
                MarkerSelection.singleMarkersSelected = tmpNonDupicateMarkers;
            }
            MarkerSelection.updateSelectionInSpan();
        }
        else {
            MarkerSelection.selectedMarkers.length = 0;
            // if(MarkerSelection.singleMarkersSelectedSpan!=null)
            //      MarkerSelection.singleMarkersSelectedSpan.innerHTML = '';
            tmpSelectedDisplayedMarkers = [];
            for (var i = 0; i < MarkerSelection.singleMarkersSelected.length; ++i) {
                var tmpMarker = MarkerSelection.singleMarkersSelected[i];
                if(!(tmpMarker.getMap() == null) )   //IS DISPLAYED!
                {
                    tmpSelectedDisplayedMarkers.push(tmpMarker);
                    //also draw it selected(helps with change of capability
                    drawSelectedIcon(tmpMarker, MarkerSelection.selectedCapability);
                }
            }
            MarkerSelection.singleMarkersSelected = [];
            MarkerSelection.singleMarkersSelected.length = 0;
            MarkerSelection.singleMarkersSelected = tmpSelectedDisplayedMarkers;
            MarkerSelection.updateSelectionInSpan();
        }
    }


    MarkerSelection.trimSingleMarkerSelected = function(dIndex) {
        if(dIndex >=0 && MarkerSelection.singleMarkersSelected && MarkerSelection.singleMarkersSelected.length > dIndex) {
            tmpWithoutTheTrimmedMarker = [];
            for (var i = 0; i < MarkerSelection.singleMarkersSelected.length; ++i) {
                var tmpMarker = MarkerSelection.singleMarkersSelected[i];
                if(i!=dIndex)
                {
                    tmpWithoutTheTrimmedMarker.push(tmpMarker);
                }
                else {
                    drawAsUnselectedIcon(tmpMarker, MarkerSelection.selectedCapability)
                }
            }
            MarkerSelection.singleMarkersSelected = [];
            MarkerSelection.singleMarkersSelected.length = 0;
            MarkerSelection.singleMarkersSelected = tmpWithoutTheTrimmedMarker;
        }
    }

    MarkerSelection.trimRegionMarkerSelected = function(dIndex) {
        if(dIndex >=0 && MarkerSelection.selectedMarkers && MarkerSelection.selectedMarkers.length > dIndex) {
            tmpWithoutTheTrimmedMarker = [];
            for (var i = 0; i < MarkerSelection.selectedMarkers.length; ++i) {
                var tmpMarker = MarkerSelection.selectedMarkers[i];
                if(i!=dIndex)
                {
                    tmpWithoutTheTrimmedMarker.push(tmpMarker);
                }
                else {
                    drawAsUnselectedIcon(tmpMarker, MarkerSelection.selectedCapability)
                }
            }
            MarkerSelection.selectedMarkers = [];
            MarkerSelection.selectedMarkers.length = 0;
            MarkerSelection.selectedMarkers = tmpWithoutTheTrimmedMarker;
        }
    }


    MarkerSelection.Display = function(pMap) {
        if (MarkerSelection.routeMarkers) {
            //for (i in MarkerSelection.routeMarkers) {
            for (var i = 0; i < MarkerSelection.routeMarkers.length; ++i) {
                MarkerSelection.routeMarkers[i].setMap(null)
            }
        }
        if (! (MarkerSelection.routePath == undefined)) {
            MarkerSelection.routePath.setMap(null)
        }
        if (! (MarkerSelection.routePath2 == undefined)) {
            MarkerSelection.routePath2.setMap(null)
        }
        if (! (MarkerSelection.polygon == undefined)) {
            MarkerSelection.polygon.setMap(null)
        }

        MarkerSelection.routePath = new google.maps.Polyline({
            path: MarkerSelection.points,
            strokeColor: MarkerSelection.lineColor,
            strokeOpacity: 1.0,
            strokeWeight: MarkerSelection.lineWidth,
            geodesic: true
        });
        MarkerSelection.routePath.setMap(pMap);
        if (MarkerSelection.points.length > 2) {
            var points2 = [MarkerSelection.points[0], MarkerSelection.points[MarkerSelection.points.length - 1]];
            MarkerSelection.routePath2 = new google.maps.Polyline({
                path: points2,
                strokeColor: MarkerSelection.lineColor,
                strokeOpacity: 1.0,
                strokeWeight: MarkerSelection.lineWidth,
                geodesic: true
            });
            MarkerSelection.routePath2.setMap(pMap);
            MarkerSelection.polygon = new google.maps.Polygon({
                paths: MarkerSelection.points,
                strokeColor: "#FF0000",
                strokeOpacity: 1,
                strokeWeight: 1,
                fillColor: MarkerSelection.fillColor,
                fillOpacity: 0.5
            });
            MarkerSelection.polygon.setMap(pMap);
            if(MarkerSelection.areaSpan!=null)
                MarkerSelection.areaSpan.innerHTML = '&nbsp;';
            if(MarkerSelection.areaSpanKm!=null)
                MarkerSelection.areaSpanKm.innerHTML = '&nbsp;';
            var areaMeters2 = SphericalPolygonAreaMeters2(MarkerSelection.points);
            if (areaMeters2 < 1000000.0)
                areaMeters2 = PlanarPolygonAreaMeters2(MarkerSelection.points);
            if(MarkerSelection.areaSpan!=null)
                MarkerSelection.areaSpan.innerHTML = Areas(areaMeters2);
            if(MarkerSelection.areaSpanKm!=null)
                MarkerSelection.areaSpanKm.innerHTML = Areaskm(areaMeters2)
        }
        MarkerSelection.lines = [];
        MarkerSelection.routeMarkers = new Array(0);
        for (var i = 0; i < MarkerSelection.points.length; ++i) {
            var marker = placeMarker(MarkerSelection.points[i], i, pMap);
            MarkerSelection.routeMarkers.push(marker);
            marker.setMap(pMap)
        }
        SearchPointsAdd(pMap)
    }


    MarkerSelection.DeleteLastPoint = function(pMap) {
        if (MarkerSelection.points.length > 0)
            MarkerSelection.points.length--;
        MarkerSelection.Display(pMap)
    }


    function placeMarker(location, number, pMap) {
        var image = new google.maps.MarkerImage('<%=request.getContextPath()%>/img/marker004c-icon32.png', new google.maps.Size(20, 34), new google.maps.Point(0, 0), new google.maps.Point(9, 33));
        var shadow = new google.maps.MarkerImage('<%=request.getContextPath()%>/img/markershadow004-icon32.png', new google.maps.Size(28, 22), new google.maps.Point(0, 0), new google.maps.Point(1, 22));
        var marker = new google.maps.Marker({
            position: location,
            map: pMap,
            shadow: shadow,
            icon: image,
            draggable: true
        });
        google.maps.event.addListener(marker, 'dragend', function (event) {
            MarkerSelection.points[number] = event.latLng;
            MarkerSelection.Display(pMap)
        });
        return marker
    }


    function placeMarkerred(location, number, pMap) {
        var image = new google.maps.MarkerImage('<%=request.getContextPath()%>/img/marker004c-icon32.png', new google.maps.Size(20, 34), new google.maps.Point(0, 0), new google.maps.Point(9, 33));
        var shadow = new google.maps.MarkerImage('<%=request.getContextPath()%>/img/markershadow-icon32.png', new google.maps.Size(28, 22), new google.maps.Point(0, 0), new google.maps.Point(1, 22));
        var marker = new google.maps.Marker({
            position: location,
            map: pMap,
            shadow: shadow,
            icon: image,
            draggable: false
        });
        //	google.maps.event.addListener(marker, 'dragend', function (event) {
        //        MarkerSelection.points[number] = event.latLng;
        //		Display(pMap)
        //	});
        return marker
    }

    function GreatCirclePoints(p1, p2) {
        var maxDistanceMeters = 200000.0;
        var ps = [];
        if (p1.distanceFrom(p2) <= maxDistanceMeters) {
            ps.push(p1);
            ps.push(p2)
        } else {
            var theta1 = p1.lng() * radiansPerDegree;
            var phi1 = (90.0 - p1.lat()) * radiansPerDegree;
            var x1 = earthRadiusMeters * Math.cos(theta1) * Math.sin(phi1);
            var y1 = earthRadiusMeters * Math.sin(theta1) * Math.sin(phi1);
            var z1 = earthRadiusMeters * Math.cos(phi1);
            var theta2 = p2.lng() * radiansPerDegree;
            var phi2 = (90.0 - p2.lat()) * radiansPerDegree;
            var x2 = earthRadiusMeters * Math.cos(theta2) * Math.sin(phi2);
            var y2 = earthRadiusMeters * Math.sin(theta2) * Math.sin(phi2);
            var z2 = earthRadiusMeters * Math.cos(phi2);
            var x3 = (x1 + x2) / 2.0;
            var y3 = (y1 + y2) / 2.0;
            var z3 = (z1 + z2) / 2.0;
            var r3 = Math.sqrt(x3 * x3 + y3 * y3 + z3 * z3);
            var theta3 = Math.atan2(y3, x3);
            var phi3 = Math.acos(z3 / r3);
            var p3 = new GLatLng(90.0 - phi3 * degreesPerRadian, theta3 * degreesPerRadian);
            var s1 = GreatCirclePoints(p1, p3);
            var s2 = GreatCirclePoints(p3, p2);
            for (var i = 0; i < s1.length; ++i) ps.push(s1[i]);
            for (var i = 1; i < s2.length; ++i) ps.push(s2[i])
        }
        return ps
    }


//    function ShowHide(pMap) {
//        if (MarkerSelection.ShowHideONOFF == 0) {
//            MarkerSelection.ShowHideONOFF = 1;
//            for (var i = 0; i < MarkerSelection.allnodeMarkers.length; ++i) {
//                //var marker = placeMarkerred(MarkerSelection.pointsrand[i], i, pMap); // TODO: replace this inefficient code if we use this method to clear the map.
//                MarkerSelection.selectedMarkers.push(marker);
//                marker.setMap(pMap)
//            }
//        } else {
//            MarkerSelection.ShowHideONOFF = 0;
//            for (var i = 0; i < MarkerSelection.allnodeMarkers.length; ++i) {
//                MarkerSelection.selectedMarkers[i].setMap(null)
//            }
//            MarkerSelection.Display(pMap)
//        }
//    }


    function Areas(areaMeters2) {
        var areaHectares = areaMeters2 / meters2PerHectare;
        var areaKm2 = areaMeters2 / metersPerKm / metersPerKm;
        var areaFeet2 = areaMeters2 * feetPerMeter * feetPerMeter;
        var areaMiles2 = areaFeet2 / feetPerMile / feetPerMile;
        var areaAcres = areaMiles2 * acresPerMile2;
        return areaMeters2 + ' m&sup2; '
    }


    function Areaskm(areaMeters2) {
        var areaHectares = areaMeters2 / meters2PerHectare;
        var areaKm2 = areaMeters2 / metersPerKm / metersPerKm;
        var areaFeet2 = areaMeters2 * feetPerMeter * feetPerMeter;
        var areaMiles2 = areaFeet2 / feetPerMile / feetPerMile;
        var areaAcres = areaMiles2 * acresPerMile2;
        return areaKm2 + ' km&sup2; '
    }


    function SphericalPolygonAreaMeters2(points) {
        var totalAngle = 0.0;
        for (i = 0; i < points.length; ++i) {
            var j = (i + 1) % points.length;
            var k = (i + 2) % points.length;
            totalAngle += Angle(points[i], points[j], points[k])
        }
        var planarTotalAngle = (points.length - 2) * 180.0;
        var sphericalExcess = totalAngle - planarTotalAngle;
        if (sphericalExcess > 420.0) {
            totalAngle = points.length * 360.0 - totalAngle;
            sphericalExcess = totalAngle - planarTotalAngle
        } else if (sphericalExcess > 300.0 && sphericalExcess < 420.0) {
            sphericalExcess = Math.abs(360.0 - sphericalExcess)
        }
        return sphericalExcess * radiansPerDegree * earthRadiusMeters * earthRadiusMeters
    }


    function PlanarPolygonAreaMeters2(points) {
        var a = 0.0;
        for (var i = 0; i < points.length; ++i) {
            var j = (i + 1) % points.length;
            var xi = points[i].lng() * metersPerDegree * Math.cos(points[i].lat() * radiansPerDegree);
            var yi = points[i].lat() * metersPerDegree;
            var xj = points[j].lng() * metersPerDegree * Math.cos(points[j].lat() * radiansPerDegree);
            var yj = points[j].lat() * metersPerDegree;
            a += xi * yj - xj * yi
        }
        return Math.abs(a / 2.0)
    }


    function Angle(p1, p2, p3) {
        var bearing21 = Bearing(p2, p1);
        var bearing23 = Bearing(p2, p3);
        var angle = bearing21 - bearing23;
        if (angle < 0.0)
            angle += 360.0;
        return angle
    }


    function Bearing(from, to) {
        var lat1 = from.lat() * radiansPerDegree;
        var lon1 = from.lng() * radiansPerDegree;
        var lat2 = to.lat() * radiansPerDegree;
        var lon2 = to.lng() * radiansPerDegree;
        var angle = -Math.atan2(Math.sin(lon1 - lon2) * Math.cos(lat2), Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
        if (angle < 0.0) {
            angle += Math.PI * 2.0;
            angle = angle * degreesPerRadian
        }
        return angle
    }

    //  ???????????????????????????????????????????????
    //  ???????????????????????????????????????????????
    //  ???????????????????????????????????????????????

    if (!google.maps.Polygon.prototype.getBounds) {
        google.maps.Polygon.prototype.getBounds = function (latLng) {
            var bounds = new google.maps.LatLngBounds();
            var paths = this.getPaths();
            var path;
            for (var p = 0; p < paths.getLength(); p++) {
                path = paths.getAt(p);
                for (var i = 0; i < path.getLength(); i++) {
                    bounds.extend(path.getAt(i))
                }
            }
            return bounds
        }
    }
    ;
    //
    // TODO: could be replaced by google.maps.geometry.poly   containsLocation(point:LatLng, polygon:Polygon)	boolean	Computes whether the given point lies inside the specified polygon.
    //
    google.maps.Polygon.prototype.containsLatLng = function (latLng) {
        var bounds = this.getBounds();
        if (bounds != null && !bounds.contains(latLng)) {
            return false
        }
        var inPoly = false;
        var numPaths = this.getPaths().getLength();
        for (var p = 0; p < numPaths; p++) {
            var path = this.getPaths().getAt(p);
            var numPoints = path.getLength();
            var j = numPoints - 1;
            for (var i = 0; i < numPoints; i++) {
                var vertex1 = path.getAt(i);
                var vertex2 = path.getAt(j);
                if (vertex1.lng() < latLng.lng() && vertex2.lng() >= latLng.lng() || vertex2.lng() < latLng.lng() && vertex1.lng() >= latLng.lng()) {
                    if (vertex1.lat() + (latLng.lng() - vertex1.lng()) / (vertex2.lng() - vertex1.lng()) * (vertex2.lat() - vertex1.lat()) < latLng.lat()) {
                        inPoly = !inPoly
                    }
                }
                j = i
            }
        }
        return inPoly
    };

    google.maps.LatLng.prototype.distanceFrom = function (newLatLng) {
        var lat1 = this.lat();
        var radianLat1 = lat1 * (Math.PI / 180);
        var lng1 = this.lng();
        var radianLng1 = lng1 * (Math.PI / 180);
        var lat2 = newLatLng.lat();
        var radianLat2 = lat2 * (Math.PI / 180);
        var lng2 = newLatLng.lng();
        var radianLng2 = lng2 * (Math.PI / 180);
        var earth_radius = 3959;
        var diffLat = (radianLat1 - radianLat2);
        var diffLng = (radianLng1 - radianLng2);
        var sinLat = Math.sin(diffLat / 2);
        var sinLng = Math.sin(diffLng / 2);
        var a = Math.pow(sinLat, 2.0) + Math.cos(radianLat1) * Math.cos(radianLat2) * Math.pow(sinLng, 2.0);
        var distance = earth_radius * 2 * Math.asin(Math.min(1, Math.sqrt(a)));
        return distance
    };
