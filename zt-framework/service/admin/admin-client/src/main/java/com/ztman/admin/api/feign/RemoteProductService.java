package com.ztman.admin.api.feign;

import com.ztman.common.core.util.R;
//import com.ztman.product.api.entity.DemoProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
//@FeignClient(name = "product", configuration=DemoFeignConfiguration.class)
@FeignClient(value = "product")
public interface RemoteProductService {
	/**
	 *  通过商品ID获取商品信息--spring mvc 注解
	 * @param id
	 * @return
	 */
	@GetMapping("/demoproduct/{id}")
	R info(@PathVariable("id") Integer id);

//	@PutMapping("/demoproduct/")
//	R update(@RequestBody DemoProduct demoProduct);

/*	@GetMapping("/demoproduct/")
	R info();*/

	@RequestMapping(value = "demoproduct",method = RequestMethod.GET)
	R info();
//	/**
//	 * Feign自带注解
//	 * @param id
//	 * @return
//	 */
//	@RequestLine("GET /product/demoproduct/{id}")
//	R info(@PathVariable("id") Integer id);
//
//
//	@RequestLine("GET /demoproduct/")
//	R info();
//
//
//	@RequestLine("GET /demoproduct/")
//	R update(@RequestBody DemoProduct demoProduct);
//
}