package com.project.room.models;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain=true) // check
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="room")
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="room_id", nullable = false)
    private int id;

    @Column(name="room_name", nullable = false)
    private String name;

    @Column(name="country_name", nullable = false)
    @NotEmpty(message="Country is required")
    private String countryName;

    @Column(name="status", nullable = false)
    @NotEmpty(message="Status is required")
    private boolean status;
}
