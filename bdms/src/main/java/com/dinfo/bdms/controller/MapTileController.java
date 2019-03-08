package com.dinfo.bdms.controller;

import com.dinfo.bdms.extract.ExtractTaskDispatcher;
import com.dinfo.bdms.response.RequestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Carlos on 2017/12/28.
 */
@Controller
@RequestMapping("/bdms")
public class MapTileController {

    @RequestMapping(value="/getBDTile", method= RequestMethod.GET)
    @ResponseBody
    public String getBDTile(HttpServletRequest request, HttpServletResponse response) {
        try {
            String minXStr = request.getParameter("minX");
            String maxXStr = request.getParameter("maxX");
            String minYStr = request.getParameter("minY");
            String maxYStr = request.getParameter("maxY");
            String levelStr = request.getParameter("level");
            String useMultiThreadStr = request.getParameter("useMultiThread");
            String maxUrlPerThreadStr = request.getParameter("maxUrlPerThread");

            if(minXStr == null || minXStr.length() == 0) {
                RequestResponse rr = new RequestResponse("Parameter: minX empty", null, false);
                return rr.toString();
            }
            if(maxXStr == null || maxXStr.length() == 0) {
                RequestResponse rr = new RequestResponse("Parameter: maxX empty", null, false);
                return rr.toString();
            }
            if(minYStr == null || minYStr.length() == 0) {
                RequestResponse rr = new RequestResponse("Parameter: minY empty", null, false);
                return rr.toString();
            }
            if(maxYStr == null || maxYStr.length() == 0) {
                RequestResponse rr = new RequestResponse("Parameter: maxY empty", null, false);
                return rr.toString();
            }
            if(levelStr == null || levelStr.length() == 0) {
                RequestResponse rr = new RequestResponse("Parameter: level empty", null, false);
                return rr.toString();
            }
            if(useMultiThreadStr == null || useMultiThreadStr.length() == 0) {
                RequestResponse rr = new RequestResponse("Parameter: useMultiThread empty", null, false);
                return rr.toString();
            }
            boolean useMultiThread = Boolean.parseBoolean(useMultiThreadStr);
            if(useMultiThread) {
                if(maxUrlPerThreadStr == null || maxUrlPerThreadStr.length() == 0) {
                    RequestResponse rr = new RequestResponse("Parameter: maxUrlPerThread empty", null, false);
                    return rr.toString();
                }
                int minX = Integer.parseInt(minXStr);
                int maxX = Integer.parseInt(maxXStr);
                int minY = Integer.parseInt(minYStr);
                int maxY = Integer.parseInt(maxYStr);
                int level = Integer.parseInt(levelStr);
                int maxUrlPerThread = Integer.parseInt(maxUrlPerThreadStr);
                if(maxUrlPerThread <= 0) {
                    RequestResponse rr = new RequestResponse("Parameter: maxUrlPerThread error", null, false);
                    return rr.toString();
                }
                ExtractTaskDispatcher etd = new ExtractTaskDispatcher();
                etd.dispatch(minX, maxX, minY, maxY, level, useMultiThread, maxUrlPerThread);
            } else {
                int minX = Integer.parseInt(minXStr);
                int maxX = Integer.parseInt(maxXStr);
                int minY = Integer.parseInt(minYStr);
                int maxY = Integer.parseInt(maxYStr);
                int level = Integer.parseInt(levelStr);
                ExtractTaskDispatcher etd = new ExtractTaskDispatcher();
                etd.dispatch(minX, maxX, minY, maxY, level, useMultiThread, 0);
            }
            RequestResponse rr = new RequestResponse("OK", null, true);
            return rr.toString();
        } catch(Exception e) {
            e.printStackTrace();
            RequestResponse rr = new RequestResponse("Error", null, false);
            return rr.toString();
        }
    }
}
