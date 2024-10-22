package io.upscaling.video.controller;

import io.upscaling.video.entity.VideoEntity;
import io.upscaling.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class VideoController {
    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("video")
    public ResponseEntity<VideoEntity> saveVideoUrl(@RequestBody VideoEntity videoEntity) {
        VideoEntity savedVideo = videoService.saveVideoUrl(videoEntity);
        return ResponseEntity.ok(savedVideo);
    }

//    @GetMapping("/video/{id}")
//    public ResponseEntity<VideoEntity> getVideoUrlById(@PathVariable String id) {
//        VideoEntity video = videoService.getVideoUrlById(id);
//        if (video != null) {
//            return ResponseEntity.ok(video);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/video/{id}")
    public ResponseEntity<List<VideoEntity>> getAllVideos() {
        List<VideoEntity> videos = videoService.getAllVideos();
        if (!videos.isEmpty()) {
            return ResponseEntity.ok(videos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
