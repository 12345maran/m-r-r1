package com.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2022-11-14
 */
@Data
  @EqualsAndHashCode(callSuper = false)
public class TableUser implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    private String password;

    private Integer kind;

}
