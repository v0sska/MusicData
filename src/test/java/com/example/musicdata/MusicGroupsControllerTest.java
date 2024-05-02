package com.example.musicdata;

import com.example.musicdata.controllers.MusicGroupController;
import com.example.musicdata.entities.Labels;
import com.example.musicdata.entities.MusicGroups;
import com.example.musicdata.services.MusicGroupService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;


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
        assertEquals("group is added!", responseEntity.getBody());

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
        // Arrange
        HttpServletResponse response = mock(HttpServletResponse.class);
        String genre = "rock";

        // Act
        ResponseEntity<Void> result = controller.downloadReport(response, genre);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(groupService, times(1)).generateReport(response, genre);;
    }

    @Test
    void testGetMusicGroups() {
        // Arrange
        int page = 0;
        int size = 10;
        String genre = "rock";
        PageRequest pageRequest = PageRequest.of(page, size);
        List<MusicGroups> musicGroupsList = Collections.singletonList(new MusicGroups());
        Page<MusicGroups> musicGroupsPage = new PageImpl<>(musicGroupsList);

        when(groupService.listAllByPages(genre, pageRequest)).thenReturn(musicGroupsPage);

        // Act
        Page<MusicGroups> result = controller.getMusicGroups(page, size, genre);

        // Assert
        assertEquals(musicGroupsPage, result);
        verify(groupService, times(1)).listAllByPages(genre, pageRequest);
    }

    @Test
    void testUploadGroupsAndLabelsFromFile() {
        // Arrange
        MultipartFile fileToUpload = new MockMultipartFile("file", "test.txt", "text/plain", "content".getBytes());

        // Act
        ResponseEntity<String> result = controller.uploadGroupsAndLabelsFromFile(fileToUpload);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(groupService, times(1)).uploadGroupsAndLabelsFromFile(fileToUpload);
    }




}
