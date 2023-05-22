package Simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerticalSimulation {

    public static void main(String[] args) {
        Random random = new Random();

        double x = random.nextDouble() * 2 - 1;  // Random x coordinate between -1 and 1
        double y = random.nextDouble() * 2 - 1;  // Random y coordinate between -1 and 1
        double px = random.nextDouble() * 5 + 5;  // Random x component of momentum between 5 and 10
        double py = random.nextDouble() * 5 + 5;  // Random y component of momentum between 5 and 10

        int reflections = 0;
        List<double[]> reflectionPoints = new ArrayList<>();
        double delta = 0.001;  // Specify the small delta for deviation

        while (reflections <= 30) {
            double t = (-1 - x) / px;  // Calculate intersection time with left boundary
            double yIntersect = y + py * t - 5 * t * t;  // Calculate y coordinate at intersection

            if (yIntersect >= -1 && yIntersect <= 1 && t > 0) {
                // Reflection off left boundary
                x = -1;
                y = yIntersect;
                px = -(y * y - x * x) * px - 2 * x * y * py;
                py = -2 * x * y * px + (x * x - y * y) * py;
                reflectionPoints.add(new double[]{x, y});
                reflections++;
                continue;
            }

            t = (1 - x) / px;  // Calculate intersection time with right boundary
            yIntersect = y + py * t - 5 * t * t;  // Calculate y coordinate at intersection

            if (yIntersect >= -1 && yIntersect <= 1 && t > 0) {
                // Reflection off right boundary
                x = 1;
                y = yIntersect;
                px = -(y * y - x * x) * px - 2 * x * y * py;
                py = -2 * x * y * px + (x * x - y * y) * py;
                reflectionPoints.add(new double[]{x, y});
                reflections++;
                continue;
            }

            t = (-1 - y) / py;  // Calculate intersection time with bottom boundary
            double xIntersect = x + px * t;  // Calculate x coordinate at intersection

            if (xIntersect >= -1 && xIntersect <= 1 && t > 0) {
                // Reflection off bottom boundary
                x = xIntersect;
                y = -1;
                px = -(y * y - x * x) * px - 2 * x * y * py;
                py = -2 * x * y * px + (x * x - y * y) * py;
                reflectionPoints.add(new double[]{x, y});
                reflections++;
                continue;
            }

            t = (1 - y) / py;  // Calculate intersection time with top boundary
            xIntersect = x + px * t;  // Calculate x coordinate at intersection

            if (xIntersect >= -1 && xIntersect <= 1 && t > 0) {
                // Reflection off top boundary
                x = xIntersect;
                y = 1;
                px = -(y * y - x * x) * px - 2 * x * y * py;
                py = -2 * x * y * px + (x * x - y * y) * py;
                reflectionPoints.add(new double[]{x, y});
                reflections++;
                continue;
            }

            break;  // No intersection=> stop simulation
        }

        // Reversing the momentum
        px = -px;
        py = -py;

        // Reset the position
        x = reflectionPoints.get(reflectionPoints.size() - 1)[0];
        y = reflectionPoints.get(reflectionPoints.size() - 1)[1];

        int reversedReflections = 0;
        List<double[]> reversedReflectionPoints = new ArrayList<>();

        while (reversedReflections <= reflections) {
            double t = (-1 - x) / px;  // Calculate intersection time with left boundary
            double yIntersect = y + py * t - 5 * t * t;  // Calculate y coordinate at intersection

            if (yIntersect >= -1 && yIntersect <= 1 && t > 0) {
                // Reflection off left boundary
                x = -1;
                y = yIntersect;
                px = -(y * y - x * x) * px - 2 * x * y * py;
                py = -2 * x * y * px + (x * x - y * y) * py;
                reversedReflectionPoints.add(new double[]{x, y});
                reversedReflections++;
                continue;
            }

            t = (1 - x) / px;  // Calculate intersection time with right boundary
            yIntersect = y + py * t - 5 * t * t;  // Calculate y coordinate at intersection

            if (yIntersect >= -1 && yIntersect <= 1 && t > 0) {
                // Reflection off right boundary
                x = 1;
                y = yIntersect;
                px = -(y * y - x * x) * px - 2 * x * y * py;
                py = -2 * x * y * px + (x * x - y * y) * py;
                reversedReflectionPoints.add(new double[]{x, y});
                reversedReflections++;
                continue;
            }

            t = (-1 - y) / py;  // Calculate intersection time with bottom boundary
            double xIntersect = x + px * t;  //  x coordinate at intersection

            if (xIntersect >= -1 && xIntersect <= 1 && t > 0) {
                // Reflection off bottom boundary
                x = xIntersect;
                y = -1;
                px = -(y * y - x * x) * px - 2 * x * y * py;
                py = -2 * x * y * px + (x * x - y * y) * py;
                reversedReflectionPoints.add(new double[]{x, y});
                reversedReflections++;
                continue;
            }

            t = (1 - y) / py;  //  intersection time with top boundary
            xIntersect = x + px * t;  //  x coordinate at intersetion

            if (xIntersect >= -1 && xIntersect <= 1 && t > 0) {
                // Reflection off top boundary
                x = xIntersect;
                y = 1;
                px = -(y * y - x * x) * px - 2 * x * y * py;
                py = -2 * x * y * px + (x * x - y * y) * py;
                reversedReflectionPoints.add(new double[]{x, y});
                reversedReflections++;
                continue;
            }

            break;  // No intersection
        }

        // Check if the reversed path deviates more than the specified delta
        int deviationPoint = -1;
        for (int i = 0; i < Math.min(reflections, reversedReflections); i++) {
            double[] point = reflectionPoints.get(i);
            double[] reversedPoint = reversedReflectionPoints.get(i);
            double deviation = Math.sqrt((point[0] - reversedPoint[0]) * (point[0] - reversedPoint[0]) +
                    (point[1] - reversedPoint[1]) * (point[1] - reversedPoint[1]));
            if (deviation > delta) {
                deviationPoint = i;
                break;
            }
        }

        if (deviationPoint == -1) {
            System.out.println("The reversed path coincides with the straight path.");
        } else {
            System.out.println("The paths deviate after " + deviationPoint + " reflections.");
        }
    }

    }

    


