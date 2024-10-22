package io.upscaling.trainings.model;

import io.upscaling.trainings.document.Training;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingResponse {
    private List<Training> futureTrainings;
    private List<Training> ongoingTrainings;

}
