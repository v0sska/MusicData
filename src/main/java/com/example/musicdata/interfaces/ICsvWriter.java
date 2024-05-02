package com.example.musicdata.interfaces;

import com.example.musicdata.entities.MusicGroups;

import java.util.List;

public interface ICsvWriter {

    byte[] generateReport(List<MusicGroups> groupsToDownload);

}
