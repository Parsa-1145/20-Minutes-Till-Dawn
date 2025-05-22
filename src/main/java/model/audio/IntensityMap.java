package model.audio;

import java.io.*;
import java.util.*;
import java.nio.file.*;

public class IntensityMap {
    static class Point {
        double time;
        double intensity;

        Point(double time, double intensity) {
            this.time = time;
            this.intensity = intensity;
        }
    }

    public IntensityMap(String filePath) {
        try {
            loadFromCSV(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Point> points = new ArrayList<>();

    public void loadFromCSV(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        points.clear();

        for (int i = 1; i < lines.size(); i++) { // skip header
            String[] parts = lines.get(i).split(",");
            double time = Double.parseDouble(parts[0]);
            double intensity = Double.parseDouble(parts[1]);
            points.add(new Point(time, intensity));
        }

        // Make sure it's sorted by time
        points.sort(Comparator.comparingDouble(p -> p.time));
    }

    // Get interpolated intensity at any time
    public double getIntensityAt(double time) {
        if (points.isEmpty()) return 0;

        if (time <= points.get(0).time) return points.get(0).intensity;
        if (time >= points.get(points.size() - 1).time) return points.get(points.size() - 1).intensity;

        for (int i = 1; i < points.size(); i++) {
            Point prev = points.get(i - 1);
            Point next = points.get(i);
            if (time >= prev.time && time <= next.time) {
                double t = (time - prev.time) / (next.time - prev.time); // normalize
                return prev.intensity + t * (next.intensity - prev.intensity); // linear interpolation
            }
        }

        return 0; // fallback
    }

//    // Example usage
//    public static void main(String[] args) throws IOException {
//        IntensityMap map = new IntensityMap();
//        map.loadFromCSV("intensity.csv");
//
//        double t = 4.0;
//        double intensity = map.getIntensityAt(t);
//        System.out.println("Intensity at " + t + "s: " + intensity);
//    }
}