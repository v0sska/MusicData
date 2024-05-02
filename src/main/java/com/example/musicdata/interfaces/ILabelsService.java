package com.example.musicdata.interfaces;



import com.example.musicdata.entities.Labels;

import java.util.List;

public interface ILabelsService {

    void addLabel(Labels labels);

    void deleteLabelById(Long id);

    void updateLabelById(Long id, Labels updatedLabel);

    List<Labels> getAllLabels();

}
