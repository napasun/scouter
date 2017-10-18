package scouterx.webapp.layer.controller;

import org.apache.commons.collections.CollectionUtils;
import scouterx.webapp.framework.client.server.ServerManager;
import scouterx.webapp.framework.exception.ErrorState;
import scouterx.webapp.framework.util.ZZ;
import scouterx.webapp.layer.service.VisitorService;
import scouterx.webapp.model.VisitorGroup;
import scouterx.webapp.request.VisitorGroupRequest;
import scouterx.webapp.view.CommonResultView;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by csk746(csk746@naver.com) on 2017. 10. 13..
 */
@Path("/v1/visitor")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class VisitorController {

    @Context
    HttpServletRequest servletRequest;

    private final VisitorService visitorService = new VisitorService();

    @GET
    @Path("/realTime/ofObject/{objHash}")
    @Consumes(MediaType.APPLICATION_JSON)
    public CommonResultView<Long> retrieveVisitorRealTimeByObj(@PathParam("objHash") final int objHash,
                                                               @QueryParam("serverId") final int serverId) {
        Long visitorRealTime = visitorService.retrieveVisitorRealTimeByObj(objHash,
                ServerManager.getInstance().getServerIfNullDefault(serverId));

        return CommonResultView.success(visitorRealTime);
    }

    @GET
    @Path("/realTime/ofType/{objType}")
    @Consumes(MediaType.APPLICATION_JSON)
    public CommonResultView<Long> retrieveVisitorRealTimeByObjType(@NotNull @PathParam("objType") final String objType,
                                                                   @QueryParam("serverId") final int serverId) {

        Long visitorTotalRealTime = visitorService.retrieveVisitorRealTimeByObjType(objType,
                ServerManager.getInstance().getServerIfNullDefault(serverId));

        return CommonResultView.success(visitorTotalRealTime);
    }

    @GET
    @Path("/realTime")
    @Consumes(MediaType.APPLICATION_JSON)
    public CommonResultView<Long> retrieveVisitorRealtimeByObjHashes(@QueryParam("objHashes") String objHashes,
                                                                     @QueryParam("serverId") final int serverId) {

        List<Integer> objList = ZZ.splitParamAsInteger(objHashes);
        if (CollectionUtils.isEmpty(objList)) {
            throw ErrorState.VALIDATE_ERROR.newBizException("Query parameter 'objHashes' is required!");
        }

        Long visitorGroupRealTime = visitorService.retrieveVisitorRealTimeByObjHashes(objList,
                ServerManager.getInstance().getServerIfNullDefault(serverId));

        return CommonResultView.success(visitorGroupRealTime);
    }

    @GET
    @Path("/loadedDate/{date}/ofObject/{objHash}")
    @Consumes(MediaType.APPLICATION_JSON)
    public CommonResultView<Long> retrieveVisitorLoaddateByObjAndDate(@PathParam("date") final String date,
                                                                      @PathParam("objHash") final int objHash,
                                                                      @QueryParam("serverId") final int serverId) {
        Long visitorLoadeddate = visitorService.retrieveVisitorLoaddateByObjAndDate(objHash, date,
                ServerManager.getInstance().getServerIfNullDefault(serverId));

        return CommonResultView.success(visitorLoadeddate);
    }

    @GET
    @Path("/loadeDate/total/{date}/ofType/{objType}")
    @Consumes(MediaType.APPLICATION_JSON)
    public CommonResultView<Long> retrieveVisitorLoaddateTotalByObjAndDate(@PathParam("date") final String date,
                                                                           @PathParam("objType") final String objType,
                                                                           @QueryParam("serverId") final int serverId) {
        Long visitorLoadeddate = visitorService.retrieveVisitorLoaddateTotalByObjAndDate(objType, date,
                ServerManager.getInstance().getServerIfNullDefault(serverId));

        return CommonResultView.success(visitorLoadeddate);
    }

    @GET
    @Path("/loadedDate/group/{sdate}/{edate}")
    @Consumes(MediaType.APPLICATION_JSON)
    public CommonResultView<VisitorGroup> retrieveVisitorLoaddateGroupByObjHashesAndDate(@BeanParam @Valid final VisitorGroupRequest visitorGroupRequest) {

        VisitorGroup visitorGroupLoaded = visitorService.retrieveVisitorLoaddateGroupByObjHashesAndDate(visitorGroupRequest);

        return CommonResultView.success(visitorGroupLoaded);
    }

    @GET
    @Path("/loadedHour/group/{sdate}/{edate}")
    @Consumes(MediaType.APPLICATION_JSON)
    public CommonResultView<List<VisitorGroup>> retrieveVisitorLoadhourGroupByObjHashesAndDate(@BeanParam @Valid final VisitorGroupRequest visitorGroupRequest) {

        List<VisitorGroup> visitorGroupLoadedList = visitorService.retrieveVisitorLoadhourGroupByObjHashesAndDate(visitorGroupRequest);

        return CommonResultView.success(visitorGroupLoadedList);
    }


}
