package com.intesim.entity;

import lombok.Data;

/**
 * RdpAuthEntity
 *
 */
@Data
public class RdpAuthEntity {

    private String ip;

    private String user;

    private String password;

    private Integer vncWebPort;

    private Integer vncPort;

    private String uid;
}
