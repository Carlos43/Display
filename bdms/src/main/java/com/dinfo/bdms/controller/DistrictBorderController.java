package com.dinfo.bdms.controller;

import com.dinfo.bdms.district.DistrictBorderExtracter;
import com.dinfo.bdms.response.RequestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/bdms")
public class DistrictBorderController {

    @RequestMapping(value="/getDistrictBorder", method= RequestMethod.GET)
    @ResponseBody
    public String getDistrictBorder(HttpServletRequest request, HttpServletResponse response) {
        try {
            String districtName = request.getParameter("districtName");

            if(districtName == null || districtName.length() == 0) {
                RequestResponse rr = new RequestResponse("Parameter: districtName empty", null, false);
                return rr.toString();
            }
            DistrictBorderExtracter districtBorderExtracter = new DistrictBorderExtracter();
            String border = districtBorderExtracter.extractBorder(districtName);

            RequestResponse rr = new RequestResponse("OK", "\""+border+"\"", true);
            return rr.toString();
        } catch(Exception e) {
            e.printStackTrace();
            RequestResponse rr = new RequestResponse("Error", null, false);
            return rr.toString();
        }
    }
}
