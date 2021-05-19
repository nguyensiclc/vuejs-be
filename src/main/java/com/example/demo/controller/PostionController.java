package com.example.demo.controller;

import static org.assertj.core.api.Assertions.offset;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Position;
import com.example.demo.repon.EmployeeReponsitory;
import com.example.demo.repon.PositionReponsitory;

@RestController
@RequestMapping("pos")
public class PostionController {
	
	@Autowired
	PositionReponsitory positionReponsitory;
	
	@Autowired
	EmployeeReponsitory employeeReponsitory;
	
	@GetMapping(
		value = "getAll",
		produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
		headers = "Accept=application/json"
			)
	public ResponseEntity<Object> findAll() throws JSONException {
		List<Position> poss = positionReponsitory.findAll();
		JSONArray arr = new JSONArray();
		for (Position pos : poss) {
			JSONObject object = new JSONObject();
			object.put("id", pos.getId());
			object.put("name", pos.getName());
			arr.put(object);
		}
		return new ResponseEntity<>(arr.toString(), HttpStatus.OK);
	}
	
	@PostMapping("add")
	public void createPos(@RequestBody String data) throws JSONException {
		JSONObject dataNew = new JSONObject(data);
		Position posNew = new Position();
		posNew.setName(dataNew.getString("name"));
		positionReponsitory.save(posNew);
	}
	
	@PostMapping("update")
	public void updateEmployee(@RequestBody String data) throws JSONException {
		JSONObject dataNew = new JSONObject(data);
		int id = dataNew.getInt("id");
		Position posUpdate = positionReponsitory.getById(id);
		posUpdate.setName(dataNew.getString("name"));
		positionReponsitory.save(posUpdate);
	}
	
	@PostMapping("delete")
	public ResponseEntity<Object> deletePos(@RequestBody String data) throws JSONException {
		JSONObject dataNew = new JSONObject(data);
		int id = dataNew.getInt("id");
		Position posDelete = positionReponsitory.getById(id);
		boolean canDelete = true;
		canDelete = employeeReponsitory.findByPosition(posDelete).isEmpty();
		JSONObject object = new JSONObject();
		if (canDelete) {
			positionReponsitory.delete(posDelete);
			object.put("mess", "Deleted");
		} else {
			object.put("mess", "Can not delete this item!");
		}
		return new ResponseEntity<>(object.toString(), HttpStatus.OK);
		
	}
	
	@GetMapping(
			value = "candelete/{idPos}",
			produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
			headers = "Accept=application/json"
				)
	public ResponseEntity<Object> canDelete(@PathVariable(value="idPos") int idPos) throws JSONException {
		boolean canDelete = true;
		Position pos = positionReponsitory.getById(idPos);
		canDelete = employeeReponsitory.findByPosition(pos).isEmpty();
		JSONObject object = new JSONObject();
		object.put("canDelete", canDelete);
		System.out.println(employeeReponsitory.findByPosition(pos).size());
		return new ResponseEntity<>(object.toString(), HttpStatus.OK);
	}
	
}
