package com.tpe.domain;

import com.tpe.enums.AppLogLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Document  // !!! MongoDB kullanildigi icin @Entity yerine @Document kullanildi
public class AppLog {

    @Id
    private String id;

    private AppLogLevel level; // !!! enum yapi --> core applicationdan alinacak

    private String description;

    private LocalDateTime time;
}
