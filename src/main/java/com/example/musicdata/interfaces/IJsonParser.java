package com.example.musicdata.interfaces;

import com.example.musicdata.pojos.MusicGroupsPojo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IJsonParser {

    List<MusicGroupsPojo> readJsonFileByField(MultipartFile file);


}
