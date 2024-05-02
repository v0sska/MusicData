package com.example.musicdata.parsers;

import com.example.musicdata.entities.MusicGroups;
import com.example.musicdata.interfaces.IMusicGroupsService;
import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.StringWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class CsvWriter {

    public byte[] generateReport(List<MusicGroups> groupsToDownload) {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             StringWriter stringWriter = new StringWriter();
             CSVWriter writer = new CSVWriter(stringWriter)) {

            // Write CSV Header
            String[] headerRecord = {"Name", "Genre", "Label-id"};
            writer.writeNext(headerRecord);

            // Write CSV Content
            for (MusicGroups musicGroup : groupsToDownload) {
                String[] data = {musicGroup.getName(), musicGroup.getGenre(), String.valueOf(musicGroup.getLabels().getId())};
                writer.writeNext(data);
            }
            writer.flush();

            // Перетворення рядка у байтовий масив
            return stringWriter.toString().getBytes();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
