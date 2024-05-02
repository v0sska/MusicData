package com.example.musicdata.services;


import com.example.musicdata.entities.Labels;
import com.example.musicdata.interfaces.ILabelsService;
import com.example.musicdata.repositories.LabelsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LabelService implements ILabelsService {

    private LabelsRepository labelsRepository;

    @Override
    public void addLabel(Labels labels) {
        labelsRepository.save(labels);
    }

    @Override
    public void deleteLabelById(Long id) {
        labelsRepository.deleteById(id);
    }

    @Override
    public void updateLabelById(Long id, Labels updatedLabel) {

        Optional<Labels> oldLabel = labelsRepository.findById(id);

        if(oldLabel.isPresent()){

            Labels labelToUpdate = oldLabel.get();
            labelToUpdate.setName(updatedLabel.getName());
            labelToUpdate.setFoundedYear(updatedLabel.getFoundedYear());

            labelsRepository.save(labelToUpdate);

        }
        else
            System.out.println("Label is not founded!");
    }

    @Override
    public List<Labels> getAllLabels() {
        return (List<Labels>) labelsRepository.findAll();
    }
}
