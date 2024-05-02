package com.example.musicdata.parsers;

import com.example.musicdata.entities.MusicGroups;
import com.example.musicdata.interfaces.ICsvWriter;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;
import java.io.StringWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class CsvWriter implements ICsvWriter {

    @Override
    public byte[] generateReport(List<MusicGroups> groupsToDownload) {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             StringWriter stringWriter = new StringWriter();
             CSVWriter writer = new CSVWriter(stringWriter)) {

            String[] headerRecord = {"Name", "Genre", "Label-id"};
            writer.writeNext(headerRecord);

            for (MusicGroups musicGroup : groupsToDownload) {
                String[] data = {musicGroup.getName(), musicGroup.getGenre(), String.valueOf(musicGroup.getLabels().getId())};
                writer.writeNext(data);
            }
            writer.flush();

            return stringWriter.toString().getBytes();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
