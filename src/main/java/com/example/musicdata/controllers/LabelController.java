package com.example.musicdata.controllers;


import com.example.musicdata.entities.Labels;
import com.example.musicdata.interfaces.ILabelsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/label")
@AllArgsConstructor
public class LabelController {

    private ILabelsService labelsService;

    @PostMapping
    public ResponseEntity<String> addLabel(@RequestBody Labels labels){

        labelsService.addLabel(labels);

        return new ResponseEntity<>("Label is added!", HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLabelById(@PathVariable Long id){

        labelsService.deleteLabelById(id);

        return new ResponseEntity<>("Label is deleted!", HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLabelById(@PathVariable Long id, @RequestBody Labels updatedLabel){

        labelsService.updateLabelById(id, updatedLabel);

        return new ResponseEntity<>("Label is updated!", HttpStatus.OK);

    }

    @GetMapping
    public List<Labels> getAllLabels(){
        return labelsService.getAllLabels();
    }

}
