package com.mylove.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mylove.eduservice.entity.EduSubject;
import com.mylove.eduservice.entity.excel.SubjectData;
import com.mylove.eduservice.entity.subject.OneSubject;
import com.mylove.eduservice.entity.subject.TwoSubject;
import com.mylove.eduservice.listener.SubjectExcelListener;
import com.mylove.eduservice.mapper.EduSubjectMapper;
import com.mylove.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-06-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {

        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //课程分类方法（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //1.查询所有的一级分类  查询条件为：parentid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");

        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2.查询所有的二级分类  查询条件为：parentid ！= 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id","0");

        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //3.创建用于最终存储封装数据的list集合
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //4.封装一级分类
        /*遍历一级分类的list集合，将一级分类的每个对象的值封装到最终的list集合finalSubjectList中
        * */
        for (int i = 0; i < oneSubjectList.size(); i++) {//遍历oneSubjectList集合
            //获取每个oneSubjectList对象
            EduSubject eduSubject = oneSubjectList.get(i);
            //将eduSubject里的值获取出来 放到OneSubject对象里
            OneSubject oneSubject = new OneSubject();

//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //此方法等同于上两行代码 将EduSubject对象的值复制到OneSubject对象中
            BeanUtils.copyProperties(eduSubject,oneSubject);
            //将多个oneSubject放到finalSubjectList里
            finalSubjectList.add(oneSubject);

            //5.封装二级分类
            //在一级分类循环遍历中查询所有的二级分类
            //二级分类的parentid与一级分类的id相等时 既加入到一级分类的children中

            //创建list集合存储每个一级分类下的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            //遍历二级分类
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //获取每个二级分类
                EduSubject tSubject = twoSubjectList.get(m);
                //判断是否为该一级分类下的二级分类
                if (tSubject.getParentId().equals(eduSubject.getId())){
                    //将二级分类加入到twoFinalSubjectList中 以便后面一并加入到一级分类的children中

                    //将EduSubject对象的值复制到TwoSubject对象中
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }

            }
            //每个一级分类下的所有二级分类找出后 放入该一级分类下的children中
            oneSubject.setChildren(twoFinalSubjectList);

        }

        return finalSubjectList;
    }
}
