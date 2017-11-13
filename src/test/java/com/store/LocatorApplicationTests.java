package com.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.dto.json.MainObj;
import com.store.dto.json.StoreObj;
import com.store.persistence.entity.Store;
import com.store.persistence.repository.StoreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocatorApplicationTests {

	@Autowired
	private StoreRepository storeRepository;

	@Before
	public void setup(){
		try {
			ObjectMapper mapper = new ObjectMapper();
			File file = new ClassPathResource("stores.json").getFile();
			MainObj obj = mapper.readValue(file, MainObj.class);
			for (StoreObj store : obj.getStores()) {
				GeoJsonPoint geoJsonPoint = new GeoJsonPoint(Double.parseDouble(store.getLongitude()), Double.parseDouble(store.getLatitude()));
				Store storeEntity = Store.builder().addressName(store.getAddressName()).city(store.getCity()).complexNumber(store.getComplexNumber()).locationType(store.getLocationType())
						.postalCode(store.getPostalCode()).showWarningMessage(store.getShowWarningMessage()).street(store.getStreet()).street2(store.getStreet2()).street3(store.getStreet3())
						.todayClose(store.getTodayClose()).uuid(store.getUuid()).todayOpen(store.getTodayOpen()).location(geoJsonPoint).build();
				storeRepository.save(storeEntity);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void findOneStoreSuccessfully(){
		Store se = storeRepository.findOne("WXIKYx4XBRYAAAFIIgoYwKxK");
		assert(se.getAddressName().equals("Jumbo Zwolle Groeneveld"));
	}

	@Test
	public void find5ClosestStoresSuccessfully(){
		Distance distance = new Distance(500000d, Metrics.KILOMETERS);
		PageRequest pageRequest = new PageRequest(0,5);
		List<GeoResult<Store>> storeList = storeRepository.findByLocationNear(new Point(0, 0), distance,pageRequest).getContent();
		assert(storeList.get(0).getContent().getCity().equals("Maastricht"));
	}

}
