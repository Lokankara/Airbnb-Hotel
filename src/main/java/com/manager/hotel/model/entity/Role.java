package com.manager.hotel.model.entity;

import com.manager.hotel.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

//    @Id
//    @GeneratedValue
private Long id;


//    @Column(unique = true)
    private String name;
//    @ManyToMany(mappedBy = "roles")
//    @JsonIgnore
    private List<User> user;
}
