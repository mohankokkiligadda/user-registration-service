package io.upscaling.trainings.repository;

import io.upscaling.trainings.document.Training;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TrainingRepository extends MongoRepository<Training, String> {
    List<Training> findByStartDateAfter(Instant currentDate);
    List<Training> findByStartDateBetweenAndEndDateAfter(Instant startDate7DaysOld, Instant startDate, Instant endDate);
}
