package com.example.musicdata;

import com.example.musicdata.controllers.LabelController;
import com.example.musicdata.entities.Labels;
import com.example.musicdata.entities.MusicGroups;
import com.example.musicdata.interfaces.ILabelsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LabelsControllerTest {

    @Mock
    private ILabelsService labelsService;
    
    @InjectMocks
    private LabelController labelController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void addMusicGroups(){
        Labels labels = new Labels("Capitol Records", 1961);

        doNothing().when(labelsService).addLabel(any(Labels.class));

        ResponseEntity responseEntity = labelController.addLabel(labels);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Label is added!", responseEntity.getBody());

        verify(labelsService, times(1)).addLabel(any(Labels.class));

    }

    @Test
    public void testGetAllLabels() {
        // Arrange
        List<Labels> expectedLabels = new ArrayList<>(); // add some expected labels here

        expectedLabels.add(new Labels("Tesr", 1234));
        expectedLabels.add(new Labels("Test", 342));

        // Act
        when(labelsService.getAllLabels()).thenReturn(expectedLabels); // mock your service method call

        List<Labels> actualLabels = labelController.getAllLabels();

        // Assert
        assertEquals(expectedLabels, actualLabels);
        verify(labelsService).getAllLabels(); // verify that service method was called
    }


    @Test
    public void testUpdateLabelById() {
        // Arrange
        Long id = 1L;
        Labels updatedLabel = new Labels("Bla", 41 ); // create your updated label object here
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Label is updated!", HttpStatus.OK);

        // Act
       doNothing().when(labelsService).updateLabelById(id, updatedLabel); // mock your service method call

        ResponseEntity<String> actualResponse = labelController.updateLabelById(id, updatedLabel);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(labelsService).updateLabelById(id, updatedLabel); // verify that service method was called with correct parameters
    }

}
