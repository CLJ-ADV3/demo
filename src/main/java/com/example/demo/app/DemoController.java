package com.example.demo.app;


import com.example.demo.data.DemoData;
import com.example.demo.data.IDemoData;

import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUserDetails(@RequestParam(name="id") String id){

        //UserDetails userDetails = null;
        String result = null;
        IDemoData demoData = new DemoData();
        if(id != null)
        {

            result = demoData.getUserDetails(id);
        }


        return result;
    }
}
