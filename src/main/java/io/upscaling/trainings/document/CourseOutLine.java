package io.upscaling.trainings.document;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseOutLine {
    @NonNull
    private String id;
    private String topicName;
    private String details;

}
