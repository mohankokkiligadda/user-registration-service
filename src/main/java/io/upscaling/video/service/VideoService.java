package io.upscaling.video.service;

import io.upscaling.video.entity.VideoEntity;
import io.upscaling.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public VideoEntity saveVideoUrl(VideoEntity videoEntity) {
        return videoRepository.save(videoEntity);
    }

    public VideoEntity getVideoUrlById(String id) {
        return videoRepository.findById(id).orElse(null);
    }

    public List<VideoEntity> getAllVideos() {
        return videoRepository.findAll();
    }
}
