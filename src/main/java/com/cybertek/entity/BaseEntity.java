package com.cybertek.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public  class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false)//prepersist calistiginda bu satir onceki verinin silinmemesini sagliyor
    private LocalDateTime insertDateTime;
    @Column(nullable = false,updatable = false)
    private Long insertUserId;
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    private Long lastUpdateUserId;

    private Boolean isDeleted = false;
//bu methodlari burdan sildik cunku injection yapamiyoruz baseentity den baseentitylistener i olusturup orda tanimladik
//    @PrePersist
//    private void onPrePersist(){
//        this.insertDateTime=LocalDateTime.now();
//        this.lastUpdateDateTime=LocalDateTime.now();
//        this.insertUserId=1L;
//        this.lastUpdateUserId=1L;
//    }
//
//    @PreUpdate
//    private void onPreUpdate(){
//        this.lastUpdateDateTime= LocalDateTime.now();
//        this.lastUpdateUserId=1L;
//    }

}
