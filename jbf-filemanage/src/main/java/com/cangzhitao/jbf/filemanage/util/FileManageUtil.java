package com.cangzhitao.jbf.filemanage.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cangzhitao.jbf.filemanage.comparator.FileVONameComparator;
import com.cangzhitao.jbf.filemanage.enums.FileType;
import com.cangzhitao.jbf.filemanage.vo.FileVO;
import com.cangzhitao.jbf.filemanage.vo.NetFileVO;

public class FileManageUtil {

	private static final String SAVE_PATH = "/Users/lihui/Downloads/upload";
	
	private static final String DOWNLOAD_PATH = "http://localhost:8888/upload";
	
	public static List<FileVO> listFile(String path, FileType... types) {
		List<FileVO> fileList = new ArrayList<FileVO>();
		String requestPath = "";
		if(path==null) {
			requestPath = "/";
		} else {
			requestPath = path;
		}
		if(requestPath.endsWith("/")==false) {
			requestPath += "/";
		}
		if(requestPath.startsWith("/")==false) {
			requestPath = "/" + requestPath;
		}
		requestPath = requestPath.replace("/", File.separator);
		path = SAVE_PATH + requestPath;
		File rootFile = new File(path);
		if(!rootFile.exists()) {
			return new ArrayList<FileVO>();
		}
		Map<String, String> typeMap = new HashMap<String, String>();
		if(types!=null&&types.length>0) {
			for(FileType type : types) {
				typeMap.put(type.value(), "");
			}
		}
		File[] files = rootFile.listFiles();
		for(int i=0;i<files.length;i++) {
			File file = files[i];
			FileVO fileVO = new FileVO();
			fileVO.setName(file.getName());
			if(file.isDirectory()) {
                fileVO.setType(FileType.FOLDER);
            } else {
                fileVO.setType(getFileType(file.getName()));
                fileVO.setSize(file.length());
            }
			fileVO.setLastModifyDate(new Date(file.lastModified()));
			fileVO.setAbsolutePath(DOWNLOAD_PATH+requestPath + file.getName());
			fileVO.setPath(requestPath + file.getName());
			if(typeMap==null||typeMap.size()==0) {
				fileList.add(fileVO);
			} else {
				if(typeMap.get(fileVO.getType().value())!=null) {
					fileList.add(fileVO);
				}
			}
		}
		Collections.sort(fileList, new FileVONameComparator());
		if(!"/".equals(requestPath)) {
			FileVO fileVO = new FileVO();
			fileVO.setName("..");
			fileVO.setType(FileType.FOLDER);
			String prevPath = "";
			String[] arrays = requestPath.split("/");
			for(int i=1;i<arrays.length-1;i++) {
				prevPath += ("/"+arrays[i]);
			}
			fileVO.setAbsolutePath(prevPath);
			fileVO.setPath(prevPath);
			fileList.add(0, fileVO);
		}
		return fileList;
	}
	
	public static void newFolder(String path, String name) {
		String requestPath = path;
		path = SAVE_PATH + requestPath;
		File file = new File(path, name);
		file.mkdirs();
	}
	
	public static void renameFile(String path, String oldName, String newName) {
		String requestPath = path;
		path = SAVE_PATH + requestPath;
		File oldFile = new File(path, oldName);
		File newFile = new File(path, newName);
		oldFile.renameTo(newFile);
	}
	
	public static void renameFile(String oldPath, String oldName, String newPath, String newName) {
		String requestPath = oldPath;
		oldPath = SAVE_PATH + requestPath;
		requestPath = newPath;
		newPath = SAVE_PATH + requestPath;
		File oldFile = new File(oldPath, oldName);
		File newFile = new File(newPath, newName);
		if (!oldPath.equals(newPath)) {
			newFile.delete();
		} else {
			if(oldName.equals(newName)) {
				return;
			}
		}
		int i = 1;
		while(newFile.exists()) {
			newFile = new File(newPath, newName+"_"+i);
			i++;
		}
		oldFile.renameTo(newFile);
	}
	
	public static void copyFile(String oldPath, String oldName, String newPath, String newName) {
		String requestPath = oldPath;
		oldPath = SAVE_PATH + requestPath;
		requestPath = newPath;
		newPath = SAVE_PATH + requestPath;
		File oldFile = new File(oldPath, oldName);
		File newFile = new File(newPath, newName);
		if (!oldPath.equals(newPath)) {
			newFile.delete();
		}
		int i = 1;
		while(newFile.exists()) {
			String prevName = newName.substring(0, newName.lastIndexOf("."));
			String nextName = newName.substring(newName.lastIndexOf("."));
			newName = prevName+"_"+i+nextName;
			newFile = new File(newPath, newName);
			newName = prevName+nextName;
			i++;
		}
		try {
			FileCopyUtils.copy(oldFile, newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFile(String path, String name) {
		String requestPath = path;
		path = SAVE_PATH + requestPath;
		File file = new File(path, name);
		if(file.exists()) {
			deleteDir(file);
		}
	}
	
	public static FileVO getFile(String path, String name) {
		String requestPath = path;
		path = SAVE_PATH + requestPath;
		File file = new File(path, name);
		if(file.exists()==false) {
			return null;
		}
		FileVO fileVO = new FileVO();
		fileVO.setName(file.getName());
		if(file.isDirectory()) {
            fileVO.setType(FileType.FOLDER);
        } else {
            fileVO.setType(getFileType(file.getName()));
            fileVO.setSize(file.length());
        }
		fileVO.setLastModifyDate(new Date(file.lastModified()));
		fileVO.setAbsolutePath(DOWNLOAD_PATH+requestPath + file.getName());
		fileVO.setPath(requestPath + file.getName());
		return fileVO;
	}
	
	public static File downloadFile(String url, String path, String name) {
		if(name==null||"".equals(name)) {
			name = System.currentTimeMillis()+"";
		}
		URL fileURL = null;
        URLConnection con = null;
        OutputStream os = null;
        InputStream is = null;
        File sf = null;
        try {
            fileURL = new URL(url);
            con = fileURL.openConnection();
            con.setConnectTimeout(5*1000);
            is = con.getInputStream();
            byte[] bs = new byte[1024*10];
            int len;
            sf=new File(path, name);
            if(!sf.getParentFile().exists()){
                sf.getParentFile().mkdirs();
            }
            if(sf.exists()){
                sf.delete();
            }
            boolean flag = sf.createNewFile();
            if(!flag) {
                return null;
            }
            os = new FileOutputStream(sf);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sf;
	}
	
	public static List<FileVO> uploadFromNet(List<NetFileVO> netFiles) {
		if(netFiles==null||netFiles.size()==0) {
			return new ArrayList<FileVO>();
		}
		List<FileVO> fileList = new ArrayList<FileVO>();
		for(int i=0;i<netFiles.size();i++) {
			NetFileVO netFile = netFiles.get(i);
			String path = netFile.getPath();
			if(path==null||"".equals(path)) {
				path = "/";
			}
			if(path.startsWith("/")==false) {
				path = "/" + path;
			}
			String requestPath = path;
			path = SAVE_PATH + requestPath;
			File file = downloadFile(netFile.getUrl(), path, netFile.getName());
			FileVO fileVO = fileToFileVO(file, requestPath);
			fileList.add(fileVO);
		}
		return fileList;
	}
	
	public static FileVO uploadFromLocal(MultipartFile file, String path) {
		if(file==null) {
			return null;
		}
		if(path==null||"".equals(path)) {
			path = "/";
		}
		if(path.startsWith("/")==false) {
			path = "/" + path;
		}
		String requestPath = path;
		path = SAVE_PATH + requestPath;
		String fileName = file.getOriginalFilename();
		File targetFile = new File(path, fileName);
		if(targetFile.exists()) {
			targetFile.delete();
		}
		try {
			file.transferTo(targetFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileVO fileVO = fileToFileVO(targetFile, requestPath);
		return fileVO;
	}
	
	public static FileVO fileToFileVO(File file, String requestPath) {
		FileVO fileVO = new FileVO();
		fileVO.setName(file.getName());
		if(file.isDirectory()) {
            fileVO.setType(FileType.FOLDER);
        } else {
            fileVO.setType(getFileType(file.getName()));
            fileVO.setSize(file.length());
        }
		fileVO.setLastModifyDate(new Date(file.lastModified()));
		fileVO.setAbsolutePath(DOWNLOAD_PATH+requestPath + file.getName());
		fileVO.setPath(requestPath + file.getName());
		return fileVO;
	}
	
	private static FileType getFileType(String fileName) {
        if(fileName==null||"".equals(fileName)) {
            return FileType.FILE;
        }
        if(fileName.indexOf(".")<0) {
            return FileType.FILE;
        }
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        suffix = suffix.toLowerCase();
        if(suffix.equals("jpg")||suffix.equals("jpeg")||suffix.equals("png")||suffix.equals("gif")||suffix.equals("bmp")) {
            return FileType.IMAGE;
        }
        if(suffix.equals("zip")||suffix.equals("gz")) {
            return FileType.ZIP;
        }
        if(suffix.equals("rar")) {
            return FileType.RAR;
        }
        if(suffix.equals("mp3")||suffix.equals("wma")||suffix.equals("wav")) {
            return FileType.SOUND;
        }
        if(suffix.equals("mp4")||suffix.equals("rmvb")||suffix.equals("avi")||suffix.equals("mov")||suffix.equals("mpeg")||suffix.equals("mpg")||suffix.equals("wmv")||suffix.equals("mkv")||suffix.equals("vod")||suffix.equals("flv")) {
            return FileType.MOVIE;
        }
        return FileType.FILE;
    }
	
	private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

	public static void main(String[] args) {
	}
	
}
