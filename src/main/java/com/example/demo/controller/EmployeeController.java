package com.example.demo.controller;

import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.model.Position;
import com.example.demo.repon.EmployeeReponsitory;
import com.example.demo.repon.PositionReponsitory;


@RestController
@RequestMapping("employee")
public class EmployeeController {
	
	@Autowired
	EmployeeReponsitory employeeReponsitory;
	
	@Autowired
	PositionReponsitory positionReponsitory;
	
	
	@GetMapping(
		value = "getAll",
		produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
		headers = "Accept=application/json"
			)
	public ResponseEntity<Object> findAll() throws JSONException {
		List<Employee> emps = employeeReponsitory.findAll();
		JSONArray arr = new JSONArray();
		for (Employee employee : emps) {
			JSONObject object = new JSONObject();
			object.put("id", employee.getId());
			object.put("name", employee.getName());
			object.put("address", employee.getAddress());
			object.put("old", employee.getOld());
			object.put("year", employee.getYearExp());
			object.put("married", employee.isMarried());
			object.put("national", employee.getNational());
			object.put("posId", employee.getPosition() != null ? employee.getPosition().getId() : 0);
			arr.put(object);
		}
		System.out.println("refresh lại danh sách");
		return new ResponseEntity<>(arr.toString(), HttpStatus.OK);
	}
	
	@PostMapping("add")
	public void createEmployee(@RequestBody String data) throws JSONException {
		JSONObject dataNew = new JSONObject(data);
		Employee empNew = new Employee();
		empNew.setName(dataNew.getString("name"));
		empNew.setAddress(dataNew.getString("address"));
		empNew.setOld(dataNew.getInt("old"));
		if (dataNew.has("year")) {
			empNew.setYearExp(dataNew.getInt("year"));
		}
		if (dataNew.has("married")) {
			empNew.setMarried(dataNew.getBoolean("married"));
		}
		if (dataNew.has("national")) {
			empNew.setNational(dataNew.getString("national"));
		}
		employeeReponsitory.save(empNew);
	}
	
	@PostMapping("update")
	public void updateEmployee(@RequestBody String data) throws JSONException {
		JSONObject dataNew = new JSONObject(data);
		int id = dataNew.getInt("id");
		Employee empUpdate = employeeReponsitory.getById(id);
		empUpdate.setName(dataNew.getString("name"));
		empUpdate.setAddress(dataNew.getString("address"));
		empUpdate.setOld(dataNew.getInt("old"));
		if (dataNew.has("year")) {
			empUpdate.setYearExp(dataNew.getInt("year"));
		}
		if (dataNew.has("married")) {
			empUpdate.setMarried(dataNew.getBoolean("married"));
		}
		if (dataNew.has("national")) {
			empUpdate.setNational(dataNew.getString("national"));
		}
		if (dataNew.has("posId")) {
			int idPos = dataNew.getInt("posId");
			Position position = positionReponsitory.getById(idPos);
			if (position != null) {
				empUpdate.setPosition(position);
			}
		}
		employeeReponsitory.save(empUpdate);
	}
	
	@PostMapping("delete")
	public void deleteEmployee(@RequestBody String data) throws JSONException {
		JSONObject dataNew = new JSONObject(data);
		int id = dataNew.getInt("id");
		Employee empDelete = employeeReponsitory.getById(id);
		employeeReponsitory.delete(empDelete);
	}
	
	
}
