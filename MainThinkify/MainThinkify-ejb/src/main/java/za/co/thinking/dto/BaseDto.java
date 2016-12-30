package za.co.thinking.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * File Name    : BaseDto.java
 * Project Name : MainThinkify-ejb
 *
 * @since  Dec 20, 2016, 10:34:08 AM
 * @author Abel Chavanga <achavanga@fnb.co.za>  
 *
 */
public class BaseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;

	private Date createdDate;

	boolean isActive;

}
