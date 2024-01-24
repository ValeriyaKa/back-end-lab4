package com.example.weblab4.Database;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dots")
@Getter
@Setter
public class Dot {
    @Id
    @GeneratedValue
    private Long id;
    private float x;
    private float y;
    private float r;
    private boolean status;
    private long timestamp;
    private long scriptTime;
    private String owner;

    public Dot() {
    }

    public void setDotStatus( ) {
        if (x > 0.0 && y > 0.0)
            this.setStatus(false);
        else if (x >= 0.0 && y <= 0.0)
            this.setStatus(x * x + y * y <= r * r);
        else if (x <= 0.0 && y >= 0.0)
            this.setStatus(x >= -r / 2.0 && y <= r);
        else if (x <= 0.0 && y <= 0.0)
            this.setStatus(-x <= 2.0 * y + r);
    }
}
