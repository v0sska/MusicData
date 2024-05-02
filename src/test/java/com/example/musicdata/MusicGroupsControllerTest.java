package com.example.musicdata;

import com.example.musicdata.controllers.MusicGroupController;
import com.example.musicdata.entities.Labels;
import com.example.musicdata.entities.MusicGroups;
import com.example.musicdata.services.MusicGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MusicGroupsControllerTest {

    @Mock
    private MusicGroupService groupService;

    @InjectMocks
    private MusicGroupController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addGroupTest(){
        MusicGroups musicGroups = new MusicGroups("Nirvana", "Grunge", new Labels("Capitol Records", 1961));

        doNothing().when(groupService).addGroup(any(MusicGroups.class));

        ResponseEntity responseEntity = controller.addGroup(musicGroups);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("group is added!!", responseEntity.getBody());

        verify(groupService, times(1)).addGroup(any(MusicGroups.class));

    }

    @Test
    public void deleteGroupTest(){

       MusicGroups musicGroups = new MusicGroups("Nirvana", "Grunge", new Labels("Capitol Records", 1961));

        doNothing().when(groupService).deleteGroupById(musicGroups.getId());

        ResponseEntity responseEntity = controller.deleteGroupById(musicGroups.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("group is deleted!", responseEntity.getBody());

        verify(groupService, times(1)).deleteGroupById(musicGroups.getId());
    }

    @Test
    void testGetGroupById() {
        // Arrange
        Long id = 1L;
        List<MusicGroups> musicGroupsList = Collections.singletonList(new MusicGroups("Nirvana", "Grunge", new Labels("Capitol Records", 1961)));

        when(groupService.getGroupById(id)).thenReturn(musicGroupsList);

        // Act
        List<MusicGroups> result = controller.getGroupById(id);

        // Assert
        assertEquals(musicGroupsList, result);
        verify(groupService, times(1)).getGroupById(id);
    }

    @Test
    void testUpdateGroupById() {
        // Arrange
        Long id = 1L;
        MusicGroups updatedGroup = new MusicGroups("Nirvana", "Grunge", new Labels("Capitol Records", 1961));

        // Act
        ResponseEntity<String> result = controller.updateGroupById(id, updatedGroup);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(groupService, times(1)).updateGroupById(id, updatedGroup);
    }

    @Test
    void testDownloadReport() {
        // Act
        ResponseEntity<Void> result = controller.downloadReport(null, null);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(groupService, times(1)).generateReport(any(), anyString());
    }






}
