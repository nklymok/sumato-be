package com.naz_desu.sumato.common.entity.userProfile;

import com.naz_desu.sumato.common.entity.SumatoUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sumato_user_profile")
public class UserProfile {

    @Id
    private Long userId;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private SumatoUser user;
    private String name;
    private Integer jlptLevel;
    private Integer dangoCount;

}
