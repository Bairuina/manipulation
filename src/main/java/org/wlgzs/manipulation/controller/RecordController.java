package org.wlgzs.manipulation.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.manipulation.base.BaseController;
import org.wlgzs.manipulation.entity.Record;
import org.wlgzs.manipulation.util.Result;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 胡亚星
 * @since 2019-03-19
 */
@Controller
@RequestMapping("/record")
public class RecordController extends BaseController {

    //新增一条记录
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ModelAndView addRecord(Model model, Record record) {
        Result result = iRecordService.addRecord(record);
        if (result.getCode() == 0) {
            model.addAttribute("msg", "成功！");
        } else {
            model.addAttribute("msg", "失败！");
        }
        return new ModelAndView("redirect:/record/recordList/1");
    }

    //查看所有记录(按会员搜索，前台汇总)
    @RequestMapping(value = "/recordList/{page}")
    public ModelAndView recordList(@PathVariable("page") int page, Model model,
                                   @RequestParam(value = "findName", defaultValue = "") String findName,
                                   @RequestParam(value = "start_time", defaultValue = "") String start_time,
                                   @RequestParam(value = "end_time", defaultValue = "") String end_time) {
        Result result = iRecordService.recordList(page, findName, start_time, end_time);
        IPage<Record> ipage = (IPage<Record>) result.getData();
        model.addAttribute("TotalPages", ipage.getPages());//查询的总页数
        model.addAttribute("Number", page);//查询的当前第几页
        List<Record> recordList = ipage.getRecords();
        model.addAttribute("recordList", recordList);
        return new ModelAndView("recordList");
    }

    //删除记录
    @RequestMapping(value = "/delete/{recordId}", method = RequestMethod.DELETE)
    public ModelAndView delete(Model model, @PathVariable("recordId") int recordId) {
        Result result = iRecordService.delete(recordId);
        model.addAttribute("msg", result.getMsg());
        return new ModelAndView("redirect:/record/recordList/1");
    }

    //去修改记录
    @RequestMapping(value = "toModify/{recordId}")
    public ModelAndView toModify(Model model, @PathVariable("recordId") int recordId) {
        Record record = iRecordService.getById(recordId);
        model.addAttribute("record", record);
        if (record == null) {
            model.addAttribute("msg", "该记录不存在！");
        }
        return new ModelAndView("Modify");
    }

    //修改记录
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public ModelAndView Modify(Record record, Model model) {
        Result result = iRecordService.Modify(record);
        model.addAttribute("msg", result.getMsg());
        return new ModelAndView("redirect:/record/recordList/1");
    }

    //按员工，时间，推拿种类，并返回总数量(必须要员工名字)
    @RequestMapping(value = "/summary/{page}")
    public ModelAndView summary(@PathVariable("page") int page,Model model,
                                String staffName, @RequestParam(value = "tuinaType", defaultValue = "all") String tuinaType,
                                @RequestParam(value = "startTime", defaultValue = "") String startTime,
                                @RequestParam(value = "endTime", defaultValue = "") String endTime) {
        IPage<Record> iPage = iRecordService.summary(page,staffName,tuinaType,startTime,endTime);
        System.out.println(iPage);
        List<Record> recordList = iPage.getRecords();
        model.addAttribute("recordList",recordList);
        if(recordList.size() <= 0){
            model.addAttribute("msg","没有数据！");
        }
        model.addAttribute("size",recordList.size());
        model.addAttribute("TotalPages", iPage.getPages());//查询的总页数
        model.addAttribute("Number", page);//查询的当前第几页
        return new ModelAndView("recordList");
    }


}