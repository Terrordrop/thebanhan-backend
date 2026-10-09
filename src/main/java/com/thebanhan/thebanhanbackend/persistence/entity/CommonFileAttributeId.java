package com.thebanhan.thebanhanbackend.persistence.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommonFileAttributeId implements Serializable {

    private Long seq;
    private Long commonFileSeq;
}
