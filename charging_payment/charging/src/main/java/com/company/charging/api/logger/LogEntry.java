package com.company.charging.api.logger;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:12:01 PM
 */

@Entity
@Data
@Table(name = "log_entries")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "exception", columnDefinition = "TEXT")
    private String exception;

}