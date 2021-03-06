package edu.wfit.liveteaching.controller;

import edu.wfit.liveteaching.config.VideoConfig;
import edu.wfit.liveteaching.model.Course;
import edu.wfit.liveteaching.model.Video;
import edu.wfit.liveteaching.model.VideoMaterial;
import edu.wfit.liveteaching.service.CourseService;
import edu.wfit.liveteaching.service.TeacherService;
import edu.wfit.liveteaching.service.VideoMaterialService;
import edu.wfit.liveteaching.service.VideoService;
import edu.wfit.liveteaching.util.Util;
import edu.wfit.liveteaching.vm.VideoViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@Controller
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoConfig videoConfig;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private VideoMaterialService videoMaterialService;

    @GetMapping("/video")
    public String toVideoListPage(Model model) {
        return toVideoListPage(model, videoService.findAll());
    }

    /**
     * 查找课程视频
     * @param courseId
     * @param model
     * @return
     */
    @GetMapping("/video/course/{courseId}")
    public String toVideoListByCourseIdPage(@PathVariable String courseId, Model model) {
        return toVideoListPage(model, videoService.findByCourseId(courseId));
    }

    private String toVideoListPage(Model model, List<Video> videoList) {
        List<Course> courseList = courseService.findAll();
        model.addAttribute("courseList", courseList);
        List<VideoViewModel> videoVMList = VideoViewModel.loadFromVideoList(
                videoList,
                teacherService,
                courseService,
                videoMaterialService
        );
        model.addAttribute("videoVMList", videoVMList);
        return "video-list";
    }

    /**
     * 直播上传
     * @param video
     * @param videoFile
     * @param videoMaterialFile
     * @param session
     * @return
     */
    @PostMapping("/video/upload")
    public String upload(
            Video video,
            @RequestParam("videoFile") MultipartFile videoFile,
            @RequestParam("videoMaterial") MultipartFile videoMaterialFile,
            HttpSession session
    ) {
        String videoFilename = videoFile.getOriginalFilename();
        if (StringUtils.isEmpty(videoFilename)) {
            return "redirect:/user/upload-video";
        }

        // 视频后缀名
        String videoFileType = Util.getFileTypeByFilename(videoFilename);

        // 只能解码 mp4 文件
        if (!"mp4".equals(videoFileType)) {
            return "redirect:/user/upload-video";
        }

        video.setId(UUID.randomUUID().toString());
        video.setTeacherId(session.getAttribute("loginUserId").toString());
        video.setFileType(videoFileType);
        videoService.uploadVideo(videoFile, video.getId() + "." + video.getFileType());
        videoService.save(video);

        // 视频资料
        String materialFilename = videoMaterialFile.getOriginalFilename();
        if (StringUtils.isEmpty(materialFilename)) {
            return "redirect:/user/video-list";
        }
        VideoMaterial videoMaterial = new VideoMaterial();
        videoMaterial.setId(UUID.randomUUID().toString());
        videoMaterial.setVideoId(video.getId());
        videoMaterial.setFileType(Util.getFileTypeByFilename(materialFilename));
        videoMaterialService.upload(videoMaterialFile, videoMaterial.getId() + "." + videoMaterial.getFileType());
        videoMaterialService.save(videoMaterial);
        return "redirect:/user/video-list";
    }


    /**
     * 观看视频 根据ID进行查询视频
     * @param videoId
     * @param model
     * @return
     */
    @GetMapping("/video/{videoId}")
    public String videoWatching(@PathVariable String videoId, Model model) {
        Video video = videoService.findById(videoId);
        if (video == null) {
            return "redirect:/video";
        }
        VideoMaterial videoMaterial = videoMaterialService.findByVideoId(videoId);
        model.addAttribute("videoId", videoId);
        model.addAttribute("videoName", video.getId() + "." + video.getFileType());
        model.addAttribute("videoMaterial", videoMaterial);
        return "video-watching";
    }

    /**
     * 根据ID删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/video/{videoId}")
    public String deleteVideo(@PathVariable String videoId) {
        videoService.deleteVideoById(videoId);
        VideoMaterial videoMaterial = videoMaterialService.findByVideoId(videoId);
        if (videoMaterial != null) {
            videoMaterialService.deleteById(videoMaterial.getId());
        }
        return "redirect:/user/video-list";
    }

    /**
     * 下载视频
     * @param filename
     * @return
     * @throws MalformedURLException
     */
    @GetMapping("/video/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(new UrlResource("file://" + videoConfig.getFilepath() + "/" + filename));
    }

    @GetMapping("/video/material/{materialId}")
    public ResponseEntity<Resource> downloadMaterial(@PathVariable String materialId) throws MalformedURLException {
        VideoMaterial videoMaterial = videoMaterialService.findById(materialId);
        String filename = videoMaterial.getId() + "." + videoMaterial.getFileType();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(new UrlResource("file://" + videoConfig.getMaterialFilePath() + "/" + filename));
    }

}
