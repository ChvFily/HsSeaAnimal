package com.hs.sea_water;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hs.sea_water.entity.Video;
import com.hs.sea_water.serviceI.mpl.VideoServiceImpl;

@SpringBootTest
class SeaWaterApplicationTests {
	
	@Autowired VideoServiceImpl videoServiceImpl;

	@Test
	void contextLoads() {
		for(Video video:videoServiceImpl.getVideoAll()) {
			System.out.print(video.toString());
		}
		
	}

}
