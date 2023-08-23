package com.example.springliquidbase.infrastructure.repository.usersessionrepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "'UserSession'")
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionEntity {

    public final static String SESSION_ID = "session_id";
    public final static String ACCESS_TOKEN = "access_token";
    public final static String ACCESS_TOKEN_EXPIRED = "access_token_expired";
    public final static String REFRESH_TOKEN = "refresh_token";
    public final static String REFRESH_TOKE_EXPIRED = "refresh_token_expired";
    public final static String USER_ID = "user_id";
    public final static String CREATED = "created";

    @Id
    @Column(name = SESSION_ID)
    private UUID session_id;
    @Column(name = ACCESS_TOKEN)
    private String access_token;
    @Column(name = ACCESS_TOKEN_EXPIRED)
    private LocalDateTime access_token_expired;
    @Column(name = REFRESH_TOKEN)
    private String refresh_token;
    @Column(name = REFRESH_TOKE_EXPIRED)
    private LocalDateTime refresh_token_expired;
    @Column(name = USER_ID)
    private UUID user_id;
    @Column(name = CREATED)
    private LocalDateTime created;
}
