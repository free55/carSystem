package com.jkxy.car.api.controller;

import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.pojo.FindCarInfo;
import com.jkxy.car.api.service.CarService;
import com.jkxy.car.api.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("car")
public class CarController {
    @Autowired
    private CarService carService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findAll")
    public JSONResult findAll() {
        List<Car> cars = carService.findAll();
        return JSONResult.ok(cars);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("findById/{id}")
    public JSONResult findById(@PathVariable int id) {
        Car car = carService.findById(id);
        return JSONResult.ok(car);
    }

    /**
     * 通过车名查询
     *
     * @param carName
     * @return
     */
    @GetMapping("findByCarName/{carName}")
    public JSONResult findByCarName(@PathVariable String carName) {
        List<Car> cars = carService.findByCarName(carName);
        return JSONResult.ok(cars);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById/{id}")
    public JSONResult deleteById(@PathVariable int id) {
        carService.deleteById(id);
        return JSONResult.ok();
    }

    /**
     * 通过id更新全部信息
     *
     * @return
     */
    @PostMapping("updateById")
    public JSONResult updateById(Car car) {
        carService.updateById(car);
        return JSONResult.ok();
    }

    /**
     * 通过id增加
     *
     * @param car
     * @return
     */
    @PostMapping("insertCar")
    public JSONResult insertCar(Car car) {
        carService.insertCar(car);
        return JSONResult.ok();
    }

    @PostMapping("buyCar")
    public JSONResult buyCar(Car car){
        List<Car> cars=carService.findByType(car.getCarType());
        if(car.getNum()<=cars.size()){
            for (int i = 0; i <car.getNum(); i++) {
                carService.deleteById(cars.get(i).getId());
            }
            return JSONResult.ok();
        }else {
            return JSONResult.errorException("购车数量大于库存数量");
        }
    }
    @PostMapping("findCarRange")
    public JSONResult findCarRange(FindCarInfo findCarInfo){
        if(findCarInfo.getRange1()<=0 || findCarInfo.getRange2()<findCarInfo.getRange1()){
            return JSONResult.errorException("查询的数据范围错误");
        }
        List<Car> cars = carService.findCarRange(findCarInfo);
        return JSONResult.ok(cars);
    }
}
