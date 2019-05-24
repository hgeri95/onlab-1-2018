package bme.cateringunitmonitor.api;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate implements Serializable {
    private double latitude;
    private double longitude;

    public double distanceTo(Coordinate other) {
        if (this.latitude == other.latitude && this.longitude == other.longitude) {
            return 0;
        } else {
            double theta = this.longitude - other.longitude;
            double distance = Math.sin(Math.toRadians(this.latitude)) * Math.sin(Math.toRadians(other.latitude)) +
                    Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.latitude)) * Math.cos(Math.toRadians(theta));
            distance = Math.acos(distance);
            distance = Math.toDegrees(distance);
            distance = distance * 60 * 1.1515 * 1.609344;

            return distance;
        }
    }
}
