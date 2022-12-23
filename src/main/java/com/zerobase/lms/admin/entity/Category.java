package com.zerobase.lms.admin.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    String categoryName;
    int sortValue;
    boolean usingYn;
}
