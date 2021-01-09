package com.dio.crud.saladereuniao.saladereuniao.controller;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dio.crud.saladereuniao.saladereuniao.exception.ResourceNotFoundException;
import com.dio.crud.saladereuniao.saladereuniao.model.Room;
import com.dio.crud.saladereuniao.saladereuniao.repository.RoomRepository;





@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(" ")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }
    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getFindById(@PathVariable Long id) throws ResourceNotFoundException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not Found"));
        return ResponseEntity.ok().body(room);
    }

    @PostMapping("/rooms")
    public Room createRoom(@Valid @RequestBody Room room){
        return roomRepository.save(room);
    }


    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable(value = "id") Long roomId,
                                           @RequestBody Room roomDetails) throws ResourceNotFoundException {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not Found"));
        room.setName(roomDetails.getName());
        room.setDate(roomDetails.getDate());
        room.setStartHour(roomDetails.getStartHour());
        room.setEndHour(roomDetails.getEndHour());
        room.setResponsavel(room.getResponsavel());
        final Room roomUpdate = roomRepository.save(room);
        return ResponseEntity.ok().body(roomUpdate);
    }

    @DeleteMapping("/rooms/{id}")
    public Map<String, Boolean> deleteRoom(@PathVariable(value = "id") Long roomId)
            throws ResourceNotFoundException {
    Room room = roomRepository.findById(roomId).orElseThrow(
            () -> new ResourceNotFoundException("Room not Found"));
    roomRepository.delete(room);

    Map<String, Boolean> response = new HashMap<>();
    response.put("Deleted",Boolean.TRUE);
    return response;
    }


}
