package com.boco.eoms.ruleproject.base.util;

import java.util.UUID;


/**
 * <p>
* Title:产生UUID
* </p>
* <p>
* Description:由于数据存储的方法在引擎中完成，当引擎中采用jdbc方式保存数据时，就会需要我们手动进行赋值
* 目前该方法还用于给correlationKey赋值
* </p>
* <p>
* Date:2007-8-3 14:55:03
* </p>
* @author 陈元蜀 
* @version 1.0
*/
public class UUIDHexGenerator {
	  private static UUIDHexGenerator hexGenerator = null;

	  public static UUIDHexGenerator getInstance() {
	    if (hexGenerator == null) {
	      hexGenerator = new UUIDHexGenerator();
	    }
	    return hexGenerator;
	  }

	  public String getID() throws Exception{
	    return UUID.randomUUID().toString().replaceAll("-", "");
	  }

}
