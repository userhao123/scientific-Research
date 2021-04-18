package com.hao.scientificresearch.utils;

public class FileUpload {

//    public void uploadAndUnzip(String namePath, ProjectVersionParam param) {
//        MultipartFile file = param.getFile();
//        String originalFilename = file.getOriginalFilename();
////        log.info("上传文件名：{}", originalFilename);
//        if (StringUtils.isEmpty(originalFilename)) {
//            throw new ParamException("文件名为空");
//        }
//        // 判断后缀名，返回文件后缀
////        String suffix = checkSuffix(originalFilename);
////        param.setFileType(SuffixTypeEnum.getCodeByMsg(suffix));
//        // 文件类型路径 /PRD
//        String projectTypePath = ProjectTypeEnum.PRD.getCode() == param.getType() ? ProjectTypeEnum.PRD.getMsg() : ProjectTypeEnum.UI.getMsg();
//        // 当前时间yyyyMMddHHmm路径 /20210108103710
//        String timePath = DateUtil.format(new Date(), "yyyyMMddHHmmss");
//        // 文件目录路径 /devops-project-rp/devops-project-rp/file/five/PRD/20210108103710
//        String path = filePath + namePath + File.separator + projectTypePath + File.separator + timePath;
//        // 输出流路径 /devops-project-rp/devops-project-rp/file/five/PRD/20210108103710/unzip.zip
//        String outputPath = path + File.separator + originalFilename;
//        try {
//            byte[] data = file.getBytes();
//            BufferedOutputStream out = FileUtil.getOutputStream(outputPath);
//            out.write(data);
//            out.flush();
//            out.close();
//        } catch (IOException e) {
////            log.error("UploadServiceImpl|uploadAndUnzip|上传文件失败|{}", e.getMessage());
//            throw new ParamException("上传文件失败");
//        }
//        // 文件名，不带后缀 /unzip
//        String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
//        param.setFileName(fileName);
//        // 目录拼接文件名，不带后缀 /devops-project-rp/devops-project-rp/file/five/PRD/20210108103710/unzip
//        path = path + File.separator + fileName;
//        // 解压
//        try {
//            ZipUtil.unzip(outputPath, path);
//        } catch (Exception e) {
//            log.info("win系统，编码异常，转换成GBK");
//            ZipUtil.unzip(outputPath, path, CharsetUtil.CHARSET_GBK);
//        }
//        // 初始化遍历上传文件的属性
//        UploadResp uploadResp = new UploadResp(false, path);
        // 遍历目录
//        traverse(uploadResp);
//        if (!uploadResp.getFlag()) {
//            throw new ParamException("没找到index.html文件");
//        }
        // 处理路径中已配置在nginx中的路径 /devops-project-rp/file/five/PRD/20210108103710/unzip
//        param.setFilePath(uploadResp.getPath().substring(uploadResp.getPath().indexOf("/", 1)));
//    }
}
