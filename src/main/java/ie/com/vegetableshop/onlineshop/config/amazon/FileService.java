package ie.com.vegetableshop.onlineshop.config.amazon;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String uploadFile(MultipartFile file);
}
