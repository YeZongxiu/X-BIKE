package com.ucmed.common.api;

import com.ucmed.common.model.parking.BluetoothModel;
import com.ucmed.common.service.parking.BluetoothService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by wzyk88 on 2017/6/14.
 */
@Controller
@RequestMapping(value = "/api", produces = {APPLICATION_JSON_VALUE})
public class MacValidateApi {
    @Autowired
    private BluetoothService bluetoothService;

    @RequestMapping(method = RequestMethod.POST, produces = { "application/json" }, value = "/macvalidate")
    @ResponseBody
    public ResponseEntity<Boolean> validate(@RequestParam(value = "mac") String mac) {
        BluetoothModel model = bluetoothService.getBluetoothByMac(mac);
        if (model != null){
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return  new ResponseEntity<Boolean>(false, HttpStatus.CREATED);
        }
    }
}
