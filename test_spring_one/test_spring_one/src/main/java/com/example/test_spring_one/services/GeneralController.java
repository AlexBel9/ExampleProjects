package com.example.test_spring_one.services;

import com.example.test_spring_one.Dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GeneralController {

    @Autowired
    private CountService service;

    @GetMapping("/getPassword")
    public String getPassword (){
        return "HelloAlex2023";
    }

    @GetMapping("/getUnits")
    public List<DtoUnit> getUnits(){
        return service.getUnits();
    }

    @PostMapping("/addUnit")
    public ResponseEntity addUnit(@RequestParam String name){
        String response = service.addUnit(name);
        if(response == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( "\""+ name +"\" уже есть в базе");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/products")
    public List<DtoProduct> getProducts(){
        return service.getProduct();
    }

    @GetMapping("/crudes")
    public List<DtoCrude> getCrudes(){
        return service.getCrudes();
    }

    @PostMapping("/addCrudes")
    public ResponseEntity entity(@RequestBody DtoUnit dtoUnit){
        String message = service.addCrude(dtoUnit.getName(), dtoUnit.getUnitName());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @PostMapping("/deleteCrude")
    public ResponseEntity entityDeleteCrude(@RequestParam String name){
        String response = service.deleteCrude(name);
        if(response == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\""+name+"\" не существует");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/addConsolidate")
    public ResponseEntity entity(@RequestBody DtoPC [] dtoPC){

        StringBuilder builder = new StringBuilder();
        for (DtoPC pc : dtoPC) {
            builder.append(service.addConsolidate(pc.getProductName(), pc.getCrudeName(), pc.getCount()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(builder.toString());
    }
    @PostMapping("/addNewProduct")
    public ResponseEntity entityAddProduct(@RequestParam String name){
        String val = service.addNewProduct(name);
        return ResponseEntity.status(HttpStatus.OK).body(val);
    }

    @PostMapping("/counting")
    public ResponseEntity entityCount(@RequestBody DtoAmountProduct [] dtoAP){
        String message = service.counting(dtoAP);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
