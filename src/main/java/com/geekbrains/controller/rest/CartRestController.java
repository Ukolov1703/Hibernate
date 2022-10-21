package com.geekbrains.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persistence.Cart;
import ru.geekbrains.persistence.entities.Product;
import ru.geekbrains.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CartRestController {

    private CartService cartService;
    private Cart cart;

}