package ${package}.${moduleName};


#if(${hasBigDecimal})
import java.math.BigDecimal;
#end
import java.io.Serializable;
import java.util.Date;

/**
 * ${comments}
 * 
 * @author ${author}
 * @email ${email}
 * @date ${datetime}
 */
 @Data
public class ${className} implements Serializable {
	private static final long serialVersionUID = 1L;

#foreach ($column in $columns)
	/**
	 * $column.comments
	 */
private $column.attrType $column.attrName;
#end

}
