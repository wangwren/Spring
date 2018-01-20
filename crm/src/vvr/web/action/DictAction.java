package vvr.web.action;

/**
 * 客户字典控制层
 */
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import vvr.domain.Dict;
import vvr.service.DictService;
import vvr.web.utils.FastJsonUtil;

public class DictAction extends ActionSupport {

	private static final long serialVersionUID = -7043448609339477771L;
	
	private Dict dict;
	private DictService dictService;
	

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

	public Dict getDict() {
		return dict;
	}

	public void setDict(Dict dict) {
		this.dict = dict;
	}
	
	/**
	 * 查询客户字典的客户级别或客户来源
	 * @return
	 * @throws Exception
	 */
	public String findByCode() throws Exception{
		
		List<Dict> list = dictService.findByCode(dict.getDict_type_code());
		
		//使用fastjson将list转换成json字符串
		//List<Dict> list = dictService.findByCode("006");
		String jsonString = FastJsonUtil.toJSONString(list);
		//System.out.println(jsonString);
		
		//把json字符串写入到浏览器
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//输出至浏览器
		FastJsonUtil.write_json(response, jsonString);
		
		return NONE;
	}
	

}
