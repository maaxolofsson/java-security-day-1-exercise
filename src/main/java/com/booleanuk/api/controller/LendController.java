package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.Lend;
import com.booleanuk.api.model.User;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.LendListResponse;
import com.booleanuk.api.response.LendResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.respository.GameRepository;
import com.booleanuk.api.respository.LendRepository;
import com.booleanuk.api.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lends")
public class LendController {

    @Autowired
    private LendRepository lends;

    @Autowired
    private UserRepository users;

    @Autowired
    private GameRepository games;

    @PostMapping("{user_id}")
    public ResponseEntity<Response<?>> newLend() {
        Lend toAdd = new Lend();
        User user = this.users.findById(toAdd.getUser().getId()).orElse(null);

        Game game = this.games.findById(toAdd.getGame().getId()).orElse(null);

        if (user == null || game == null) {
            ErrorResponse errorResponse = new ErrorResponse("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        toAdd.setUser(user);
        toAdd.setGame(game);
        user.addLend(toAdd);

        this.lends.save(toAdd);

        LendResponse lendResponse = new LendResponse();
        lendResponse.set(toAdd);

        return new ResponseEntity<>(lendResponse, HttpStatus.CREATED);
    }

    @GetMapping("{user_id}/active")
    public ResponseEntity<LendListResponse> getActiveLends() {
        int userId = 1;
        LendListResponse lendListResponse = new LendListResponse();
        lendListResponse.set(this.lends.findAllByActiveAndUserId(true, userId));
        return new ResponseEntity<>(lendListResponse, HttpStatus.OK);
    }

}
