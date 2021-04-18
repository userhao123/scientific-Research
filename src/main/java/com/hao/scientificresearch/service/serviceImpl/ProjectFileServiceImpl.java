package com.hao.scientificresearch.service.serviceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.scientificresearch.entity.Project;
import com.hao.scientificresearch.entity.ProjectFile;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.mapper.ProjectFileMapper;
import com.hao.scientificresearch.model.resp.ProjectFileResp;
import com.hao.scientificresearch.service.IProjectFileService;
import com.hao.scientificresearch.service.IProjectService;
import com.hao.scientificresearch.utils.convert.ProjectFileConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 项目文件表 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-04-16
 */
@Service
public class ProjectFileServiceImpl extends ServiceImpl<ProjectFileMapper, ProjectFile> implements IProjectFileService {

    @Autowired
    private IProjectService projectService;

    @Override
    public boolean add(Integer projectId, Integer projectState, MultipartFile file) {
        if(projectId==null){
            throw new ParamException("项目id不能为空");
        }
        if(projectState==null){
            throw new ParamException("项目阶段不能为空");
        }
        if(file==null){
            throw new ParamException("文件不能为空");
        }
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileName = originalFilename.substring(0,originalFilename.indexOf("."));
        //保存文件
        String filePath = saveFile(file);
        ProjectFile projectFile = new ProjectFile();
        projectFile.setFileName(fileName);
        projectFile.setFilePath(filePath);
        projectFile.setProjectId(projectId);
        projectFile.setProjectState(projectState);
        projectFile.setUploadTime(LocalDateTime.now());
        return this.save(projectFile);
    }

    @Override
    public boolean upload(MultipartFile file, Integer projectId) {
        Project project = projectService.getById(projectId);
        if(ObjectUtil.isEmpty(project)){
            throw new ParamException("项目不存在");
        }
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileName = originalFilename.substring(0,originalFilename.indexOf("."));
        //保存文件
        String filePath = saveFile(file);
        //保存到项目文件表中
        ProjectFile projectFile = new ProjectFile();
        projectFile.setFileName(fileName);
        projectFile.setFilePath(filePath);
        projectFile.setProjectId(projectId);
        projectFile.setProjectState(project.getState());
        projectFile.setUploadTime(LocalDateTime.now());
        return this.save(projectFile);
    }

    @Override
    public Page<ProjectFileResp> pageByParam(int page, int limit, String condition, Integer state) {
        LambdaQueryWrapper<ProjectFile> wrapper = Wrappers.lambdaQuery(ProjectFile.class);
        if(StringUtils.isNotBlank(condition)){
            wrapper.eq(ProjectFile::getFileName,condition);
        }
        if(state!=null){
            wrapper.eq(ProjectFile::getProjectState,state);
        }
        wrapper.orderByDesc(ProjectFile::getProjectId).orderByAsc(ProjectFile::getProjectState);
        Page<ProjectFile> page1 = this.page(new Page<>(page, limit), wrapper);
        List<ProjectFile> records = page1.getRecords();
        //组装返回对象
        Page<ProjectFileResp> respPage = new Page<>(page1.getCurrent(), page1.getSize(), page1.getTotal());
        List<ProjectFileResp> respList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(records)){
            //所有项目
            Map<Integer, Project> projectMap = projectService.list().stream().collect(Collectors.toMap(Project::getId, p -> p));
            for(ProjectFile file:records){
                ProjectFileResp resp = ProjectFileConvert.change2Resp(file);
                resp.setProjectName(projectMap.get(file.getProjectId()).getName());
                respList.add(resp);
            }
            respPage.setRecords(respList);
        }
        return respPage;
    }

    @Override
    public boolean delete(Integer id) {
        if(id==null){
            throw new ParamException("删除id不能为空");
        }
        ProjectFile projectFile = this.getById(id);
        if(ObjectUtil.isEmpty(projectFile)){
            throw new ParamException("删除数据不存在");
        }
        return this.removeById(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return boolean
     */
    @Override
    public boolean batchDelete(List<Integer> ids) {
        if(CollectionUtil.isEmpty(ids)){
            throw new ParamException("删除id列表不能为空");
        }
        return this.removeByIds(ids);
    }

    @Override
    public boolean download(Integer id, HttpServletResponse response) {
        ProjectFile projectFile = this.getById(id);
        if(projectFile == null){
            throw new ParamException("文件不存在");
        }
        try {
            downloadFile(projectFile.getFilePath(),response);
//            response.setContentType("text/html;charset=utf-8");
//            response.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("下载文件失败");
        }
        return true;
    }


    /**保存文件，即写到磁盘中
     * @Return 文件输出流路径
     * @param file
     */
    public String saveFile(MultipartFile file){
        String projectPath = System.getProperty("user.dir");
        //文件输出流路径
        String outputPath = projectPath+ File.separator +"uploadFile"+File.separator+file.getOriginalFilename();
        try {
            byte[] data = file.getBytes();
            BufferedOutputStream out = FileUtil.getOutputStream(outputPath);
            out.write(data);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new ParamException("上传文件失败");
        }
        return outputPath;
    }

    /**
     * 根据文件路径下载文件,即写到响应输出流中
     *
     * @param path
     * @param res
     */
    public void downloadFile(String path,HttpServletResponse res) throws UnsupportedEncodingException {
        File file=new File(path);
        String fileName = file.getName();

        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"iso-8859-1"));
        byte[] buff = new byte[1024];
        FileInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new FileInputStream(file);
            int readTmp = 0;
            while ((readTmp = bis.read(buff)) != -1) {
                os.write(buff, 0, readTmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) try {
                bis.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
