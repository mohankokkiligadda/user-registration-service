package io.upscaling.trainings.document;

public class IdGenerator {
    private static String currentId = "1";

    public static String generateId() {
        String generatedId = currentId;
        // Increment the currentId for the next call
        currentId = String.valueOf(Integer.parseInt(currentId) + 1);
        return generatedId;
    }
}
