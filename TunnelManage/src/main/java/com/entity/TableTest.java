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
 * @since 2022-10-19
 */
@Data
  @EqualsAndHashCode(callSuper = false)
public class TableTest implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private Integer valuenum;

    private String valueinfo;

}
