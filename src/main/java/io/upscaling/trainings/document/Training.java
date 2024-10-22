package io.upscaling.trainings.document;

import io.upscaling.trainings.entity.MyTrainingCourseOutline;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trainings")
@Component
public class Training {
    @Id
    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String batchTime;
    private String description;
    private List<MyTrainingCourseOutline> courseOutline;

}
