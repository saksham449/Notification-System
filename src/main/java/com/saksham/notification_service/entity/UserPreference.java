package com.saksham.notification_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private boolean emailEnabled = true;

    @Column(nullable = false)
    private boolean smsEnabled = true;

    @Column(nullable = false)
    private boolean pushEnabled = true;

    @Column(nullable = false)
    private boolean inAppEnabled = true;
}