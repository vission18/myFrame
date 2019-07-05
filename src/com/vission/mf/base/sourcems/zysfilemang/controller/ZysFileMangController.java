package com.vission.mf.base.sourcems.zysfilemang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vission.mf.base.controller.BaseController;
import com.vission.mf.base.enums.BaseConstants;
import com.vission.mf.base.exception.ServiceException;
import com.vission.mf.base.model.bo.AjaxResult;
import com.vission.mf.base.model.bo.DataGrid;
import com.vission.mf.base.model.bo.SessionInfo;
import com.vission.mf.base.model.bo.Tree;
import com.vission.mf.base.model.po.SysUserInfo;
import com.vission.mf.base.model.po.UploadExcel;
import com.vission.mf.base.sourcems.zysfilemang.db.SMS_CODE_TABLE;
import com.vission.mf.base.sourcems.zysfilemang.po.ZysFileMang;
import com.vission.mf.base.sourcems.zysfilemang.service.ZysFileMangService;
import com.vission.mf.base.util.RegexUtil;

/**
 * 作者：acf
 * 描述：ZysFileMangController 控制层
 * 日期：2019-7-4 15:07:15
 * 类型：CONTROLLER文件
 */
@Controller
@RequestMapping("/zysFileMan")
public class ZysFileMangController extends BaseController {

	@Autowired
	private ZysFileMangService zysFileManService;

	/**
	 * 跳转至jsp
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		this.getAccessButtons(request, model);
		return "sourcems/zysfilemang/zysFileManList";
	}

	/**
	 * 数据列表
	 */
	@RequestMapping(value = "/zysFileManDataGrid")
	@ResponseBody
	public DataGrid zysFileManDataGrid(HttpServletRequest request, ZysFileMang po) {
		int pageNo = 1;
		int pageSize = 10;
		try {
			pageNo = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
		} catch (Exception e) {
			pageNo = 1;
			pageSize = BaseConstants.MAX_PAGE_SIZE;
		}
		return zysFileManService.dataGrid(po, pageNo, pageSize);
	}


	/**
	 * 获取一条数据
	 */
	@RequestMapping(value = "/getZysFileMangById")
	@ResponseBody
	public AjaxResult getZysFileMangById(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxResult ajaxResult = new AjaxResult();
		String pkId = request.getParameter("PK_ID");
		ajaxResult.setData(zysFileManService.getZysFileMangById(pkId));
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/deleteZysFileMangById", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult delete(HttpServletRequest request,
			HttpServletResponse response)
			throws ServiceException {
		AjaxResult ajaxResult = new AjaxResult();
		try {	
			String pkId = request.getParameter("PK_ID");
			zysFileManService.deleteZysFileMangById(pkId);
			ajaxResult.setSuccess(true);
			ajaxResult.setMessage("删除成功！");
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
			ajaxResult.setMessage("删除失败！");
		}
		return ajaxResult;
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/saveZysFileMang", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult saveZysFileMang(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ZysFileMang po)
			throws ServiceException {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if (po.getPkId() == null || "".equals(po.getPkId())) {
				ajaxResult.setType(BaseConstants.OPER_TYPE_INSERT);
			} else {
				ajaxResult.setType(BaseConstants.OPER_TYPE_UPDATE);
			}
			zysFileManService.saveZysFileMang(po);
			ajaxResult.setData(po);
			ajaxResult.setSuccess(true);
			ajaxResult.setMessage("保存成功！");
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
			ajaxResult.setMessage("保存失败！");
			throw new ServiceException(e);
		}
		response.setContentType("text/html;charset=utf-8");
		return ajaxResult;
	}
	
	/**
	 * 查询码表参数
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/queryTbCodePara", method = RequestMethod.POST)
	@ResponseBody
	public Map queryTbCodePara(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		Map<String, List> paraMap = new HashMap<String, List>();
		try {
			// 文件类型
			List<Tree> fileTypeList = this.zysFileManService
					.queryCodeTableByCodeType(SMS_CODE_TABLE.FILE_TYPE,false);
			if (fileTypeList != null && fileTypeList.size() > 0) {
				paraMap.put("fileTypeList", fileTypeList);
			}

		} catch (Exception e) {
			logger.error("获取参数失败！", e);
		}
		response.setContentType("text/html;charset=utf-8");
		return paraMap;
	}
	
	@RequestMapping("/saveFileRec")
	@ResponseBody
	public AjaxResult saveFileRec(HttpServletRequest request,ZysFileMang fileInfo,
			HttpServletResponse response,HttpSession session)
			throws ServiceException {
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		ajaxResult.setMessage("文件保存失败！");
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(BaseConstants.USER_SESSION_KEY);
		try {
			//初始化
			this.zysFileManService.initFilePara(fileInfo);
			fileInfo.setCreateUser(sessionInfo.getUser().getUserName());
			//保存记录
			this.zysFileManService.saveZysFileMang(fileInfo);
			ajaxResult.setSuccess(true);
			ajaxResult.setMessage("文件保存成功！");
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
			ajaxResult.setMessage("文件保存异常，请联系管理员！");
			throw new ServiceException("不存在的文件");
		}
		return ajaxResult;
	}
	
	@RequestMapping("/uploadExcel")
	@ResponseBody
	public AjaxResult uploadExcel(HttpServletRequest request,
			HttpServletResponse response, ZysFileMang fileInfo)
			throws ServiceException {
		if (fileInfo.getFile() == null
				|| fileInfo.getFile().getOriginalFilename() == null
				|| "".equals(fileInfo.getFile().getOriginalFilename())) {
			throw new ServiceException("不存在的文件");
		}
		AjaxResult ajaxResult = new AjaxResult();
		boolean isExcel = false;
		try {
			if (isExcel) {
				
			} else {
				ajaxResult.setSuccess(false);
				ajaxResult.setMessage("文件上传失败，请检查文件类型！");
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return ajaxResult;
	}
}
