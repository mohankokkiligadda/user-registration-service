package io.upscaling.trainings.entity;

import io.upscaling.trainings.document.CourseOutLine;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MyTrainingCourseOutline extends CourseOutLine {

    private String videoUrl;

}
